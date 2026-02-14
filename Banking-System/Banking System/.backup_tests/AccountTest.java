import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// NOTE: adjust class/method names if your source uses different names or packages.
public class AccountTest {

    // BVC: withdraw to exactly zero (boundary), then one more (invalid)
    @Test
    public void withdraw_to_zero_boundary_shouldSucceed() throws Exception {
        Account a = new Account(100.0);            // starting balance
        a.withdraw(100.0);                         // withdraw to 0
        assertEquals(0.0, a.getBalance(), 1e-9);
    }

    @Test
    public void withdraw_beyond_balance_shouldThrowInsufficientFunds() {
        Account a = new Account(100.0);
        assertThrows(InsufficientFundsException.class, () -> a.withdraw(100.01));
    }

    // Robustness: negative deposit / withdraw -> InvalidAmountException
    @Test
    public void deposit_negativeAmount_shouldThrowInvalidAmount() {
        Account a = new Account(50.0);
        assertThrows(InvalidAmountException.class, () -> a.deposit(-10.0));
    }

    @Test
    public void withdraw_negativeAmount_shouldThrowInvalidAmount() {
        Account a = new Account(50.0);
        assertThrows(InvalidAmountException.class, () -> a.withdraw(-5.0));
    }
}