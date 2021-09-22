import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseServer implements IDbServer {
	private List<ICallbackServer> connectedServers;

	public DatabaseServer() throws SQLException {
		DriverManager.registerDriver(new org.postgresql.Driver());
		connectedServers = new ArrayList<>();
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
	public void register(ICallbackServer cbServer) {
		connectedServers.add(cbServer);
		try {
			cbServer.update("Connected to DB Server");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean createAccount(int accountID) throws RemoteException {
		try (Connection c = getConnection()) {
			// Check if account exists
			if (c.prepareStatement("SELECT accountID from accounts where accountID = " + accountID).executeQuery().next()) {
				System.out.println("Account already exists");
				return false;
			}
			// Create account
			c.prepareStatement("INSERT INTO accounts values (" + accountID + ", " + 0 + ")").execute();
			System.out.println(accountID + " has been added to the database");
			return true;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		System.out.println("No access to database");
		return false;
	}

	@Override
	public boolean deposit(int accountID, double amount) throws RemoteException {
		try (Connection c = getConnection()) {
			// Check if account exists
			if (!c.prepareStatement("SELECT accountID from accounts where accountID = " + accountID).executeQuery().next()) {
				System.out.println("Account does not exist");
				return false;
			}
			// If account does exist
			ResultSet r = c.prepareStatement("SELECT * FROM accounts WHERE accountID = " + accountID).executeQuery();
			r.next();
			double currentBalance = r.getDouble("balance");
			c.prepareStatement("UPDATE accounts SET balance = " + (currentBalance + amount) + " WHERE accountID = " + accountID).execute();
			return true;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		System.out.println("Connection lost");
		return false;
	}

	@Override
	public boolean withdraw(int accountID, double amount) throws RemoteException {
		try (Connection c = getConnection()) {
			// Check if account exists
			if (!c.prepareStatement("SELECT accountID from accounts where accountID = " + accountID).executeQuery().next()) {
				System.out.println("Account does not exist");
				return false;
			}
			// If account does exist
			ResultSet r = c.prepareStatement("SELECT * FROM accounts WHERE accountID = " + accountID).executeQuery();
			r.next();
			double currentBalance = r.getDouble("balance");
			c.prepareStatement("UPDATE accounts SET balance = " + (currentBalance - amount) + " WHERE accountID = " + accountID).execute();
			return true;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		System.out.println("Connection lost");
		return false;
	}

}
