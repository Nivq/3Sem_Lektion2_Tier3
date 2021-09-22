import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class Start {
	public static void main(String[] args) throws SQLException, AlreadyBoundException, RemoteException {
		DatabaseServer dbs = new DatabaseServer();
		dbs.startDatabaseServer();
	}
}
