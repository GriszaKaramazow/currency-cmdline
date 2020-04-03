package pl.connectis.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(Lifecycle.PER_CLASS)
public class ExchangeRateTests {

    private final CommandLine commandLine = new CommandLine(new ExchangeRate());
    private final StringWriter stringWriter = new StringWriter();

    @BeforeAll
    public void initializeCommandLine() {
        commandLine.setCaseInsensitiveEnumValuesAllowed(true);
        PrintWriter printWriter = new PrintWriter(stringWriter);
        commandLine.setOut(printWriter);
    }

    @Test
    public void testNoInput() {
        assertEquals(0, commandLine.execute());
    }

    @Test
    public void testHelpMenuSimple() {
        assertEquals(0, commandLine.execute("-h"));
    }

    @Test
    public void testHelpMenu() {
        assertEquals(0, commandLine.execute("--help"));
    }

    @Test
    public void testUnsupportedArgument() {
        assertEquals(2, commandLine.execute("test"));
        assertTrue(stringWriter.toString().contains("Unmatched argument at index 0: 'test'"));
    }

}
