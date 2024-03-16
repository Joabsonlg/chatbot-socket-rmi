package ufrn.br.dim0614;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServerImpl extends UnicastRemoteObject implements ChatBotServerInterface, ChatBotService {
    protected RMIServerImpl() throws RemoteException {
    }

    @Override
    public void startServer() throws RemoteException {
        try {
            ChatBotService server = new RMIServerImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("ChatBotService", server);
            System.out.println("ChatBot Server (RMI) is ready.");
        } catch (Exception e) {
            System.out.println("ChatBot Server (RMI) exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String sendMessage(String message) {
        System.out.println("Received: " + message);
        return "Echo from RMI Server: " + message;
    }
}
