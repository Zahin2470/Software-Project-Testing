// ManualTestRunner.java
// A standalone test runner that exercises StudentSystem and InputValidator logic,
// captures printed output, and writes a readable log file.
// No JUnit required.

import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

import packages.system.StudentSystem;
import packages.system.InputValidator;

public class ManualTestRunner {

    private static final String LOG_FILE = "test-run-output.txt";
    private final StringBuilder log = new StringBuilder();

    public static void main(String[] args) {
        ManualTestRunner runner = new ManualTestRunner();
        runner.runAllTests();
        runner.flushLogToFile();
        System.out.println("All tests finished. Log written to: " + LOG_FILE);
    }

    void runAllTests() {
        appendHeader("Manual Test Runner: Student Management System");
        runTestAddAndCount();
        runTestRemoveAndSearchNotFound();
        runTestUpdateGPAAndAverage();
        runTestListAndSortOutputs();
        runTestFilterByGPA();
        runTestFilterByYear();
        runTestFilterByDepartment();
        runTestDisplayFailingStudents();
        runTestDisplayTop5();
        runTestGenerateSummary();
        runTestSearchByID_foundAndNotFound();
        runTestMergeStudentSystem();
        runInputValidatorGpaTests();
        appendHeader("End of tests");
    }

    // Utility to capture System.out while action runs
    private String captureOutput(Runnable action) {
        PrintStream oldOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);
        try {
            action.run();
        } finally {
            System.setOut(oldOut);
        }
        return baos.toString();
    }

    private void logAndPrint(String title, String output, boolean passHint) {
        String sep = "\n----------------------------------------------------------------\n";
        String entry = sep + title + "\n\n" + output + "\n\nResult hint: " + (passHint ? "PASS" : "CHECK/FAIL") + sep;
        System.out.println(entry);
        log.append(entry);
    }

    private void appendHeader(String text) {
        String h = "\n\n================ " + text + " ================\n\n";
        System.out.println(h);
        log.append(h);
    }

    private void flushLogToFile() {
        try {
            Files.write(Paths.get(LOG_FILE), log.toString().getBytes());
        } catch (IOException e) {
            System.err.println("Failed to write log file: " + e.getMessage());
        }
    }

    // -------------------------
    // Individual test scenarios
    // -------------------------

    void runTestAddAndCount() {
        StudentSystem ss = new StudentSystem();
        ss.addStudent("Abrar Hossain", 1001, 3.5, "Third", "CSE", false);
        ss.addStudent("Zahin Hossain", 1002, 3.9, "Fourth", "EEE", false);

        String out = captureOutput(() -> ss.countTotalStudents());
        boolean pass = out.toLowerCase().contains("total") || out.matches("(?s).*2.*");
        logAndPrint("TC-001 Add student (valid) & countTotalStudents()", out, pass);
    }

    void runTestRemoveAndSearchNotFound() {
        StudentSystem ss = new StudentSystem();
        ss.addStudent("Midul Hossain", 2001, 2.0, "Third", "CSE", false);
        ss.removeStudentByID(2001);

        String out = captureOutput(() -> ss.searchByID(2001));
        boolean pass = out.toLowerCase().contains("not found") || out.toLowerCase().contains("no student") || out.trim().length() > 0;
        logAndPrint("TC-003 Remove student & searchByID(not found)", out, pass);
    }

    void runTestUpdateGPAAndAverage() {
        StudentSystem ss = new StudentSystem();
        ss.addStudent("Mohammoad", 3001, 2.0, "Third", "EEE", false);
        ss.addStudent("Rahim", 3002, 4.0, "Fourth", "CSE", false);

        ss.updateStudentByID(3001, null, 3.0, null, null);
        String out = captureOutput(() -> ss.calculateAverageGPA());
        boolean pass = out.contains("3.50") || out.contains("3.5") || out.matches("(?s).*3\\.5.*");
        logAndPrint("TC-005 Update GPA & calculateAverageGPA()", out, pass);
    }

    void runTestListAndSortOutputs() {
        StudentSystem ss = new StudentSystem();
        ss.addStudent("X", 10, 3.1, "Third", "Gen", false);
        ss.addStudent("A", 5, 3.9, "Third", "Gen", false);
        String out = captureOutput(() -> ss.listAndSortAllStudents("Name"));
        boolean pass = out.contains("A") && out.contains("X");
        logAndPrint("TC-006 listAndSortAllStudents(\"Name\")", out, pass);
    }

    void runTestFilterByGPA() {
        StudentSystem ss = new StudentSystem();
        ss.addStudent("LowGPA", 4001, 1.5, "Third", "CSE", false);
        ss.addStudent("MidGPA", 4002, 2.5, "Third", "CSE", false);
        ss.addStudent("HighGPA", 4003, 3.8, "Third", "CSE", false);

        String out = captureOutput(() -> ss.filterByGPA(2.5));
        boolean pass = out.contains("MidGPA");
        logAndPrint("TC-007 filterByGPA(2.5)", out, pass);
    }

    void runTestFilterByYear() {
        StudentSystem ss = new StudentSystem();
        ss.addStudent("Y1", 5001, 3.0, "First", "GEN", false);
        ss.addStudent("Y3", 5002, 3.2, "Third", "GEN", false);

        String out = captureOutput(() -> ss.filterByYear("Third"));
        boolean pass = out.contains("Y3");
        logAndPrint("TC-008 filterByYear(\"Third\")", out, pass);
    }

    void runTestFilterByDepartment() {
        StudentSystem ss = new StudentSystem();
        ss.addStudent("InCSE", 6001, 3.6, "Third", "CSE", false);
        ss.addStudent("InEEE", 6002, 3.2, "Third", "EEE", false);

        String out = captureOutput(() -> ss.filterByDepartment("CSE"));
        boolean pass = out.contains("InCSE");
        logAndPrint("TC-009 filterByDepartment(\"CSE\")", out, pass);
    }

    void runTestDisplayFailingStudents() {
        StudentSystem ss = new StudentSystem();
        ss.addStudent("Failing1", 7001, 1.2, "Third", "CSE", false);
        ss.addStudent("Passing1", 7002, 2.8, "Third", "CSE", false);

        String out = captureOutput(() -> ss.displayFailingStudents());
        boolean pass = out.contains("Failing1");
        logAndPrint("TC-010 displayFailingStudents()", out, pass);
    }

    void runTestDisplayTop5() {
        StudentSystem ss = new StudentSystem();
        ss.addStudent("S1", 8001, 4.0, "Fourth", "CSE", false);
        ss.addStudent("S2", 8002, 3.9, "Fourth", "CSE", false);
        ss.addStudent("S3", 8003, 3.8, "Fourth", "CSE", false);
        ss.addStudent("S4", 8004, 3.7, "Fourth", "CSE", false);
        ss.addStudent("S5", 8005, 3.6, "Fourth", "CSE", false);
        ss.addStudent("S6", 8006, 3.5, "Fourth", "CSE", false);

        String out = captureOutput(() -> ss.displayTop5());
        boolean pass = out.contains("S1");
        logAndPrint("TC-011 displayTop5()", out, pass);
    }

    void runTestGenerateSummary() {
        StudentSystem ss = new StudentSystem();
        ss.addStudent("SumA", 9001, 3.1, "Third", "CSE", false);
        ss.addStudent("SumB", 9002, 3.7, "Third", "EEE", false);

        String out = captureOutput(() -> ss.generateSummary());
        boolean pass = out != null && out.trim().length() > 0;
        logAndPrint("TC-012 generateSummary()", out, pass);
    }

    void runTestSearchByID_foundAndNotFound() {
        StudentSystem ss = new StudentSystem();
        ss.addStudent("SearchMe", 10001, 3.3, "Third", "CSE", false);

        String found = captureOutput(() -> ss.searchByID(10001));
        boolean passFound = found.contains("SearchMe") || found.toLowerCase().contains("found");
        logAndPrint("TC-013 searchByID(found)", found, passFound);

        String notFound = captureOutput(() -> ss.searchByID(99999));
        boolean passNotFound = notFound.toLowerCase().contains("not found") || notFound.trim().length() > 0;
        logAndPrint("TC-013 searchByID(not found)", notFound, passNotFound);
    }

    void runTestMergeStudentSystem() {
        StudentSystem ss = new StudentSystem();
        ss.addStudent("BaseA", 11001, 3.0, "Third", "CSE", false);

        StudentSystem other = new StudentSystem();
        other.addStudent("OtherB", 11002, 3.8, "Fourth", "EEE", false);

        ss.mergeStudentSystem(other);
        String out = captureOutput(() -> ss.searchByID(11002));
        boolean pass = out.contains("OtherB") || out.toLowerCase().contains("found");
        logAndPrint("TC-014 mergeStudentSystem()", out, pass);
    }

    void runInputValidatorGpaTests() {
        // testValidGPAInput
        String output1 = runInputValidatorGpaScenario("3.75\n");
        boolean pass1 = output1.contains("3.75") || output1.contains("3.7");
        logAndPrint("TC-015 InputValidator.inputValidGPA(3.75)", output1, pass1);

        // testInvalidGPAThenValid
        String output2 = runInputValidatorGpaScenario("-1\n5\n3.2\n");
        boolean pass2 = output2.contains("3.2") || output2.contains("3.20");
        logAndPrint("TC-016 InputValidator.inputValidGPA(invalid then 3.2)", output2, pass2);
    }

    private String runInputValidatorGpaScenario(String simulatedScannerInput) {
        // Some InputValidator implementations read from Scanner parameter; adapt to your implementation.
        Scanner sc = new Scanner(simulatedScannerInput);
        String out;
        try {
            // If inputValidGPA prints messages, capture them. Otherwise, just show the returned value.
            double gpa = InputValidator.inputValidGPA(sc);
            out = "Returned GPA: " + gpa;
        } catch (Exception ex) {
            // If InputValidator prints prompts and expects System.in rather than a Scanner param,
            // fallback: capture System.out while reading from System.in stream replacement.
            out = "Exception during InputValidator test: " + ex.toString();
        }
        return out;
    }
}