// Tests: constructors reject null/blank (Robustness)

package Bank_src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerContactAddressTest {

    @Test
    void contactinfo_nulls_shouldThrow() {
        System.out.println("[TEST START] contactinfo_nulls_shouldThrow");

        assertThrows(IllegalArgumentException.class,() -> new ContactInfo(null, "abrar@gmail.com"));
        System.out.println("  -> Null phone rejected");

        assertThrows(IllegalArgumentException.class, () -> new ContactInfo("0123", null));
        System.out.println("  -> Null email rejected");

        System.out.println("[TEST PASS] contactinfo_nulls_shouldThrow");
    }

    @Test
    void address_null_fields_shouldThrow() {
        System.out.println("[TEST START] address_null_fields_shouldThrow");

        assertThrows(IllegalArgumentException.class,() -> new Address(null, "City", "State", "ZIP"));
        System.out.println("  -> Null street rejected");

        assertThrows(IllegalArgumentException.class,() -> new Address("Street", null, "State", "ZIP"));
        System.out.println("  -> Null city rejected");

        assertThrows(IllegalArgumentException.class,() -> new Address("Street", "City", "State", null));
        System.out.println("  -> Null ZIP rejected");

        System.out.println("[TEST PASS] address_null_fields_shouldThrow");
    }

    @Test
    void customer_null_or_blank_shouldThrow() {
        System.out.println("[TEST START] customer_null_or_blank_shouldThrow");

        Address address = new Address("St", "City", "State", "1000");
        ContactInfo contact = new ContactInfo("0123", "c@d.com");

        assertThrows(IllegalArgumentException.class,() -> new Customer(null, "Name", address, contact));
        System.out.println("  -> Null ID rejected");

        assertThrows(IllegalArgumentException.class,() -> new Customer("ID", "", address, contact));
        System.out.println("  -> Blank name rejected");

        assertThrows(IllegalArgumentException.class,() -> new Customer("ID", "Name", null, contact));
        System.out.println("  -> Null address rejected");

        assertThrows(IllegalArgumentException.class,() -> new Customer("ID", "Name", address, null));
        System.out.println("  -> Null contact rejected");

        System.out.println("[TEST PASS] customer_null_or_blank_shouldThrow");
    }
}
