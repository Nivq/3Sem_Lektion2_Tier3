import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseServer implements IDbServer {


	public DatabaseServer() throws SQLException {
		DriverManager.registerDriver(new org.postgresql.Driver());
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
			if (!c.prepareStatement("SELECT accountID from accounts where accountID = " + account.getAccountId()).execute()) {
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
		return false;
	}

	@Override
	public boolean withdraw(Account account, double amount) {
		return false;
	}


}
