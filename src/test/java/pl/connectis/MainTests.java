package pl.connectis;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import picocli.CommandLine;
import pl.connectis.command.ExchangeRate;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(Lifecycle.PER_CLASS)
public class MainTests {

    private CommandLine commandLine;

    @BeforeAll
    public void initializeCommandLine() {
        commandLine = new CommandLine(new ExchangeRate());
        commandLine.setCaseInsensitiveEnumValuesAllowed(true);
        StringWriter stringWriter = new StringWriter();
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
    }

}
