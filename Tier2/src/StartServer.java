import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class StartServer {
	public static void main(String[] args) throws AlreadyBoundException, NotBoundException, RemoteException {
		Server s = new Server();
		s.startServer();
	}
}
