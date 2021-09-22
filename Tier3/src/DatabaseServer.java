import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseServer implements IDbServer
{


    private DatabaseServer() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres?currentSchema=banking",
                "username", "pasword");
    }


    @Override
    public boolean createAccount(Account account) {
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
