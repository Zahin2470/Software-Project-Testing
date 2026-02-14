package Bank_src;

public class Address {
    private String street;
    private String city;
    private String state;
    private String zip;

    public Address(String street, String city, String state, String zip) {
        if (street == null || city == null || state == null || zip == null) {
            throw new IllegalArgumentException("Address fields cannot be null.");
        }
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getFullAddress() {
        return street + ", " + city + ", " + state + " " + zip;
    }

    // Getters
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getZip() { return zip; }
}
