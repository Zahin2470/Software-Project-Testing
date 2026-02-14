// Tests: constructor validation (robustness), outstanding loan, payment reduces outstanding (BVC-ish)

package Bank_src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoanAccountTest {

    @Test
    void constructor_negativeLoan_shouldThrow() {
        System.out.println("[TEST START] constructor_negativeLoan_shouldThrow");
        Address address = new Address("R", "Dhaka", "Dhaka Division", "1200");
        ContactInfo c = new ContactInfo("0123","loan@example.com");
        Customer cust = new Customer("C010","LoanUser", address, c);

        assertThrows(IllegalArgumentException.class, () -> new LoanAccount("L1", cust, -1000, 0.05));
        System.out.println("  -> Negative principal correctly rejected");

        assertThrows(IllegalArgumentException.class, () -> new LoanAccount("L1", cust, 1000, -0.01));
        System.out.println("  -> Negative interest rate correctly rejected");

        System.out.println("[TEST PASS] constructor_negativeLoan_shouldThrow");
    }

    @Test
    void outstandingLoan_and_payments_shouldBehave() throws Exception {
        System.out.println("[TEST START] outstandingLoan_and_payments_shouldBehave");
        Address address = new Address("R", "Dhaka", "Dhaka Division", "1200");
        ContactInfo c = new ContactInfo("0123","loan@example.com");
        Customer cust = new Customer("C011","LoanUser2", address, c);

        LoanAccount la = new LoanAccount("L100", cust, 1000.0, 0.05);
        System.out.println("  -> Created LoanAccount L100 with principal=1000.0 and rate=0.05");
        System.out.println("  -> getBalance() = " + la.getBalance());
        System.out.println("  -> getOutstandingLoan() = " + la.getOutstandingLoan());

        // balance should be negative (loan principal)
        assertEquals(-1000.0, la.getBalance(), 1e-6);
        assertEquals(1000.0, la.getOutstandingLoan(), 1e-6);
        System.out.println("  -> initial assertions passed");

        // make a payment (deposit) reduces outstanding
        la.deposit(200);
        System.out.println("  -> Deposited 200 to loan account");
        System.out.println("  -> getOutstandingLoan() after payment = " + la.getOutstandingLoan());

        assertEquals(800.0, la.getOutstandingLoan(), 1e-6);
        System.out.println("[TEST PASS] outstandingLoan_and_payments_shouldBehave - outstanding reduced to " + la.getOutstandingLoan());
    }

    @Test
    void withdraw_from_loanAccount_shouldThrowInvalidAmount() {
        System.out.println("[TEST START] withdraw_from_loanAccount_shouldThrowInvalidAmount");
        Address address = new Address("R", "Dhaka", "Dhaka Division", "1200");
        ContactInfo c = new ContactInfo("0123","loan@example.com");
        Customer cust = new Customer("C012","LoanUser3", address, c);

        LoanAccount la = new LoanAccount("L200", cust, 500, 0.1);
        System.out.println("  -> Created LoanAccount L200 with principal=500.0");

        assertThrows(InvalidAmountException.class, () -> la.withdraw(10));
        System.out.println("  -> withdraw(10) correctly threw InvalidAmountException");

        System.out.println("[TEST PASS] withdraw_from_loanAccount_shouldThrowInvalidAmount");
    }
}
