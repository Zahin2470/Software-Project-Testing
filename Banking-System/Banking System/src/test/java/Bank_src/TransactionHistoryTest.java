// Tests: addTransaction ignores null (Robustness), stores transactions, printHistory works (no throwing)

package Bank_src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class TransactionHistoryTest {

    @Test
    void add_null_transaction_shouldBeIgnored() {
        TransactionHistory th = new TransactionHistory();
        th.addTransaction(null);
        List transactions = th.getTransactions();
        assertNotNull(transactions);
        assertEquals(0, transactions.size());
    }

    @Test
    void add_and_get_transactions_shouldReturnList() {
        TransactionHistory th = new TransactionHistory();
        th.addTransaction(new Deposit(50));
        th.addTransaction(new Withdrawal(10));
        List transactions = th.getTransactions();
        assertEquals(2, transactions.size());
    }

    @Test
    void printHistory_shouldNotThrow() {
        TransactionHistory th = new TransactionHistory();
        th.addTransaction(new Deposit(25));
        th.printHistory(); // just ensure no exception (side-effect: prints to console)
    }
}