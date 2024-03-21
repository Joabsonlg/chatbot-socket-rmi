package ufrn.br.dim0614;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicBooleans;

public class SocketServerImpl implements ChatBotServerInterface {
    private static final int PORT = 4444;
    private static final Set<PrintWriter> CLIENT_WRITERS = ConcurrentHashMap.newKeySet();
    private static final boolean TRACE_MODE = false;
    private static Bot bot;
    private static Chat chatSession;

    public SocketServerImpl() {
        String resourcesPath = getResourcesPath();
        bot = new Bot("super", resourcesPath);
        MagicBooleans.trace_mode = TRACE_MODE;
        chatSession = new Chat(bot);
        bot.brain.nodeStats();;
        bot.brain.nodeStats();
    }

    @Override
    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("ChatBot Server (Socket) is listening on port " + PORT);

            while (true) {
                new ClientHandler(serverSocket.accept(), chatSession).start();
            }
        } catch (IOException e) {
            System.out.println("Exception in ChatBot Server (Socket): " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket socket;
        private final Chat chatSession;

        public ClientHandler(Socket socket, Chat chatSession) {
            this.socket = socket;
            this.chatSession = chatSession;
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
                    String response = chatSession.multisentenceRespond(message);
                    System.out.println("Send: " + response);
                    output.println(response);
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

    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 1);
        String resourcesPath = path + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }

}
