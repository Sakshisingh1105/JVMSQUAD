import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client extends JFrame {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 1234;
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;
    
    // GUI Components
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JScrollPane scrollPane;
    private JLabel statusLabel;
    private JPanel headerPanel;
    
    // Colors for Telegram-like appearance
    private static final Color PRIMARY_COLOR = new Color(0, 136, 204);
    private static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
    private static final Color MESSAGE_BACKGROUND = new Color(245, 245, 245);
    private static final Color SEND_BUTTON_COLOR = new Color(0, 122, 255);
    
    public Client() {
        initializeGUI();
        connectToServer();
    }
    
    private void initializeGUI() {
        setTitle("Chat Application - Connecting...");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        // Header panel
        createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Chat area
        createChatArea();
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Message input panel
        createMessageInputPanel();
        mainPanel.add(createMessageInputPanel(), BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Set focus to message field
        messageField.requestFocusInWindow();
    }
    
    private void createHeaderPanel() {
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        
        JLabel titleLabel = new JLabel("Chat Room");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        statusLabel = new JLabel("Connecting...");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(statusLabel, BorderLayout.EAST);
    }
    
    private void createChatArea() {
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(MESSAGE_BACKGROUND);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        chatArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        
        scrollPane = new JScrollPane(chatArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
    }
    
    private JPanel createMessageInputPanel() {
        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(BACKGROUND_COLOR);
        
        messageField = new JTextField();
        messageField.setFont(new Font("Arial", Font.PLAIN, 14));
        messageField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        
        sendButton = new JButton("Send");
        sendButton.setBackground(SEND_BUTTON_COLOR);
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        sendButton.setFocusPainted(false);
        sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sendButton.setBackground(SEND_BUTTON_COLOR.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sendButton.setBackground(SEND_BUTTON_COLOR);
            }
        });
        
        // Add action listeners
        ActionListener sendAction = e -> sendMessage();
        sendButton.addActionListener(sendAction);
        
        messageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });
        
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        
        return inputPanel;
    }
    
    private void connectToServer() {
        try {
            // Get username
            username = JOptionPane.showInputDialog(
                this,
                "Enter your username:",
                "Join Chat",
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (username == null || username.trim().isEmpty()) {
                username = "Anonymous" + System.currentTimeMillis() % 1000;
            }
            
            username = username.trim();
            setTitle("Chat Application - " + username);
            
            // Connect to server
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Send username to server
            out.println(username + " joined the chat");
            
            // Update status
            statusLabel.setText("Connected");
            statusLabel.setForeground(Color.GREEN);
            
            appendMessage("Connected to chat server", "SYSTEM");
            appendMessage("Welcome, " + username + "!", "SYSTEM");
            
            // Start listening for messages
            startMessageListener();
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                this,
                "Unable to connect to server: " + e.getMessage(),
                "Connection Error",
                JOptionPane.ERROR_MESSAGE
            );
            statusLabel.setText("Connection Failed");
            statusLabel.setForeground(Color.RED);
        }
    }
    
    private void startMessageListener() {
        Thread messageListener = new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    final String finalMessage = message;
                    SwingUtilities.invokeLater(() -> {
                        if (finalMessage.startsWith("Client: " + username + ":")) {
                            // Don't display our own messages again
                            return;
                        }
                        appendMessage(finalMessage, "SERVER");
                    });
                }
            } catch (IOException e) {
                if (!socket.isClosed()) {
                    SwingUtilities.invokeLater(() -> {
                        appendMessage("Connection lost", "SYSTEM");
                        statusLabel.setText("Disconnected");
                        statusLabel.setForeground(Color.RED);
                    });
                }
            }
        });
        messageListener.setDaemon(true);
        messageListener.start();
    }
    
    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty() && out != null) {
            // Send message to server
            out.println(username + ": " + message);
            
            // Display message locally
            appendMessage("You: " + message, "USER");
            
            // Clear message field
            messageField.setText("");
            messageField.requestFocusInWindow();
        }
    }
    
    private void appendMessage(String message, String type) {
        SwingUtilities.invokeLater(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String timestamp = sdf.format(new Date());
            
            String formattedMessage;
            switch (type) {
                case "SYSTEM":
                    formattedMessage = "[" + timestamp + "] " + message + "\n";
                    break;
                case "USER":
                    formattedMessage = "[" + timestamp + "] " + message + "\n";
                    break;
                default:
                    formattedMessage = "[" + timestamp + "] " + message + "\n";
            }
            
            chatArea.append(formattedMessage);
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }
    
    @Override
    public void dispose() {
        try {
            if (out != null) {
                out.println(username + " left the chat");
                out.close();
            }
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Client().setVisible(true);
        });
    }
}