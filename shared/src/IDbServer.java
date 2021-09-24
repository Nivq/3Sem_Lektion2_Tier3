import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDbServer extends Remote {
	void register(ICallback cbServer) throws RemoteException;

	void putIntoDatabase(Account acc) throws RemoteException;

	Account getFromDatabase(int accountID) throws RemoteException;
}
