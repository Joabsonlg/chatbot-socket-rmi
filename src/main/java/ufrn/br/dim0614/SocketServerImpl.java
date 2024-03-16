package ufrn.br.dim0614;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SocketServerImpl implements ChatBotServerInterface {
    private static final int PORT = 4444;
    private static final Set<PrintWriter> CLIENT_WRITERS = ConcurrentHashMap.newKeySet();

    @Override
    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("ChatBot Server (Socket) is listening on port " + PORT);

            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.out.println("Exception in ChatBot Server (Socket): " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("New client connected");
            try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {
                CLIENT_WRITERS.add(output);

                String message;
                while ((message = input.readLine()) != null) {
                    System.out.println("Received: " + message);
                    for (PrintWriter writer : CLIENT_WRITERS) {
                        if (writer != output) {
                            writer.println("From another client: " + message);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Exception in ClientHandler: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CLIENT_WRITERS.remove(this);
            }
        }
    }
}
