import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICallbackServer extends Remote {
	void update() throws RemoteException;
}
