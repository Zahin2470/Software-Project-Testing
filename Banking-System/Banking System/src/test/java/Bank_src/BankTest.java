// Decision-table style tests for registering/opening/getting customers & accounts

package Bank_src;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.NoSuchElementException;

public class BankTest {
    private Bank bank;
    private Customer c;
    private CheckingAccount acc;

    @BeforeEach
    void setUp() {
        bank = new Bank("MyBank");
        Address address = new Address("Road 1","Dhaka","Dhaka Division","1200");
        ContactInfo ci = new ContactInfo("0123456789","a@b.com");
        c = new Customer("CU1","Name", address, ci);
        acc = new CheckingAccount("AC1", c, 0.0);
    }

    // Decision table row: Valid register + open account
    @Test
    void registerCustomer_and_openAccount_valid_shouldSucceed() {
        bank.registerCustomer(c);
        assertEquals(1, bank.getTotalCustomers());
        bank.openAccount(acc);
        assertEquals(1, bank.getTotalAccounts());
        Account fetched = bank.getAccount("AC1");
        assertEquals("AC1", fetched.getAccountNumber());
    }

    // Decision table row: register duplicate -> IllegalArgumentException
    @Test
    void register_duplicateCustomer_shouldThrow() {
        bank.registerCustomer(c);
        assertThrows(IllegalArgumentException.class, () -> bank.registerCustomer(c));
    }

    // Decision table row: getCustomer not found -> NoSuchElementException
    @Test
    void getCustomer_notFound_shouldThrow() {
        assertThrows(NoSuchElementException.class, () -> bank.getCustomer("doesnotexist"));
    }

    // Decision table row: getAccountsByCustomer returns list
    @Test
    void getAccountsByCustomer_shouldReturnList() {
        bank.registerCustomer(c);
        bank.openAccount(acc);
        List accounts = bank.getAccountsByCustomer("CU1");
        assertEquals(1, accounts.size());
    }

    // Robustness: open null account -> IllegalArgumentException
    @Test
    void open_nullAccount_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> bank.openAccount(null));
    }
}