// Tests: Transaction subclasses enforce positive amounts (Robustness) and types (Decision)

package Bank_src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    void deposit_and_withdrawal_creation_shouldWork() {
        System.out.println("[TEST START] deposit_and_withdrawal_creation_shouldWork");

        Deposit d = new Deposit(100.0);
        System.out.println("  -> Created Deposit with amount = " + d.getAmount());
        assertEquals(100.0, d.getAmount(), 1e-6);
        assertEquals("Deposit", d.getType());
        System.out.println("  -> Deposit type = " + d.getType());

        Withdrawal w = new Withdrawal(50.0);
        System.out.println("  -> Created Withdrawal with amount = " + w.getAmount());
        assertEquals(50.0, w.getAmount(), 1e-6);
        assertEquals("Withdrawal", w.getType());
        System.out.println("  -> Withdrawal type = " + w.getType());

        System.out.println("[TEST PASS] deposit_and_withdrawal_creation_shouldWork");
    }

    @Test
    void transaction_nonPositiveAmount_shouldThrow() {
        System.out.println("[TEST START] transaction_nonPositiveAmount_shouldThrow");

        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("  -> Attempting Deposit(0)");
            new Deposit(0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("  -> Attempting Withdrawal(-5)");
            new Withdrawal(-5);
        });

        System.out.println("[TEST PASS] transaction_nonPositiveAmount_shouldThrow");
    }

    @Test
    void transaction_toString_containsAmountAndType() {
        System.out.println("[TEST START] transaction_toString_containsAmountAndType");

        Deposit d = new Deposit(123.45);
        String s = d.toString();

        System.out.println("  -> toString() output: " + s);

        assertTrue(s.contains("Deposit"));
        assertTrue(s.contains("123.45") || s.contains("123.45"));

        System.out.println("[TEST PASS] transaction_toString_containsAmountAndType");
    }
}
