package ufrn.br.dim0614;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatBotService extends Remote {
    String sendMessage(String message) throws RemoteException;
}
