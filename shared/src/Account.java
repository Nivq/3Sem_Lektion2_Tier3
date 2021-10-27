import java.io.Serializable;

public class Account implements Serializable {
	private int accountId;
	private double balance;

	public Account(int accountId) {
		this.accountId = accountId;
		balance = 0d;
	}

	public int getAccountId() {
		return accountId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override public String toString()
	{
		return "Account{" + "accountId=" + accountId + ", balance=" + balance + '}';
	}
}
