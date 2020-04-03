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
public class ExchangeRateSingleTests {

    private CommandLine commandLine = new CommandLine(new ExchangeRate());
    private StringWriter stringWriter = new StringWriter();

    @BeforeAll
    public void initializeCommandLine() {
        commandLine.setCaseInsensitiveEnumValuesAllowed(true);
        PrintWriter printWriter = new PrintWriter(stringWriter);
        commandLine.setOut(printWriter);
    }


    @Test
    public void testNoInput() {
        assertEquals(2, commandLine.execute("single"));
        assertTrue(stringWriter.toString().contains("Missing required option '--quote=<symbol>'"));
    }

    @Test
    public void testHelpMenuSimple() {
        assertEquals(0, commandLine.execute("single -h".split(" ")));
    }

    @Test
    public void testHelpMenu() {
        assertEquals(0, commandLine.execute("single --help".split(" ")));
    }

    @Test
    public void testUnsupportedArgument() {
        assertEquals(2, commandLine.execute("single test".split(" ")));
        assertTrue(stringWriter.toString().contains("Missing required option '--quote=<symbol>'"));
    }

}
