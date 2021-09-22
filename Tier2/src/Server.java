import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class Server implements IServer{


    private Map<String, ICallbackClient> connectedClients;


    public void startServer() throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(1099);

        registry.bind(NameConstants.DBServer.name(), this);

        UnicastRemoteObject.exportObject(this, 1100);

        System.out.println("Server Started!");

        connectedClients = new HashMap<>();
    }

    @Override
    public boolean createAccount(Account account) {
        return false;
    }

    @Override
    public boolean deposit(Account account, double amount) {
        return false;
    }

    @Override
    public boolean withdraw(Account account, double amount) {
        return false;
    }
}
