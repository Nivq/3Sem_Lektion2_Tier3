import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
	boolean createAccount(int accountID) throws RemoteException;

	boolean deposit(int accountID, double amount) throws RemoteException;

	boolean withdraw(int accountID, double amount) throws RemoteException;
}
