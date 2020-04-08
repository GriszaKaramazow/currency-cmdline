package pl.connectis.print;

import org.junit.jupiter.api.Test;
import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;
import pl.connectis.utils.FileUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TXTPrinterTests {

    @Test
    public void testsPrintingToTXT() {

        // given
        String testFilePath = "target/test.txt";

        ExchangeRates exchangeRates = new ExchangeRates();
        exchangeRates.addSingleRate(
                new SingleRate("CAD", "HUF", "2019-06-03", 214.4191283614));
        exchangeRates.addSingleRate(
                new SingleRate("CAD", "HUF", "2019-06-04", 213.0898021309));
        exchangeRates.addSingleRate(
                new SingleRate("CAD", "HUF", "2019-06-05", 213.3443928334));

        Printer printer = new TXTPrinter(testFilePath);

        // when
        printer.print(exchangeRates);

        // then
        FileUtils fileUtils = new FileUtils(testFilePath);
        String testFileContent = fileUtils.getTextFileContentAsString();

        assertTrue(testFileContent.contains("CAD"));
        assertTrue(testFileContent.contains("HUF"));
        assertTrue(testFileContent.contains("2019-06-03"));
        assertTrue(testFileContent.contains(String.valueOf(214.4191283614)));
        assertTrue(testFileContent.contains("2019-06-04"));
        assertTrue(testFileContent.contains(String.valueOf(213.0898021309)));
        assertTrue(testFileContent.contains("2019-06-05"));
        assertTrue(testFileContent.contains(String.valueOf(213.3443928334)));

        assertTrue(testFileContent.contains("Rate date\tCAD/HUF"));
        assertTrue(testFileContent.contains("2019-06-03\t" + 214.4191283614));
        assertTrue(testFileContent.contains("2019-06-04\t" + 213.0898021309));
        assertTrue(testFileContent.contains("2019-06-05\t" + 213.3443928334));

        fileUtils.deleteFile();

    }

}

