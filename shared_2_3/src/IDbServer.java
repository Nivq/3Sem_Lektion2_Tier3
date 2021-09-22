import java.rmi.Remote;

public interface IDbServer extends Remote {
	boolean createAccount(Account account);

	boolean deposit(Account account, double amount);

	boolean withdraw(Account account, double amount);
}
