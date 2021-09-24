import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Server implements IServer {
	private Map<String, Set<ICallback>> connectedClients;
	private IDbServer dbServer;

	public void startServer() throws RemoteException, AlreadyBoundException, NotBoundException {
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.bind(NameConstants.Server.name(), this);
		UnicastRemoteObject.exportObject(this, 1100);
		connectedClients = new HashMap<>();

		System.out.println("Server Started!");

		// Connecting to database server
		connectToDbServer();
	}

	private void connectToDbServer() throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(1101);
		dbServer = (IDbServer) registry.lookup(NameConstants.DBServer.name());
//		dbServer.register(this);
	}

	@Override
	public void register(ICallback connectingClient) throws RemoteException {
		connectedClients.get(connectingClient.getClass().getName()).add(connectingClient);
	}


	@Override
	public boolean createAccount(int accountID) throws RemoteException {

	}

	@Override
	public boolean deposit(int accountID, double amount) throws RemoteException {

	}

	@Override
	public boolean withdraw(int accountID, double amount) throws RemoteException {

	}

	// @Override
	public void update(String message) {
		System.out.println(message);
	}
}
