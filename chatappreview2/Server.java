import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.text.SimpleDateFormat;

public class Server {
    private static final int PORT = 1234;
    private static final int MAX_CLIENTS = 50;
    
    // Thread-safe collections
    private static final Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private static final Set<PrintWriter> clientWriters = Collections.synchronizedSet(new HashSet<>());
    private static final AtomicInteger clientCounter = new AtomicInteger(0);
    
    // Server statistics
    private static volatile boolean serverRunning = true;
    private static final Date startTime = new Date();
    
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("  Java Chat Server Starting...  ");
        System.out.println("=================================");
        System.out.println("Server started on port: " + PORT);
        System.out.println("Max clients allowed: " + MAX_CLIENTS);
        System.out.println("Start time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime));
        System.out.println("=================================");
        
        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nServer shutting down gracefully...");
            serverRunning = false;
            broadcastSystemMessage("Server is shutting down...");
            
            // Close all client connections
            synchronized (clients) {
                for (ClientHandler client : clients.values()) {
                    client.disconnect();
                }
            }
        }));
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setSoTimeout(1000); // 1 second timeout for accept()
            
            while (serverRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    
                    // Check client limit
                    if (clientCounter.get() >= MAX_CLIENTS) {
                        System.out.println("Client limit reached. Rejecting connection from: " + 
                                         clientSocket.getInetAddress());
                        PrintWriter rejectWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                        rejectWriter.println("SERVER: Maximum client limit reached. Please try again later.");
                        clientSocket.close();
                        continue;
                    }
                    
                    String clientId = "Client-" + clientCounter.incrementAndGet();
                    System.out.println("New client connected: " + clientSocket.getInetAddress() + 
                                     " [ID: " + clientId + "]");
                    System.out.println("Total clients: " + clientCounter.get());
                    
                    ClientHandler clientHandler = new ClientHandler(clientSocket, clientId);
                    clients.put(clientId, clientHandler);
                    
                    Thread clientThread = new Thread(clientHandler);
                    clientThread.setName("ClientHandler-" + clientId);
                    clientThread.start();
                    
                } catch (SocketTimeoutException e) {
                    // Timeout is normal, continue loop to check serverRunning
                    continue;
                } catch (IOException e) {
                    if (serverRunning) {
                        System.err.println("Error accepting client connection: " + e.getMessage());
                    }
                }
            }
            
        } catch (IOException e) {
            System.err.println("Server startup error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("Server stopped.");
    }
    
    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private final String clientId;
        private PrintWriter out;
        private BufferedReader in;
        private String clientName;
        private boolean connected;
        
        public ClientHandler(Socket socket, String clientId) {
            this.socket = socket;
            this.clientId = clientId;
            this.connected = true;
        }
        
        @Override
        public void run() {
            try {
                // Set up streams
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                clientWriters.add(out);
                
                // Send welcome message
                out.println("SERVER: Welcome to the chat! You are connected as " + clientId);
                out.println("SERVER: Type your messages and press Enter to send.");
                out.println("SERVER: Current users online: " + clientCounter.get());
                
                // Notify others about new user
                broadcastMessage("SERVER: " + clientId + " joined the chat", this);
                
                String message;
                while (connected && (message = in.readLine()) != null) {
                    // Input validation
                    if (message.trim().isEmpty()) {
                        continue;
                    }
                    
                    // Limit message length
                    if (message.length() > 500) {
                        out.println("SERVER: Message too long. Maximum 500 characters allowed.");
                        continue;
                    }
                    
                    // Extract client name from first message if it contains "joined"
                    if (clientName == null && message.contains("joined the chat")) {
                        clientName = message.substring(0, message.indexOf(" joined the chat"));
                        System.out.println("Client " + clientId + " identified as: " + clientName);
                    }
                    
                    // Process commands
                    if (message.startsWith("/")) {
                        handleCommand(message);
                        continue;
                    }
                    
                    System.out.println("[" + clientId + "] " + message);
                    broadcastMessage("Client: " + message, this);
                }
                
            } catch (IOException e) {
                System.err.println("Client " + clientId + " connection error: " + e.getMessage());
            } finally {
                disconnect();
            }
        }
        
        private void handleCommand(String command) {
            String[] parts = command.split(" ", 2);
            String cmd = parts[0].toLowerCase();
            
            switch (cmd) {
                case "/help":
                    out.println("SERVER: Available commands:");
                    out.println("SERVER: /help - Show this help message");
                    out.println("SERVER: /users - List online users");
                    out.println("SERVER: /time - Show server time");
                    out.println("SERVER: /stats - Show server statistics");
                    break;
                    
                case "/users":
                    out.println("SERVER: Online users (" + clientCounter.get() + "):");
                    synchronized (clients) {
                        for (String id : clients.keySet()) {
                            out.println("SERVER: - " + id);
                        }
                    }
                    break;
                    
                case "/time":
                    out.println("SERVER: Server time: " + 
                              new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    break;
                    
                case "/stats":
                    long uptime = (System.currentTimeMillis() - startTime.getTime()) / 1000;
                    out.println("SERVER: Server Statistics:");
                    out.println("SERVER: - Uptime: " + uptime + " seconds");
                    out.println("SERVER: - Current users: " + clientCounter.get());
                    out.println("SERVER: - Start time: " + 
                              new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime));
                    break;
                    
                default:
                    out.println("SERVER: Unknown command. Type /help for available commands.");
            }
        }
        
        public void disconnect() {
            if (!connected) return;
            
            connected = false;
            
            try {
                if (out != null) {
                    clientWriters.remove(out);
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing client " + clientId + " resources: " + e.getMessage());
            }
            
            // Remove from clients map
            clients.remove(clientId);
            clientCounter.decrementAndGet();
            
            // Notify others about user leaving
            String departureMessage = clientName != null ? 
                "SERVER: " + clientName + " left the chat" : 
                "SERVER: " + clientId + " disconnected";
            broadcastMessage(departureMessage, this);
            
            System.out.println("Client " + clientId + " disconnected. Total clients: " + clientCounter.get());
        }
    }
    
    private static void broadcastMessage(String message, ClientHandler sender) {
        if (message == null || message.trim().isEmpty()) {
            return;
        }
        
        synchronized (clientWriters) {
            Iterator<PrintWriter> iterator = clientWriters.iterator();
            while (iterator.hasNext()) {
                PrintWriter writer = iterator.next();
                try {
                    // Don't send message back to sender
                    if (sender != null && writer == sender.out) {
                        continue;
                    }
                    
                    writer.println(message);
                    
                    // Check if writer is still valid
                    if (writer.checkError()) {
                        iterator.remove();
                        System.out.println("Removed invalid client writer");
                    }
                } catch (Exception e) {
                    iterator.remove();
                    System.err.println("Error broadcasting message, removed client: " + e.getMessage());
                }
            }
        }
    }
    
    private static void broadcastSystemMessage(String message) {
        broadcastMessage("SERVER: " + message, null);
    }
}