// Tests: constructors reject null/blank (Robustness)

package Bank_src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerContactAddressTest {

    @Test
    void contactinfo_nulls_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new ContactInfo(null, "a@b.com"));
        assertThrows(IllegalArgumentException.class, () -> new ContactInfo("0123", null));
    }

    @Test
    void address_null_fields_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new Address(null, "City", "State", "ZIP"));
        assertThrows(IllegalArgumentException.class, () -> new Address("Street", null, "State", "ZIP"));
        assertThrows(IllegalArgumentException.class, () -> new Address("Street", "City", "State", null));
    }

    @Test
    void customer_null_or_blank_shouldThrow() {
        Address address = new Address("St", "City", "State", "1000");
        ContactInfo contact = new ContactInfo("0123", "c@d.com");
        assertThrows(IllegalArgumentException.class, () -> new Customer(null, "Name", address, contact));
        assertThrows(IllegalArgumentException.class, () -> new Customer("ID", "", address, contact));
        assertThrows(IllegalArgumentException.class, () -> new Customer("ID", "Name", null, contact));
        assertThrows(IllegalArgumentException.class, () -> new Customer("ID", "Name", address, null));
    }
}
