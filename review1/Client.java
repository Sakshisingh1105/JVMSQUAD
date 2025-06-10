import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connected to the chat server");

            Thread sendThread = new Thread(() -> {
                String userInput;
                try {
                    while ((userInput = consoleInput.readLine()) != null) {
                        out.println(userInput);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Thread receiveThread = new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("Server: " + serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            sendThread.start();
            receiveThread.start();

            sendThread.join();
            receiveThread.join();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}