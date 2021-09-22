public class Account {
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
}
