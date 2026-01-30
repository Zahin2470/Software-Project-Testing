import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import packages.system.InputValidator;
import packages.system.StudentSystem;
import java.util.Scanner;

public class InputValidatorTest {

    private StudentSystem ss;

    @BeforeEach
    void setup() {
        ss = new StudentSystem();
    }

    @Test
    void testValidGPAInput() {
        Scanner sc = new Scanner("3.75\n");
        double gpa = InputValidator.inputValidGPA(sc);
        Assertions.assertEquals(3.75, gpa, 1e-9);
    }

    @Test
    void testInvalidGPAThenValid() {
        // invalid values first: -1 and 5 (out of typical 0-4 range), then a valid 3.2
        Scanner sc = new Scanner("-1\n5\n3.2\n");
        double gpa = InputValidator.inputValidGPA(sc);
        Assertions.assertEquals(3.2, gpa, 1e-9);
    }

    @Test
    void testValidChoice() {
        // simple valid integer choice
        Scanner sc = new Scanner("2\n");
        int choice = InputValidator.inputValidChoice(sc);
        Assertions.assertEquals(2, choice);
    }

    @Test
    void testInvalidChoiceThenValid() {
        // feed invalid tokens before a valid numeric choice
        Scanner sc = new Scanner("abc\n-5\n3\n");
        int choice = InputValidator.inputValidChoice(sc);
        Assertions.assertEquals(3, choice);
    }

    @Test
    void testAddUniqueNameWhenDuplicateThenNew() {
        // Pre-populate StudentSystem with an existing name to force uniqueness check
        ss.addStudent("Existing", 1001, 3.0, "Third", "CSE", false);

        // Provide name "Existing" first (will be rejected), then "NewName"
        Scanner sc = new Scanner("Existing\nNewName\n");
        String name = InputValidator.addUniqueName(sc, ss);
        Assertions.assertEquals("NewName", name);
    }

    @Test
    void testAddUniqueIDWhenDuplicateThenNew() {
        // Pre-populate StudentSystem with an ID to force uniqueness check
        ss.addStudent("Someone", 2001, 3.1, "Third", "EEE", false);

        // Provide duplicate ID 2001 then 3001
        Scanner sc = new Scanner("2001\n3001\n");
        int id = InputValidator.addUniqueID(sc, ss);
        Assertions.assertEquals(3001, id);
    }

    @Test
    void testInputValidYear() {
        // valid year
        Scanner sc = new Scanner("Third\n");
        String year = InputValidator.inputValidYear(sc);
        Assertions.assertEquals("Third", year);

        // invalid then valid
        Scanner sc2 = new Scanner("NotAYear\nSecond\n");
        String year2 = InputValidator.inputValidYear(sc2);
        Assertions.assertEquals("Second", year2);
    }

    @Test
    void testInputValidDepartment() {
        // check basic acceptance of allowed department strings
        Scanner sc = new Scanner("CS\n");
        String dept = InputValidator.inputValidDepartment(sc);
        Assertions.assertEquals("CS", dept);

        // test another allowed department
        Scanner sc2 = new Scanner("IS\n");
        String dept2 = InputValidator.inputValidDepartment(sc2);
        Assertions.assertEquals("IS", dept2);
    }

    @Test
    void testInputValidID() {
        // invalid token then valid id
        Scanner sc = new Scanner("abc\n500\n");
        int id = InputValidator.inputValidID(sc);
        Assertions.assertEquals(500, id);
    }
}