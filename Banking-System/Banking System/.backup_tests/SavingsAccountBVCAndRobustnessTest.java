import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccountBVCAndRobustnessTest {

    // Assume SavingsAccount has minBalance requirement, e.g., minBalance = 100.
    // BVC: test exactly min, just below min, and just above min.
    @Test
    public void savings_minBalance_boundaryTests() throws Exception {
        SavingsAccount s1 = new SavingsAccount(100.0); // exactly min
        assertTrue(s1.isValid()); // or check whatever validates account

        SavingsAccount s2 = new SavingsAccount(99.99);
        // either construction fails or some method indicates invalid; adapt:
        assertThrows(InvalidAmountException.class, () -> new SavingsAccount(99.99));
    }

    // Robustness: passing nulls (if constructor takes ContactInfo)
    @Test
    public void constructor_nullContactInfo_shouldThrow() {
        assertThrows(NullPointerException.class, () -> new SavingsAccount(200.0, null));
    }
}
