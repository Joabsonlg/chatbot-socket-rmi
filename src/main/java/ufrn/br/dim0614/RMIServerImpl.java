package ufrn.br.dim0614;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicBooleans;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServerImpl extends UnicastRemoteObject implements ChatBotServerInterface, ChatBotService {
    private Bot bot;
    private Chat chatSession;

    protected RMIServerImpl() throws RemoteException {
        super();
        initializeBot();
    }

    @Override
    public void startServer() {
        try {
            ChatBotService server = this;
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("ChatBotService", server);
            System.out.println("ChatBot Server (RMI) is ready.");
        } catch (Exception e) {
            System.out.println("ChatBot Server (RMI) exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String sendMessage(String message) throws RemoteException {
        System.out.println("Received: " + message);
        String response = chatSession.multisentenceRespond(message);
        System.out.println("Send: " + response);
        return response;
    }

    private void initializeBot() {
        String resourcesPath = getResourcesPath();
        bot = new Bot("super", resourcesPath);
        chatSession = new Chat(bot);
        MagicBooleans.trace_mode = false;
    }

    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 1);
        String resourcesPath = path + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }
}
