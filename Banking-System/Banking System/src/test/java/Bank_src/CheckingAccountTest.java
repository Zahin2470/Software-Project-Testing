// Tests: BVC (exact balance, overdraft), Robustness (negative/zero amounts)

package Bank_src;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckingAccountTest {
    private CheckingAccount account;

    @BeforeEach
    void setUp() {
        System.out.println("[SETUP] Creating CheckingAccount with overdraft 50.0");
        Address address = new Address("Road 10", "Dhaka", "Dhaka Division", "1207");
        ContactInfo contact = new ContactInfo("0123456789", "zahin@example.com");
        Customer customer = new Customer("C001", "Zahin", address, contact);
        account = new CheckingAccount("A001", customer, 50.0);
    }

    // BVC: exact balance
    @Test
    void withdraw_exact_balance_shouldResultZero() throws Exception {
        System.out.println("[TEST START] withdraw_exact_balance_shouldResultZero");
        account.deposit(200);
        System.out.println("  -> Deposited 200");
        account.withdraw(200);
        System.out.println("  -> Withdrew 200");
        assertEquals(0.0, account.getBalance(), 1e-6);
        System.out.println("[TEST PASS] Balance after withdrawal = " + account.getBalance());
    }

    // BVC: exact overdraft boundary
    @Test
    void withdraw_using_full_overdraft_shouldSucceed() throws Exception {
        System.out.println("[TEST START] withdraw_using_full_overdraft_shouldSucceed");
        account.deposit(100);
        System.out.println("  -> Deposited 100");
        account.withdraw(150);
        System.out.println("  -> Withdrew 150 (including overdraft)");
        assertEquals(-50.0, account.getBalance(), 1e-6);
        System.out.println("[TEST PASS] Balance after overdraft = " + account.getBalance());
    }

    // BVC: beyond overdraft
    @Test
    void withdraw_beyond_overdraft_shouldThrow() {
        System.out.println("[TEST START] withdraw_beyond_overdraft_shouldThrow");
        assertThrows(InsufficientFundsException.class, () -> {
            account.deposit(100);
            System.out.println("  -> Deposited 100");
            account.withdraw(151);
        });
        System.out.println("[TEST PASS] InsufficientFundsException thrown as expected");
    }

    // Robustness: negative deposit
    @Test
    void deposit_negative_shouldThrowInvalidAmount() {
        System.out.println("[TEST START] deposit_negative_shouldThrowInvalidAmount");
        assertThrows(InvalidAmountException.class, () -> account.deposit(-10));
        System.out.println("[TEST PASS] InvalidAmountException thrown for negative deposit");
    }

    @Test
    void deposit_zero_shouldThrowInvalidAmount() {
        System.out.println("[TEST START] deposit_zero_shouldThrowInvalidAmount");
        assertThrows(InvalidAmountException.class, () -> account.deposit(0));
        System.out.println("[TEST PASS] InvalidAmountException thrown for zero deposit");
    }

    @Test
    void withdraw_negative_shouldThrowInvalidAmount() {
        System.out.println("[TEST START] withdraw_negative_shouldThrowInvalidAmount");
        assertThrows(InvalidAmountException.class, () -> account.withdraw(-5));
        System.out.println("[TEST PASS] InvalidAmountException thrown for negative withdrawal");
    }

    @Test
    void withdraw_zero_shouldThrowInvalidAmount() {
        System.out.println("[TEST START] withdraw_zero_shouldThrowInvalidAmount");
        assertThrows(InvalidAmountException.class, () -> account.withdraw(0));
        System.out.println("[TEST PASS] InvalidAmountException thrown for zero withdrawal");
    }
}
