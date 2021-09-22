


public class DatabaseServer implements IDbServer
{

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
