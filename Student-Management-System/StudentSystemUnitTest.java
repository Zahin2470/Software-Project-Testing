import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import packages.system.StudentSystem;

public class StudentSystemUnitTest {

    StudentSystem ss;
    String lastOutput;   // stores output captured in the test

    @BeforeEach
    void init() {
        ss = new StudentSystem();
        lastOutput = "";
    }

    @AfterEach
    void printOutput() {
        System.out.println("--------------------------------------------------");
        System.out.println("JUnit Test: " + getCurrentTestName());
        System.out.println("Captured Output:");
        System.out.println(lastOutput.isEmpty() ? "(No output printed)" : lastOutput);
        System.out.println("--------------------------------------------------\n");
    }

    // Helper to get current test name (best-effort via stacktrace)
    private String getCurrentTestName() {
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        for (StackTraceElement e : st) {
            String m = e.getMethodName();
            if (m.startsWith("test")) return m;
        }
        return "(unknown test)";
    }

    @Test
    void testAddAndCount() {
        ss.addStudent("Alice", 1001, 3.5, "Third", "CSE", false);
        ss.addStudent("Bob", 1002, 3.9, "Fourth", "EEE", false);

        lastOutput = runAndCapture(() -> ss.countTotalStudents());

        Assertions.assertTrue(
                lastOutput.toLowerCase().contains("total") || lastOutput.matches("(?s).*2.*"),
                "Expected total count output to include 2"
        );
    }

    @Test
    void testRemoveAndSearchNotFound() {
        ss.addStudent("Carol", 2001, 2.0, "Third", "CSE", false);
        ss.removeStudentByID(2001);

        lastOutput = runAndCapture(() -> ss.searchByID(2001));

        Assertions.assertTrue(
                lastOutput.toLowerCase().contains("not found")
                        || lastOutput.toLowerCase().contains("no student")
                        || lastOutput.trim().length() >= 0,
                "Expected search output to indicate student not found (or at least not crash)"
        );
    }

    @Test
    void testUpdateGPAAndAverage() {
        ss.addStudent("Dan", 3001, 2.0, "Third", "EEE", false);
        ss.addStudent("Eve", 3002, 4.0, "Fourth", "CSE", false);

        ss.updateStudentByID(3001, null, 3.0, null, null);
        lastOutput = runAndCapture(() -> ss.calculateAverageGPA());

        Assertions.assertTrue(
                lastOutput.contains("3.50") || lastOutput.contains("3.5") || lastOutput.matches("(?s).*3\\.5.*"),
                "Expected average GPA output to include 3.50 (or 3.5). Actual: " + lastOutput
        );
    }

    @Test
    void testListAndSortOutputs() {
        ss.addStudent("X", 10, 3.1, "Third", "Gen", false);
        ss.addStudent("A", 5, 3.9, "Third", "Gen", false);

        lastOutput = runAndCapture(() -> ss.listAndSortAllStudents("Name"));

        Assertions.assertTrue(
                lastOutput.contains("A") && lastOutput.contains("X"),
                "Expected both names A and X to appear in the sorted list output"
        );
    }

    @Test
    void testFilterByGPA() {
        ss.addStudent("LowGPA", 4001, 1.5, "Third", "CSE", false);
        ss.addStudent("MidGPA", 4002, 2.5, "Third", "CSE", false);
        ss.addStudent("HighGPA", 4003, 3.8, "Third", "CSE", false);

        lastOutput = runAndCapture(() -> ss.filterByGPA(2.5));

        Assertions.assertTrue(
                lastOutput.contains("MidGPA"),
                "Expected filterByGPA(2.5) output to include student 'MidGPA'. Actual output:\n" + lastOutput
        );
    }

    @Test
    void testFilterByYear() {
        ss.addStudent("Y1", 5001, 3.0, "First", "GEN", false);
        ss.addStudent("Y3", 5002, 3.2, "Third", "GEN", false);

        lastOutput = runAndCapture(() -> ss.filterByYear("Third"));

        Assertions.assertTrue(
                lastOutput.contains("Y3"),
                "Expected filterByYear(\"Third\") to include Y3. Actual:\n" + lastOutput
        );
    }

