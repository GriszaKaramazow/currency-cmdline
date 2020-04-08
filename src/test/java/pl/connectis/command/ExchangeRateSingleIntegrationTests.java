package pl.connectis.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import picocli.CommandLine;
import pl.connectis.utils.FileUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(Lifecycle.PER_METHOD)
public class ExchangeRateSingleIntegrationTests {

    private final CommandLine commandLine = new CommandLine(new ExchangeRate());

    @Test
    public void testsPrintingToTXT() {

        // given
        String testFilePath = "target/test.txt";
        String command = "single -b USD -q CHF -d 2004-11-11 -f " + testFilePath;

        // when
        commandLine.execute(command.split(" "));

        // then
        FileUtils fileUtils = new FileUtils(testFilePath);
        String testFileContent = fileUtils.getTextFileContentAsString();

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

        fileUtils.deleteFile();

    }

    @Test
    public void testPrintingToCSV() {

        // given
        String testFilePath = "target/test.csv";
        String command = "single -b GBP -q DKK NOK SEK -d 2016-01-08 -f " + testFilePath;

        // when
        commandLine.execute(command.split(" "));

        // then
        FileUtils fileUtils = new FileUtils(testFilePath);
        String testFileContent = fileUtils.getTextFileContentAsString();

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

        fileUtils.deleteFile();

    }

}
