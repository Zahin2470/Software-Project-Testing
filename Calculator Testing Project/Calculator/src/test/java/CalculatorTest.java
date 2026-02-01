package com.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    private Calculator calculator;

    // simple counters for a summary printed at the end
    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;

    /**
     * TestWatcher extension to observe test success/failure and print immediately.
     * Registered per-instance so it receives callbacks for each test.
     */
@RegisterExtension
public TestWatcher watcher = new TestWatcher() {

    @Override
    public void testSuccessful(ExtensionContext context) {
        passedTests++;
        System.out.println(">> " + context.getDisplayName() + " ✔ PASSED");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        failedTests++;
        System.out.println(">> " + context.getDisplayName() + " ✖ FAILED");
        System.out.println("   Reason: " + cause.getMessage());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        System.out.println(">> " + context.getDisplayName()
                + " ⚠ DISABLED: " + reason.orElse("No reason"));
    }
};


    @BeforeEach
    void setUp(TestInfo testInfo) {
        calculator = new Calculator();
        totalTests++;
        System.out.println("\n=== START: " + testInfo.getDisplayName() + " ===");
    }

    @AfterEach
    void afterEach(TestInfo testInfo) {
        System.out.println("=== END: " + testInfo.getDisplayName() + " ===\n");
    }

    @AfterAll
    static void summary() {
        System.out.println("===== TEST SUMMARY =====");
        System.out.println("Total tests run: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + failedTests);
        System.out.println("========================");
    }

    /* Helper that wraps assertEquals with console logging.
       It prints a check mark on success and a cross on failure,
       then rethrows AssertionError so JUnit still fails the test. */
    private void assertEqualsWithPrint(String label, int expected, int actual) {
        try {
            assertEquals(expected, actual);
            System.out.println(label + " ✔ Expected=" + expected + ", Actual=" + actual);
        } catch (AssertionError e) {
            System.out.println(label + " ✖ Expected=" + expected + ", Actual=" + actual);
            throw e;
        }
    }

    @Test
    void testAdd() {
        assertEqualsWithPrint("testAdd: 2 + 3", 5, calculator.add(2, 3));
        assertEqualsWithPrint("testAdd: -2 + 2", 0, calculator.add(-2, 2));
    }

    @Test
    void testSubtract() {
        assertEqualsWithPrint("testSubtract: 5 - 3", 2, calculator.subtract(5, 3));
        assertEqualsWithPrint("testSubtract: 3 - 9", -6, calculator.subtract(3, 9));
    }

    @Test
    void testMultiply() {
        assertEqualsWithPrint("testMultiply: 3 * 5", 15, calculator.multiply(3, 5));
        assertEqualsWithPrint("testMultiply: 0 * 100", 0, calculator.multiply(0, 100));
        assertEqualsWithPrint("testMultiply: -3 * 4", -12, calculator.multiply(-3, 4));
    }

    @Test
    void testDivide() {
        assertEqualsWithPrint("testDivide: 10 / 5", 2, calculator.divide(10, 5));
        assertEqualsWithPrint("testDivide: 9 / 3", 3, calculator.divide(9, 3));
        // integer division behaviour
        assertEqualsWithPrint("testDivide: 5 / 2 (int)", 2, calculator.divide(5, 2));
    }

    @Test
    void testDivideByZeroThrows() {
        String label = "testDivideByZeroThrows";

        try {
            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.divide(10, 0)
            );
            // If we reach here, exception was thrown as expected
            System.out.println(label + " ✔ Threw IllegalArgumentException with message: \"" + ex.getMessage() + "\"");
            // verify message as an extra assertion (will throw if not equal)
            assertEquals("Cannot divide by zero.", ex.getMessage());
        } catch (AssertionError e) {
            // Print a failure line and rethrow so JUnit reports the test as failed
            System.out.println(label + " ✖ Expected IllegalArgumentException with message \"Cannot divide by zero.\"");
            throw e;
        }
    }
}
