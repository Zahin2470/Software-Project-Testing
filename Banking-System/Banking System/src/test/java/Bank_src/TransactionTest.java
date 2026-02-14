// Tests: Transaction subclasses enforce positive amounts (Robustness) and types (Decision)

package Bank_src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    void deposit_and_withdrawal_creation_shouldWork() {
        Deposit d = new Deposit(100.0);
        assertEquals(100.0, d.getAmount(), 1e-6);
        assertEquals("Deposit", d.getType());

        Withdrawal w = new Withdrawal(50.0);
        assertEquals(50.0, w.getAmount(), 1e-6);
        assertEquals("Withdrawal", w.getType());
    }

    @Test
    void transaction_nonPositiveAmount_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new Deposit(0));
        assertThrows(IllegalArgumentException.class, () -> new Withdrawal(-5));
    }

    @Test
    void transaction_toString_containsAmountAndType() {
        Deposit d = new Deposit(123.45);
        String s = d.toString();
        assertTrue(s.contains("Deposit"));
        assertTrue(s.contains("123.45") || s.contains("123.45")); // numeric representation check
    }
}
