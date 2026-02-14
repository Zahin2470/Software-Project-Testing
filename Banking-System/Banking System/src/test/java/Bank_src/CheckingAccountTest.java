// Tests: BVC (exact balance, overdraft), Robustness (negative/zero amounts)

package Bank_src;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckingAccountTest {
    private CheckingAccount account;

    @BeforeEach
    void setUp() {
        Address address = new Address("Road 10", "Dhaka", "Dhaka Division", "1207");
        ContactInfo contact = new ContactInfo("0123456789", "zahin@example.com");
        Customer customer = new Customer("C001", "Zahin", address, contact);
        account = new CheckingAccount("A001", customer, 50.0); // overdraft limit = 50
    }

    // BVC: exact balance
    @Test
    void withdraw_exact_balance_shouldResultZero() throws Exception {
        account.deposit(200);
        account.withdraw(200);
        assertEquals(0.0, account.getBalance(), 1e-6);
    }

    // BVC: exact overdraft boundary
    @Test
    void withdraw_using_full_overdraft_shouldSucceed() throws Exception {
        account.deposit(100);
        account.withdraw(150); // 100 + overdraft 50
        assertEquals(-50.0, account.getBalance(), 1e-6);
    }

    // BVC: beyond overdraft => InsufficientFundsException
    @Test
    void withdraw_beyond_overdraft_shouldThrow() {
        assertThrows(InsufficientFundsException.class, () -> {
            account.deposit(100);
            account.withdraw(151); // exceeds overdraft
        });
    }

    // Robustness: negative deposit
    @Test
    void deposit_negative_shouldThrowInvalidAmount() {
        assertThrows(InvalidAmountException.class, () -> account.deposit(-10));
    }

    @Test
    void deposit_zero_shouldThrowInvalidAmount() {
        assertThrows(InvalidAmountException.class, () -> account.deposit(0));
    }

    @Test
    void withdraw_negative_shouldThrowInvalidAmount() {
        assertThrows(InvalidAmountException.class, () -> account.withdraw(-5));
    }

    @Test
    void withdraw_zero_shouldThrowInvalidAmount() {
        assertThrows(InvalidAmountException.class, () -> account.withdraw(0));
    }
}