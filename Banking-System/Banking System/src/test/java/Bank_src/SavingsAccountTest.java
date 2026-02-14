// Tests: BVC (withdraw equal to balance), Robustness (negative deposit), Behavioral (interest)

package Bank_src;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccountTest {
    private SavingsAccount s;

    @BeforeEach
    void init() {
        Address address = new Address("Road 20", "Dhaka", "Dhaka Division", "1212");
        ContactInfo contact = new ContactInfo("01999999999", "sav@example.com");
        Customer customer = new Customer("C002", "Suman", address, contact);
        s = new SavingsAccount("S001", customer, 0.05); // interest 5%
    }

    // BVC: withdraw exactly current balance
    @Test
    void withdraw_exact_balance_shouldSucceed() throws Exception {
        s.deposit(500);
        s.withdraw(500);
        assertEquals(0.0, s.getBalance(), 1e-6);
    }

    // BVC: withdraw slightly more than balance -> InsufficientFundsException
    @Test
    void withdraw_more_than_balance_shouldThrow() {
        assertThrows(InsufficientFundsException.class, () -> {
            s.deposit(100);
            s.withdraw(100.01);
        });
    }

    // Robustness: negative deposit
    @Test
    void deposit_negative_shouldThrow() {
        assertThrows(InvalidAmountException.class, () -> s.deposit(-1));
    }

    // Behavior: interest calculation
    @Test
    void calculateInterest_shouldReturnBalanceTimesRate() throws Exception {
        s.deposit(1000);
        double interest = s.calculateInterest();
        assertEquals(1000 * 0.05, interest, 1e-6);
    }
}
