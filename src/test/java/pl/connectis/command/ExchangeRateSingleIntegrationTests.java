package pl.connectis.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(Lifecycle.PER_CLASS)
public class ExchangeRateSingleIntegrationTests {

    private final CommandLine commandLine = new CommandLine(new ExchangeRate());

    @Test
    public void testsPrintingToTXT() throws IOException {

        // given
        String testFilePath = "target/test.txt";
        String command = "single -b USD -q CHF -d 2004-11-11 -f " + testFilePath;

        // when
        commandLine.execute(command.split(" "));

        // then
        String testFileContent = new String(Files.readAllBytes(Paths.get(testFilePath)));

        String baseCurrency = "USD";
        String quoteCurrency = "CHF";
        String rateDate = "2004-11-11";
        Double rateValue = 1.1785104732;

        assertTrue(testFileContent.contains(baseCurrency));
        assertTrue(testFileContent.contains(quoteCurrency));
        assertTrue(testFileContent.contains(rateDate));
        assertTrue(testFileContent.contains(String.valueOf(rateValue)));

        assertTrue(testFileContent.contains("Rate date\t" + baseCurrency + "/" + quoteCurrency));
        assertTrue(testFileContent.contains(rateDate + "\t" + rateValue));

        new File(testFilePath).delete();

    }

    @Test
    public void testPrintingToCSV() throws IOException {

        // given
        String testFilePath = "target/test.csv";
        String command = "single -b GBP -q DKK NOK SEK -d 2016-01-08 -f " + testFilePath;

        // when
        commandLine.execute(command.split(" "));

        // then
        String testFileContent = new String(Files.readAllBytes(Paths.get(testFilePath)));

        String baseCurrency = "GBP";
        String quoteCurrencyDKK = "DKK";
        String quoteCurrencyNOK = "NOK";
        String quoteCurrencySEK = "SEK";
        String rateDate = "2016-01-08";
        Double rateValueDKK = 10.0106013232;
        Double rateValueNOK = 12.9913176505;
        Double rateValueSEK = 12.4317288208;

        assertTrue(testFileContent.contains(baseCurrency));
        assertTrue(testFileContent.contains(quoteCurrencyDKK));
        assertTrue(testFileContent.contains(quoteCurrencyNOK));
        assertTrue(testFileContent.contains(quoteCurrencySEK));
        assertTrue(testFileContent.contains(rateDate));
        assertTrue(testFileContent.contains(String.valueOf(rateValueDKK)));
        assertTrue(testFileContent.contains(String.valueOf(rateValueNOK)));
        assertTrue(testFileContent.contains(String.valueOf(rateValueSEK)));

        assertTrue(testFileContent.contains(
                "Rate date," + baseCurrency + "/" + quoteCurrencyDKK + "," + baseCurrency + "/" + quoteCurrencyNOK
                        + "," + baseCurrency + "/" + quoteCurrencySEK));
        assertTrue(testFileContent.contains(
                rateDate + "," + rateValueDKK + "," + rateValueNOK + "," + rateValueSEK));

        new File(testFilePath).delete();

    }

}
