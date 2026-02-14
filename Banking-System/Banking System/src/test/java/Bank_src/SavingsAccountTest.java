// Tests: BVC (withdraw equal to balance), Robustness (negative deposit), Behavioral (interest)

package Bank_src;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccountTest {
    private SavingsAccount s;

    @BeforeEach
    void init() {
        System.out.println("[SETUP] Creating SavingsAccount with interest rate 5%");
        Address address = new Address("Road 20", "Dhaka", "Dhaka Division", "1212");
        ContactInfo contact = new ContactInfo("01999999999", "sav@example.com");
        Customer customer = new Customer("C002", "Suman", address, contact);
        s = new SavingsAccount("S001", customer, 0.05); // interest 5%
    }

    // BVC: withdraw exactly current balance
    @Test
    void withdraw_exact_balance_shouldSucceed() throws Exception {
        System.out.println("[TEST START] withdraw_exact_balance_shouldSucceed");
        s.deposit(500);
        System.out.println("  -> Deposited 500");
        s.withdraw(500);
        System.out.println("  -> Withdrew 500");
        assertEquals(0.0, s.getBalance(), 1e-6);
        System.out.println("[TEST PASS] Balance after withdrawal = " + s.getBalance());
    }

    // BVC: withdraw slightly more than balance
    @Test
    void withdraw_more_than_balance_shouldThrow() {
        System.out.println("[TEST START] withdraw_more_than_balance_shouldThrow");
        assertThrows(InsufficientFundsException.class, () -> {
            s.deposit(100);
            System.out.println("  -> Deposited 100");
            s.withdraw(100.01);
        });
        System.out.println("[TEST PASS] InsufficientFundsException thrown as expected");
    }

    // Robustness: negative deposit
    @Test
    void deposit_negative_shouldThrow() {
        System.out.println("[TEST START] deposit_negative_shouldThrow");
        assertThrows(InvalidAmountException.class, () -> s.deposit(-1));
        System.out.println("[TEST PASS] InvalidAmountException thrown for negative deposit");
    }

    // Behavior: interest calculation
    @Test
    void calculateInterest_shouldReturnBalanceTimesRate() throws Exception {
        System.out.println("[TEST START] calculateInterest_shouldReturnBalanceTimesRate");
        s.deposit(1000);
        System.out.println("  -> Deposited 1000");
        double interest = s.calculateInterest();
        System.out.println("  -> Calculated interest = " + interest);
        assertEquals(1000 * 0.05, interest, 1e-6);
        System.out.println("[TEST PASS] Interest correctly calculated");
    }
}
