package Bank_src;

public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, Customer customer, double interestRate) {
        super(accountNumber, customer);
        this.interestRate = interestRate;
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
        if (amount > balance) {
            throw new InsufficientFundsException("Not enough balance.");
        }
        balance -= amount;
    }

    public double calculateInterest() {
        return balance * interestRate;
    }
}
