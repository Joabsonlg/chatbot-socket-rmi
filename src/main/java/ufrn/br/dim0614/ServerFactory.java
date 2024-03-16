package ufrn.br.dim0614;

public class ServerFactory {
    public static ChatBotServerInterface getServer(String type) throws Exception {
        if ("socket".equalsIgnoreCase(type)) {
            return new SocketServerImpl();
        } else if ("rmi".equalsIgnoreCase(type)) {
            return new RMIServerImpl();
        }
        throw new IllegalArgumentException("Unknown server type.");
    }
}
