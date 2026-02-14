package Bank_src;

public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected Customer customer;

    public Account(String accountNumber, Customer customer) {
        if (accountNumber == null || customer == null) {
            throw new IllegalArgumentException("Account number and customer cannot be null.");
        }
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = 0.0;
    }

    public abstract void deposit(double amount) throws InvalidAmountException;
    public abstract void withdraw(double amount) throws InvalidAmountException, InsufficientFundsException;

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public Customer getCustomer() {
        return customer;
    }
}
