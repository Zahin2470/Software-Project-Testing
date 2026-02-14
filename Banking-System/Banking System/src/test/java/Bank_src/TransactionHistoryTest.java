// Tests: addTransaction ignores null (Robustness), stores transactions, printHistory works (no throwing)

package Bank_src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class TransactionHistoryTest {

    @Test
    void add_null_transaction_shouldBeIgnored() {
        System.out.println("[TEST START] add_null_transaction_shouldBeIgnored");

        TransactionHistory th = new TransactionHistory();
        th.addTransaction(null);
        System.out.println("  -> Attempted to add null transaction");

        List<Transaction> transactions = th.getTransactions();
        System.out.println("  -> Current transaction count = " + transactions.size());

        assertNotNull(transactions);
        assertEquals(0, transactions.size());

        System.out.println("[TEST PASS] Null transaction correctly ignored");
    }

    @Test
    void add_and_get_transactions_shouldReturnList() {
        System.out.println("[TEST START] add_and_get_transactions_shouldReturnList");

        TransactionHistory th = new TransactionHistory();
        th.addTransaction(new Deposit(50));
        System.out.println("  -> Added Deposit(50)");
        th.addTransaction(new Withdrawal(10));
        System.out.println("  -> Added Withdrawal(10)");

        List<Transaction> transactions = th.getTransactions();
        System.out.println("  -> Current transaction count = " + transactions.size());

        assertEquals(2, transactions.size());

        System.out.println("[TEST PASS] Transactions stored and retrieved correctly");
    }

    @Test
    void printHistory_shouldNotThrow() {
        System.out.println("[TEST START] printHistory_shouldNotThrow");

        TransactionHistory th = new TransactionHistory();
        th.addTransaction(new Deposit(25));
        System.out.println("  -> Added Deposit(25)");

        assertDoesNotThrow(() -> th.printHistory());
        System.out.println("  -> printHistory() executed without exception");

        System.out.println("[TEST PASS] printHistory_shouldNotThrow");
    }
}
