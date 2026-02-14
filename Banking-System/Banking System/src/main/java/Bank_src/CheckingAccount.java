package Bank_src;

public class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, Customer customer, double overdraftLimit) {
        super(accountNumber, customer);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit must be greater than 0.");
        }
        balance += amount;
    }

    @Override
    public void withdraw(double amount) throws InvalidAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal must be greater than 0.");
        }
        if (amount > balance + overdraftLimit) {
            throw new InsufficientFundsException("Overdraft limit exceeded.");
        }
        balance -= amount;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}
