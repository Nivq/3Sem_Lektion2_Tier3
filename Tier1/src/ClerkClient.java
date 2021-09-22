import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClerkClient {
	private static IServer server;
	private static Scanner input;

	public static void main(String[] args) throws RemoteException, NotBoundException {
		// Locate and set the server
		Registry r = LocateRegistry.getRegistry(1099);
		server = (IServer) r.lookup(NameConstants.Server.name());
		input = new Scanner(System.in);

		// User Interaction
		clerkLoop();
	}

	private static void clerkLoop() {
		String[] userInput;
		while (true) {
			System.out.println("use: withdraw <accountID> <amount>\nor : deposit <accountID> <amount>");
			userInput = input.nextLine().split(" ");
			try {
				cmdHandle(userInput);
			} catch (RemoteException e) {
				System.out.println("Connection has been lost");
				break;
			}
		}
	}

	private static void cmdHandle(String[] cmdAndArgs) throws RemoteException {
		String cmd = cmdAndArgs[0];
		int accountID = Integer.parseInt(cmdAndArgs[1]);
		int amount = Integer.parseInt(cmdAndArgs[2]);
		switch (cmd.toLowerCase()){
			case "withdraw" -> server.withdraw(accountID, amount);
			case "deposit" -> server.deposit(accountID, amount);
			default -> System.out.println("Invalid Command");
		}
	}
}
