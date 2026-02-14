package Bank_src;

import java.time.LocalDateTime;

public abstract class Transaction {
    protected double amount;
    protected LocalDateTime timestamp;

    public Transaction(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive.");
        }
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public abstract String getType();
}
