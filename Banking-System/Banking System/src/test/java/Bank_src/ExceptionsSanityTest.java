// Make sure your custom exceptions behave as expected (constructible, subclass of Exception).

package Bank_src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExceptionsSanityTest {
    @Test
    void invalidAmountException_is_instantiable() {
        System.out.println("[TEST START] invalidAmountException_is_instantiable");
        InvalidAmountException e = new InvalidAmountException("msg");
        assertNotNull(e.getMessage());
        System.out.println("[TEST PASS] invalidAmountException_is_instantiable - message: " + e.getMessage());
    }

    @Test
    void insufficientFundsException() {
        System.out.println("[TEST START] insufficientFundsException");
        InsufficientFundsException e = new InsufficientFundsException("no funds");
        assertNotNull(e.getMessage());
        System.out.println("[TEST PASS] insufficientFundsException - message: " + e.getMessage());
    }
}