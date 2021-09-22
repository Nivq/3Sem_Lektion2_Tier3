import java.rmi.Remote;

public interface ICallbackClient extends Remote {
	void update();
}
