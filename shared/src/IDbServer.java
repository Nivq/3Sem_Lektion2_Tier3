import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDbServer extends Remote {
	boolean register(ICallback cbServer) throws RemoteException;

	boolean putIntoDatabase(Account acc) throws RemoteException;

	/**
	 * Get Account from Database
	 * @param accountID Account Number
	 * @return Account with the corresponding ID, null if account wasn't found
	 * @throws RemoteException
	 */
	Account getFromDatabase(int accountID) throws RemoteException;
}
