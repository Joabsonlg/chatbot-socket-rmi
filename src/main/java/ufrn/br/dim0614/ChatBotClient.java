package ufrn.br.dim0614;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import java.io.*;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ChatBotClient {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java ChatBotClient <socket/rmi>");
            return;
        }
        String connectionType = args[0];

        if ("socket".equalsIgnoreCase(connectionType)) {
            connectViaSocket();
        } else if ("rmi".equalsIgnoreCase(connectionType)) {
            connectViaRMI();
        } else {
            System.out.println("Invalid connection type. Use 'socket' or 'rmi'.");
        }
    }

    private static void connectViaSocket() {
        String hostname = "localhost";
        int port = 4444;

        try (Socket socket = new Socket(hostname, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            new Thread(() -> {
                try {
                    String fromServer;
                    while ((fromServer = in.readLine()) != null) {
                        System.out.println(fromServer);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading from server: " + e.getMessage());
                    e.printStackTrace();
                }
            }).start();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your messages below:");
            while (true) {
                String userInput = scanner.nextLine();
                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }
                out.println(userInput);
            }
        } catch (IOException e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void connectViaRMI() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ChatBotService service = (ChatBotService) registry.lookup("ChatBotService");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your messages below:");
            while (true) {
                String userInput = scanner.nextLine();
                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }
                String response = service.sendMessage(userInput);
                System.out.println(response);
            }
        } catch (Exception e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
