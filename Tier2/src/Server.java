import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class Server implements IServer{


    private Map<String, ICallbackClient> connectedClients;

    private IDbServer DbServer;


    public void startServer() throws RemoteException, AlreadyBoundException, NotBoundException {
        Registry registry = LocateRegistry.createRegistry(1099);

        registry.bind(NameConstants.Server.name(), this);

        UnicastRemoteObject.exportObject(this, 1100);

        System.out.println("Server Started!");

        connectedClients = new HashMap<>();

        // connecting to database server
        connectToDbServer();
    }

    private void connectToDbServer() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(1101);
        DbServer = (IDbServer) registry.lookup(NameConstants.DBServer.name());
    }


    @Override
    public boolean createAccount(Account account) {
        return DbServer.createAccount(account);
    }

    @Override
    public boolean deposit(Account account, double amount) {
        return DbServer.deposit(account,amount);
    }

    @Override
    public boolean withdraw(Account account, double amount) {
        return DbServer.withdraw(account,amount);
    }
}
