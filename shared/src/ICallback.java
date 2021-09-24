import java.rmi.Remote;

public interface ICallback extends Remote {
	void update() throws RuntimeException;
}
