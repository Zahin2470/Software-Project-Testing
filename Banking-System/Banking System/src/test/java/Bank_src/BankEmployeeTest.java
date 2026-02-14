// Simple constructor robustness and getter tests for BankEmployee class
package Bank_src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankEmployeeTest {

    @Test
    void constructor_nulls_shouldThrow() {
        System.out.println("Running constructor_nulls_shouldThrow");
        assertThrows(IllegalArgumentException.class, () -> new BankEmployee(null, "Name"));
        assertThrows(IllegalArgumentException.class, () -> new BankEmployee("ID", null));
        System.out.println("constructor_nulls_shouldThrow finished");
    }

    @Test
    void getters_shouldReturnValues() {
        System.out.println("Running getters_shouldReturnValues");
        BankEmployee be = new BankEmployee("E001", "Zahin");
        assertEquals("E001", be.getEmployeeId());
        assertEquals("Zahin", be.getName());
        System.out.println("getters_shouldReturnValues finished, employeeId=" + be.getEmployeeId());
    }
}