import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseServer implements IDbServer {


	public DatabaseServer() throws SQLException {
		DriverManager.registerDriver(new org.postgresql.Driver());
	}


	public void startDatabaseServer() throws RemoteException, AlreadyBoundException {
		Registry registry = LocateRegistry.createRegistry(1101);

		registry.bind(NameConstants.DBServer.name(), this);

		UnicastRemoteObject.exportObject(this, 1102);

		System.out.println("Database Server Started!");
	}


	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/postgres?currentSchema=banking",
				"postgres", "sh1tvac");
	}


	@Override
	public boolean createAccount(Account account) {
		try (Connection c = getConnection()) {
			// Check if account exists
			if (c.prepareStatement("SELECT accountID from accounts where accountID = " + account.getAccountId()).execute()) {
				System.out.println("Account already exists");
				return false;
			}
			// Create account
			c.prepareStatement("INSERT INTO accounts values (" + account.getAccountId() + ", " + account.getBalance() + ")").execute();
			System.out.println(account.getAccountId() + " has been added to the database");
			return true;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		System.out.println("No access to database");
		return false;
	}

	@Override
	public boolean deposit(Account account, double amount) {
		try (Connection c = getConnection()) {
			// Check if account exists
			if (!c.prepareStatement("SELECT accountID from accounts where accountID = " + account.getAccountId()).execute()) {
				System.out.println("Account does not exist");
				return false;
			}
			// If account does exist
			double currentBalance = c.prepareStatement("SELECT * FROM accounts WHERE accountID = " + account.getAccountId()).executeQuery().getDouble("balance");
			c.prepareStatement("UPDATE accounts SET balance = " + currentBalance + account.getBalance() + " WHERE accountID = " + account.getAccountId()).execute();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean withdraw(Account account, double amount) {
		try (Connection c = getConnection()) {
			// Check if account exists
			if (!c.prepareStatement("SELECT accountID from accounts where accountID = " + account.getAccountId()).execute()) {
				System.out.println("Account does not exist");
				return false;
			}
			// If account does exist
			double currentBalance = c.prepareStatement("SELECT * FROM accounts WHERE accountID = " + account.getAccountId()).executeQuery().getDouble("balance");
			c.prepareStatement("UPDATE accounts SET balance = " + currentBalance - account.getBalance() + " WHERE accountID = " + account.getAccountId()).execute();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return false;
	}


}
