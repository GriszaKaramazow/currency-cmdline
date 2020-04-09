package pl.connectis.print;

import org.junit.jupiter.api.Test;
import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;
import pl.connectis.utils.FileUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CSVPrinterTests {

    @Test
    public void testsPrintingToTXT() {

        // given
        String testFilePath = "target/test.csv";

        ExchangeRates exchangeRates = new ExchangeRates();
        exchangeRates.addSingleRate(
                new SingleRate("NZD", "CNY", "2019-12-11", 4.6064760104));
        exchangeRates.addSingleRate(
                new SingleRate("NZD", "PHP", "2019-12-11", 33.253958875));
        exchangeRates.addSingleRate(
                new SingleRate("NZD", "THB", "2019-12-11", 19.8050106358));
        exchangeRates.addSingleRate(
                new SingleRate("NZD", "CNY", "2019-12-12", 4.6358689223));
        exchangeRates.addSingleRate(
                new SingleRate("NZD", "PHP", "2019-12-12", 33.4058913995));
        exchangeRates.addSingleRate(
                new SingleRate("NZD", "THB", "2019-12-12", 19.8781497693));
        exchangeRates.addSingleRate(
                new SingleRate("NZD", "CNY", "2019-12-13", 4.6168434777));
        exchangeRates.addSingleRate(
                new SingleRate("NZD", "PHP", "2019-12-13", 33.4504830202));
        exchangeRates.addSingleRate(
                new SingleRate("NZD", "THB", "2019-12-13", 19.9899247318));

        Printer printer = new CSVPrinter(testFilePath);

        // when
        printer.print(exchangeRates);

        // then
        FileUtils fileUtils = new FileUtils(testFilePath);
        String testFileContent = fileUtils.getTextFileContentAsString();

        assertTrue(testFileContent.contains("NZD"));
        assertTrue(testFileContent.contains("2019-12-11"));
        assertTrue(testFileContent.contains("2019-12-12"));
        assertTrue(testFileContent.contains("2019-12-13"));
        assertTrue(testFileContent.contains("CNY"));
        assertTrue(testFileContent.contains(String.valueOf(4.6064760104)));
        assertTrue(testFileContent.contains(String.valueOf(4.6358689223)));
        assertTrue(testFileContent.contains(String.valueOf(4.6168434777)));
        assertTrue(testFileContent.contains("PHP"));
        assertTrue(testFileContent.contains(String.valueOf(33.253958875)));
        assertTrue(testFileContent.contains(String.valueOf(33.4058913995)));
        assertTrue(testFileContent.contains(String.valueOf(33.4504830202)));
        assertTrue(testFileContent.contains("THB"));
        assertTrue(testFileContent.contains(String.valueOf(19.8050106358)));
        assertTrue(testFileContent.contains(String.valueOf(19.8781497693)));
        assertTrue(testFileContent.contains(String.valueOf(19.9899247318)));

        assertTrue(testFileContent.contains("Rate date,NZD/CNY,NZD/PHP,NZD/THB"));
        assertTrue(testFileContent.contains("2019-12-11," + 4.6064760104 + "," + 33.253958875 + "," + 19.8050106358));
        assertTrue(testFileContent.contains("2019-12-12," + 4.6358689223 + "," + 33.4058913995 + "," + 19.8781497693));
        assertTrue(testFileContent.contains("2019-12-13," + 4.6168434777 + "," + 33.4504830202 + "," + 19.9899247318));

        fileUtils.deleteFile();

    }

}
