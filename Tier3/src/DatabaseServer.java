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

public class DatabaseServer implements IDbServer
{
	private List<ICallback> connectedServers;

	public DatabaseServer() throws SQLException
	{
		DriverManager.registerDriver(new org.postgresql.Driver());
		connectedServers = new ArrayList<>();
	}

	public void startDatabaseServer() throws RemoteException, AlreadyBoundException
	{
		Registry registry = LocateRegistry.createRegistry(1101);

		registry.bind(NameConstants.DBServer.name(), this);

		UnicastRemoteObject.exportObject(this, 1102);

		System.out.println("Database Server Started!");
	}

	private Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=banking",
			"postgres", "sh1tvac");
	}

	@Override public boolean register(ICallback cbServer)
	{
		connectedServers.add(cbServer);
		if (connectedServers.contains(cbServer))
			return true;
//		try
//		{
//			cbServer.update();
//		}
//		catch (RemoteException e)
//		{
//			e.printStackTrace();
//		}
		return false;
	}

	@Override public boolean putIntoDatabase(Account acc) throws RemoteException
	{
		try (Connection connection = getConnection())
		{
			ResultSet result = connection.prepareStatement(
				"SELECT accountID from accounts where accountID = " + acc.getAccountId()).executeQuery();
			if (result.next())
			{
				connection.prepareStatement("UPDATE * FROM accounts WHERE accountID = " + acc.getAccountId()
					+ " SET values(accountID, balance) (" + acc.getAccountId() + ", " + acc.getBalance() + ")");
			}
			else
			{
				connection.prepareStatement(
					"INSERT INTO accounts values(" + acc.getAccountId() + ", " + acc.getBalance() + ")");
			}
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override public Account getFromDatabase(int accountID) throws RemoteException
	{
		try (Connection c = getConnection())
		{
			ResultSet r = c.prepareStatement("SELECT accountID from accounts where accountID = " + accountID)
				.executeQuery();
			if (r.next())
			{
				Account acc = new Account(accountID);
				acc.setBalance(r.getInt("balance"));
				return acc;
			}
			else
				return null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public boolean createAccount(int accountID) throws RemoteException
	{
		try (Connection c = getConnection())
		{
			// Check if account exists
			if (c.prepareStatement("SELECT accountID from accounts where accountID = " + accountID).executeQuery()
				.next())
			{
				System.out.println("Account already exists");
				return false;
			}
			// Create account
			c.prepareStatement("INSERT INTO accounts values (" + accountID + ", " + 0 + ")").execute();
			System.out.println(accountID + " has been added to the database");
			return true;
		}
		catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
		System.out.println("No access to database");
		return false;
	}

	public boolean deposit(int accountID, double amount) throws RemoteException
	{
		try (Connection c = getConnection())
		{
			// Check if account exists
			if (!c.prepareStatement("SELECT accountID from accounts where accountID = " + accountID).executeQuery()
				.next())
			{
				System.out.println("Account does not exist");
				return false;
			}
			// If account does exist
			ResultSet r = c.prepareStatement("SELECT * FROM accounts WHERE accountID = " + accountID).executeQuery();
			r.next();
			double currentBalance = r.getDouble("balance");
			c.prepareStatement(
					"UPDATE accounts SET balance = " + (currentBalance + amount) + " WHERE accountID = " + accountID)
				.execute();
			return true;
		}
		catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
		System.out.println("Connection lost");
		return false;
	}

	public boolean withdraw(int accountID, double amount) throws RemoteException
	{
		try (Connection c = getConnection())
		{
			// Check if account exists
			if (!c.prepareStatement("SELECT accountID from accounts where accountID = " + accountID).executeQuery()
				.next())
			{
				System.out.println("Account does not exist");
				return false;
			}
			// If account does exist
			ResultSet r = c.prepareStatement("SELECT * FROM accounts WHERE accountID = " + accountID).executeQuery();
			r.next();
			double currentBalance = r.getDouble("balance");
			c.prepareStatement(
					"UPDATE accounts SET balance = " + (currentBalance - amount) + " WHERE accountID = " + accountID)
				.execute();
			return true;
		}
		catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
		System.out.println("Connection lost");
		return false;
	}

}
