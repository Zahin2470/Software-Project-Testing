package Bank_src;

public class ContactInfo {
    private String phone;
    private String email;

    public ContactInfo(String phone, String email) {
        if (phone == null || email == null) {
            throw new IllegalArgumentException("Phone and email cannot be null.");
        }
        this.phone = phone;
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
