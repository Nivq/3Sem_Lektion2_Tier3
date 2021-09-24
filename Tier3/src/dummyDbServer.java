import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class dummyDbServer implements IDbServer
{
	List<Account> cunts;
	List<ICallback> connectedServers;

	public dummyDbServer() throws AlreadyBoundException, RemoteException
	{
		connectedServers = new ArrayList<>();
		cunts = new ArrayList<>();
		startDatabaseServer();
	}

	public void startDatabaseServer() throws RemoteException, AlreadyBoundException
	{
		Registry registry = LocateRegistry.createRegistry(1101);

		registry.bind(NameConstants.DBServer.name(), this);

		UnicastRemoteObject.exportObject(this, 1102);

		System.out.println("Database Server Started!");
	}

	@Override public void register(ICallback cbServer) throws RemoteException
	{
		System.out.println("Registering cbServer to List of Connected Servers");
		connectedServers.add(cbServer);
	}

	@Override public void putIntoDatabase(Account acc) throws RemoteException
	{
		System.out.println("Putting " + acc.toString() + " in the Database");
		for (Account cunt : cunts)
		{
			if (cunt.getAccountId() == acc.getAccountId())
			{
				cunt.setBalance(acc.getBalance());
				return;
			}
		}
		cunts.add(acc);
	}

	@Override public Account getFromDatabase(int accountID) throws RemoteException
	{
		for (Account cunt : cunts)
		{
			if (cunt.getAccountId() == accountID)
			{
				return cunt;
			}
		}
		return null;
	}
}
