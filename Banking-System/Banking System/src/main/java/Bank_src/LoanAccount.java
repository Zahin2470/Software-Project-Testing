package Bank_src;

public class LoanAccount extends Account {
    private double loanAmount;
    private double interestRate;

    public LoanAccount(String accountNumber, Customer customer, double loanAmount, double interestRate) {
        super(accountNumber, customer);
        if (loanAmount <= 0 || interestRate < 0) {
            throw new IllegalArgumentException("Loan amount must be positive and interest cannot be negative.");
        }
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.balance = -loanAmount; // Negative because it's a loan
    }

    @Override
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Payment must be greater than 0.");
        }
        balance += amount;
    }

    @Override
    public void withdraw(double amount) throws InvalidAmountException {
        throw new InvalidAmountException("Withdrawals are not allowed on loan accounts.");
    }

    public double getOutstandingLoan() {
        return -balance;
    }

    public double calculateInterest() {
        return getOutstandingLoan() * interestRate;
    }
}
