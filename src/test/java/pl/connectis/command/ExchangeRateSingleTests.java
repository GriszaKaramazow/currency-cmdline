package pl.connectis.command;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockserver.integration.ClientAndServer;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@TestInstance(Lifecycle.PER_CLASS)
public class ExchangeRateSingleTests {

    private CommandLine commandLine = new CommandLine(new ExchangeRate());
    private StringWriter stringWriter = new StringWriter();

    private final ClientAndServer mockServer = new ClientAndServer();
    private String serverResponse;


    public ExchangeRateSingleTests() {
    }

    @BeforeAll
    public void startServer() {
        mockServer.startClientAndServer("localhost", 1080);
    }

    @AfterAll
    public void stopServer() {
        mockServer.stop();
    }

    @BeforeAll
    public void initializeCommandLine() {
        commandLine.setCaseInsensitiveEnumValuesAllowed(true);
        PrintWriter printWriter = new PrintWriter(stringWriter);
        commandLine.setOut(printWriter);

    }

    @Test
    public void mockTest() {

        mockServer
                .when(
                    request()
                        .withMethod("GET")
                        .withPath("2020-03-01")
                        .withQueryStringParameter("base", "EUR")
                        .withQueryStringParameter("symbols", "PLN")
                )
                .respond(
                    response()
                        .withStatusCode(200)
                        .withBody("{\"base\": \"EUR\",\"rates\": {\"PLN\": 4.3259},\"date\": \"2020-02-28\"}")
                );
        assertEquals(0, commandLine.execute("single -q PLN -d 2020-03-01".split(" ")));
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
