package Bank_src;

public class BankEmployee {
    private String employeeId;
    private String name;

    public BankEmployee(String employeeId, String name) {
        if (employeeId == null || name == null) {
            throw new IllegalArgumentException("Employee ID and name cannot be null.");
        }
        this.employeeId = employeeId;
        this.name = name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }
}
