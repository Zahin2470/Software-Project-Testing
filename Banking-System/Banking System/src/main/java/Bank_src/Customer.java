package main.java.Bank_src;

public class Customer {
    private String id;
    private String name;
    private Address address;
    private ContactInfo contactInfo;

    public Customer(String id, String name, Address address, ContactInfo contactInfo) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or blank.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or blank.");
        }
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null.");
        }
        if (contactInfo == null) {
            throw new IllegalArgumentException("Contact info cannot be null.");
        }

        this.id = id;
        this.name = name;
        this.address = address;
        this.contactInfo = contactInfo;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    // Setters
    public void setAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null.");
        }
        this.address = address;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        if (contactInfo == null) {
            throw new IllegalArgumentException("Contact info cannot be null.");
        }
        this.contactInfo = contactInfo;
    }
}
