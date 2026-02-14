// Simple constructor robustness

package Bank_src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankEmployeeTest {

    @Test
    void constructor_nulls_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new BankEmployee(null, "Name"));
        assertThrows(IllegalArgumentException.class, () -> new BankEmployee("ID", null));
    }

    @Test
    void getters_shouldReturnValues() {
        BankEmployee be = new BankEmployee("E001", "Alice");
        assertEquals("E001", be.getEmployeeId());
        assertEquals("Alice", be.getName());
    }
}
