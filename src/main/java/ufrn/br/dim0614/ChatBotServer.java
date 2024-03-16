package ufrn.br.dim0614;

public class ChatBotServer {
    public static void main(String[] args) {
        try {
            if (args.length < 1) throw new IllegalArgumentException("Must specify 'socket' or 'rmi'");
            String serverType = args[0];
            ChatBotServerInterface server = ServerFactory.getServer(serverType);
            server.startServer();
        } catch (Exception e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

