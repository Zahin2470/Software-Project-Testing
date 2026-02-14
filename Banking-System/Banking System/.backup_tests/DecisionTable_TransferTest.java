import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 Decision table for transfer(from, to, amount):
 Conditions:
  C1: fromBalance >= amount (Y/N)
  C2: amount > 0 (Y/N)
  C3: to != null (Y/N)

 Actions:
  A1: transfer succeeds and balances updated
  A2: throw InsufficientFundsException
  A3: throw InvalidAmountException
  A4: throw NullPointerException

 We'll implement tests for important rows.
*/

public class DecisionTable_TransferTest {

    @Test
    public void transfer_validConditions_shouldSucceed() throws Exception {
        Account from = new Account(500.0);
        Account to = new Account(50.0);
        from.transferTo(to, 100.0);
        assertEquals(400.0, from.getBalance(), 1e-9);
        assertEquals(150.0, to.getBalance(), 1e-9);
    }

    @Test
    public void transfer_insufficientFunds_shouldThrow() {
        Account from = new Account(50.0);
        Account to = new Account(30.0);
        assertThrows(InsufficientFundsException.class, () -> from.transferTo(to, 100.0));
    }

    @Test
    public void transfer_negativeAmount_shouldThrowInvalidAmount() {
        Account from = new Account(500.0);
        Account to = new Account(100.0);
        assertThrows(InvalidAmountException.class, () -> from.transferTo(to, -10.0));
    }

    @Test
    public void transfer_toNull_shouldThrowNullPointer() {
        Account from = new Account(500.0);
        assertThrows(NullPointerException.class, () -> from.transferTo(null, 10.0));
    }
}