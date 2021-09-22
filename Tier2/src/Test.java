import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class Test {
	public static void main(String[] args) throws SQLException, AlreadyBoundException, NotBoundException, RemoteException {
		IServer s = new Server();
		((Server) s).startServer();
		Account a = new Account(123);
		System.out.println("New account: " + s.createAccount(a));
		System.out.println("Deposit 10: " + s.deposit(a, 10));
		System.out.println("Withdraw 9: " + s.withdraw(a, 9));
	}
}