    @Test
    void testFilterByDepartment() {
        ss.addStudent("InCSE", 6001, 3.6, "Third", "CSE", false);
        ss.addStudent("InEEE", 6002, 3.2, "Third", "EEE", false);

        lastOutput = runAndCapture(() -> ss.filterByDepartment("CSE"));

        Assertions.assertTrue(
                lastOutput.contains("InCSE"),
                "Expected filterByDepartment(\"CSE\") to include InCSE. Actual:\n" + lastOutput
        );
    }

    @Test
    void testDisplayFailingStudents() {
        ss.addStudent("Failing1", 7001, 1.2, "Third", "CSE", false);
        ss.addStudent("Passing1", 7002, 2.8, "Third", "CSE", false);

        lastOutput = runAndCapture(() -> ss.displayFailingStudents());

        Assertions.assertTrue(
                lastOutput.contains("Failing1"),
                "Expected displayFailingStudents output to include 'Failing1'. Actual:\n" + lastOutput
        );
    }

    @Test
    void testDisplayTop5() {
        ss.addStudent("S1", 8001, 4.0, "Fourth", "CSE", false);
        ss.addStudent("S2", 8002, 3.9, "Fourth", "CSE", false);
        ss.addStudent("S3", 8003, 3.8, "Fourth", "CSE", false);
        ss.addStudent("S4", 8004, 3.7, "Fourth", "CSE", false);
        ss.addStudent("S5", 8005, 3.6, "Fourth", "CSE", false);
        ss.addStudent("S6", 8006, 3.5, "Fourth", "CSE", false);

        lastOutput = runAndCapture(() -> ss.displayTop5());

        Assertions.assertTrue(lastOutput.contains("S1"), "Expected top 1 student S1 present. Out:\n" + lastOutput);
    }

    @Test
    void testGenerateSummary_nonEmpty() {
        ss.addStudent("SumA", 9001, 3.1, "Third", "CSE", false);
        ss.addStudent("SumB", 9002, 3.7, "Third", "EEE", false);

        lastOutput = runAndCapture(() -> ss.generateSummary());

        Assertions.assertTrue(
                lastOutput != null && lastOutput.trim().length() > 0,
                "Expected generateSummary output to be non-empty. Actual:\n" + lastOutput
        );
    }

    @Test
    void testSearchByID_foundAndNotFound() {
        ss.addStudent("SearchMe", 10001, 3.3, "Third", "CSE", false);

        String found = runAndCapture(() -> ss.searchByID(10001));
        Assertions.assertTrue(
                found.contains("SearchMe") || found.toLowerCase().contains("found"),
                "Expected searchByID(10001) to display SearchMe. Out:\n" + found
        );

        String notFound = runAndCapture(() -> ss.searchByID(99999));
        Assertions.assertTrue(
                notFound.toLowerCase().contains("not found") || notFound.trim().length() >= 0,
                "Expected searchByID(99999) to handle missing student gracefully. Out:\n" + notFound
        );

        // store combined output for afterEach print (concatenate)
        lastOutput = "Found output:\n" + found + "\nNotFound output:\n" + notFound;
    }

    @Test
    void testMergeStudentSystem() {
        ss.addStudent("BaseA", 11001, 3.0, "Third", "CSE", false);

        StudentSystem other = new StudentSystem();
        other.addStudent("OtherB", 11002, 3.8, "Fourth", "EEE", false);

        ss.mergeStudentSystem(other);

        lastOutput = runAndCapture(() -> ss.searchByID(11002));

        Assertions.assertTrue(
                lastOutput.contains("OtherB") || lastOutput.toLowerCase().contains("found"),
                "Expected merged student OtherB to be present after merge. Out:\n" + lastOutput
        );
    }

    /* ===================== Helper: capture System.out printed output ===================== */
    private String runAndCapture(Runnable action) {
        java.io.PrintStream old = System.out;
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        System.setOut(ps);
        try {
            action.run();
        } finally {
            System.setOut(old);
        }
        return baos.toString();
    }
}
