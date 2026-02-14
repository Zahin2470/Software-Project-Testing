// Tests: constructor validation (robustness), outstanding loan, payment reduces outstanding (BVC-ish)

package Bank_src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoanAccountTest {

    @Test
    void constructor_negativeLoan_shouldThrow() {
        Address address = new Address("R", "Dhaka", "Dhaka Division", "1200");
        ContactInfo c = new ContactInfo("0123","loan@example.com");
        Customer cust = new Customer("C010","LoanUser", address, c);

        assertThrows(IllegalArgumentException.class, () -> new LoanAccount("L1", cust, -1000, 0.05));
        assertThrows(IllegalArgumentException.class, () -> new LoanAccount("L1", cust, 1000, -0.01));
    }

    @Test
    void outstandingLoan_and_payments_shouldBehave() throws Exception {
        Address address = new Address("R", "Dhaka", "Dhaka Division", "1200");
        ContactInfo c = new ContactInfo("0123","loan@example.com");
        Customer cust = new Customer("C011","LoanUser2", address, c);

        LoanAccount la = new LoanAccount("L100", cust, 1000.0, 0.05);
        // balance should be negative (loan principal)
        assertEquals(-1000.0, la.getBalance(), 1e-6);
        assertEquals(1000.0, la.getOutstandingLoan(), 1e-6);

        // make a payment (deposit) reduces outstanding
        la.deposit(200);
        assertEquals(800.0, la.getOutstandingLoan(), 1e-6);
    }

    @Test
    void withdraw_from_loanAccount_shouldThrowInvalidAmount() {
        Address address = new Address("R", "Dhaka", "Dhaka Division", "1200");
        ContactInfo c = new ContactInfo("0123","loan@example.com");
        Customer cust = new Customer("C012","LoanUser3", address, c);

        LoanAccount la = new LoanAccount("L200", cust, 500, 0.1);
        assertThrows(InvalidAmountException.class, () -> la.withdraw(10));
    }
}
