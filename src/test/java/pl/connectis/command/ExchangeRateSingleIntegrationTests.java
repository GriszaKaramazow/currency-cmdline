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

        assertTrue(testFileContent.contains("USD"));
        assertTrue(testFileContent.contains("CHF"));
        assertTrue(testFileContent.contains("2004-11-11"));
        assertTrue(testFileContent.contains(String.valueOf(1.1785104732)));

        assertTrue(testFileContent.contains("Rate date\tUSD/CHF"));
        assertTrue(testFileContent.contains("2004-11-11\t" + 1.1785104732));

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

        assertTrue(testFileContent.contains("GBP"));
        assertTrue(testFileContent.contains("DKK"));
        assertTrue(testFileContent.contains("NOK"));
        assertTrue(testFileContent.contains("SEK"));
        assertTrue(testFileContent.contains("2016-01-08"));
        assertTrue(testFileContent.contains(String.valueOf(10.0106013232)));
        assertTrue(testFileContent.contains(String.valueOf(12.9913176505)));
        assertTrue(testFileContent.contains(String.valueOf(12.4317288208)));

        assertTrue(testFileContent.contains("Rate date,GBP/DKK,GBP/NOK,GBP/SEK"));
        assertTrue(testFileContent.contains("2016-01-08," + 10.0106013232 + "," + 12.9913176505 + "," + 12.4317288208));

        fileUtils.deleteFile();

    }

}
