import java.rmi.RemoteException;
import java.util.List;

public class dummyDbServer implements IDbServer
{
	List<Account> cunts;

	@Override public void register(ICallback cbServer) throws RemoteException
	{
		System.out.println("Registering cbServer to List of Connected Servers");
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
		}cunts.add(acc);
	}

	@Override public Account getFromDatabase(int accountID) throws RemoteException
	{
		returning
	}
}
