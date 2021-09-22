import java.sql.SQLException;

public class Test {
	public static void main(String[] args) throws SQLException {
		DatabaseServer db = new DatabaseServer();
		Account newAcc = new Account(1234);
		db.createAccount(newAcc);

		db.deposit(newAcc, 120);

		db.withdraw(newAcc, 100);
	}
}
