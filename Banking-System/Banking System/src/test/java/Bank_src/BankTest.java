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
        System.out.println("[SETUP] Preparing bank, customer and account for test...");
        bank = new Bank("MyBank");
        Address address = new Address("Road 1","Dhaka","Dhaka Division","1200");
        ContactInfo ci = new ContactInfo("0173456789","abrarhossain@gmail.com");
        c = new Customer("CU1","Abrar Hossain Zahin", address, ci);
        acc = new CheckingAccount("AC1", c, 0.0);
    }

    // Decision table row: Valid register + open account
    @Test
    void registerCustomer_and_openAccount_valid_shouldSucceed() {
        System.out.println("[TEST START] registerCustomer_and_openAccount_valid_shouldSucceed");
        bank.registerCustomer(c);
        System.out.println("  -> Registered customer " + c.getId());
        assertEquals(1, bank.getTotalCustomers(), "Total customers should be 1 after register");
        bank.openAccount(acc);
        System.out.println("  -> Opened account " + acc.getAccountNumber());
        assertEquals(1, bank.getTotalAccounts(), "Total accounts should be 1 after openAccount");
        Account fetched = bank.getAccount("AC1");
        assertEquals("AC1", fetched.getAccountNumber(), "Fetched account number should match");
        System.out.println("[TEST PASS] registerCustomer_and_openAccount_valid_shouldSucceed - fetched=" + fetched.getAccountNumber());
    }

    // Decision table row: register duplicate -> IllegalArgumentException
    @Test
    void register_duplicateCustomer_shouldThrow() {
        System.out.println("[TEST START] register_duplicateCustomer_shouldThrow");
        bank.registerCustomer(c);
        System.out.println("  -> First registration done for " + c.getId());
        assertThrows(IllegalArgumentException.class, () -> bank.registerCustomer(c));
        System.out.println("[TEST PASS] register_duplicateCustomer_shouldThrow - duplicate registration threw IllegalArgumentException");
    }

    // Decision table row: getCustomer not found -> NoSuchElementException
    @Test
    void getCustomer_notFound_shouldThrow() {
        System.out.println("[TEST START] getCustomer_notFound_shouldThrow");
        assertThrows(NoSuchElementException.class, () -> bank.getCustomer("doesnotexist"));
        System.out.println("[TEST PASS] getCustomer_notFound_shouldThrow - NoSuchElementException thrown as expected");
    }

    // Decision table row: getAccountsByCustomer returns list
    @Test
    void getAccountsByCustomer_shouldReturnList() {
        System.out.println("[TEST START] getAccountsByCustomer_shouldReturnList");
        bank.registerCustomer(c);
        bank.openAccount(acc);
        List<Account> accounts = bank.getAccountsByCustomer("CU1");
        assertEquals(1, accounts.size(), "Customer CU1 should have 1 account");
        System.out.println("[TEST PASS] getAccountsByCustomer_shouldReturnList - accounts.size=" + accounts.size());
    }

    // Robustness: open null account -> IllegalArgumentException
    @Test
    void open_nullAccount_shouldThrow() {
        System.out.println("[TEST START] open_nullAccount_shouldThrow");
        assertThrows(IllegalArgumentException.class, () -> bank.openAccount(null));
        System.out.println("[TEST PASS] open_nullAccount_shouldThrow - opening null account threw IllegalArgumentException");
    }
}
