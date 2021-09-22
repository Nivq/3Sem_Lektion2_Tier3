import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class Server implements ICallbackServer, IServer {
	private Map<String, ICallbackClient> connectedClients;
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
		dbServer.register((ICallbackServer) this);
	}


	@Override
	public boolean createAccount(int accountID) throws RemoteException {
		return dbServer.createAccount(accountID);
	}

	@Override
	public boolean deposit(int accountID, double amount) throws RemoteException {
		return dbServer.deposit(accountID, amount);
	}

	@Override
	public boolean withdraw(int accountID, double amount) throws RemoteException {
		return dbServer.withdraw(accountID, amount);
	}

	@Override
	public void update(String message) {
		System.out.println(message);
	}
}
