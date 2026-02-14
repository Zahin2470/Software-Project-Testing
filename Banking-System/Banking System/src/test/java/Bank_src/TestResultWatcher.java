package Bank_src;

import org.junit.jupiter.api.extension.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

public class TestResultWatcher implements TestWatcher, BeforeAllCallback, AfterAllCallback {

    private BufferedWriter writer;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        // open in append mode so multiple runs append; to overwrite, use false instead of true
        writer = new BufferedWriter(new FileWriter("result.txt", true));
        writer.write("=== Test run started: " + Instant.now().toString() + " ===\n");
        writer.flush();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        writeLine(context, "PASSED", null);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        writeLine(context, "FAILED", cause == null ? null : cause.toString());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        writeLine(context, "DISABLED", reason.orElse(""));
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        writeLine(context, "ABORTED", cause == null ? null : cause.toString());
    }

    private synchronized void writeLine(ExtensionContext context, String status, String extra) {
        try {
            String className = context.getRequiredTestClass().getSimpleName();
            String display = context.getDisplayName(); // usually method name
            String line = String.format("%s.%s : %s", className, display, status);
            if (extra != null && !extra.isEmpty()) line += " -- " + extra;
            writer.write(line + "\n");
            writer.flush();
            // Also print to console so you can see it live
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        writer.write("=== Test run finished: " + Instant.now().toString() + " ===\n\n");
        writer.flush();
        writer.close();
    }
}