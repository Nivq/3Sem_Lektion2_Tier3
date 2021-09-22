import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDbServer extends Remote {
	boolean createAccount(Account account) throws RemoteException;

	boolean deposit(Account account, double amount) throws RemoteException;

	boolean withdraw(Account account, double amount) throws RemoteException;
}
