import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDbServer extends Remote {
	boolean register(ICallback cbServer) throws RemoteException;

	boolean putIntoDatabase(Account acc) throws RemoteException;

	Account getFromDatabase(int accountID) throws RemoteException;
}
