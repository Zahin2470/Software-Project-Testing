package Bank_src;

public class Withdrawal extends Transaction {
    public Withdrawal(double amount) {
        super(amount);
    }

    @Override
    public String getType() {
        return "Withdrawal";
    }

    @Override
    public String toString() {
        return getType() + ": $" + amount + " at " + timestamp;
    }
}
