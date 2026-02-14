package Bank_src;

public class Deposit extends Transaction {
    public Deposit(double amount) {
        super(amount);
    }

    @Override
    public String getType() {
        return "Deposit";
    }

    @Override
    public String toString() {
        return getType() + ": $" + amount + " at " + timestamp;
    }
}
