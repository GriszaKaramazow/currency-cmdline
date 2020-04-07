package pl.connectis.print;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TXTPrinterTests {

    private final String testFilePath = "target/test.txt";

    @Test
    public void testsPrintingToTXT() throws IOException {

        // given
        String baseCurrency = "CAD";
        String quoteCurrency = "HUF";
        String rateDateDayOne = "2019-06-03";
        Double rateValueDayOne = 214.4191283614;
        String rateDateDayTwo = "2019-06-04";
        Double rateValueDayTwo = 213.0898021309;
        String rateDateDayThree = "2019-06-05";
        Double rateValueDayThree = 213.3443928334;

        ExchangeRates exchangeRates = new ExchangeRates();
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrency, rateDateDayOne, rateValueDayOne));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrency, rateDateDayTwo, rateValueDayTwo));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrency, rateDateDayThree, rateValueDayThree));

        // when
        Printer printer = new TXTPrinter(testFilePath);
        printer.print(exchangeRates);

        // then
        String testFileContent = new String(Files.readAllBytes(Paths.get(testFilePath)));

        assertTrue(testFileContent.contains(baseCurrency));
        assertTrue(testFileContent.contains(quoteCurrency));
        assertTrue(testFileContent.contains(rateDateDayOne));
        assertTrue(testFileContent.contains(String.valueOf(rateValueDayOne)));
        assertTrue(testFileContent.contains(rateDateDayTwo));
        assertTrue(testFileContent.contains(String.valueOf(rateValueDayTwo)));
        assertTrue(testFileContent.contains(rateDateDayThree));
        assertTrue(testFileContent.contains(String.valueOf(rateValueDayThree)));

        assertTrue(testFileContent.contains("Rate date\t" + baseCurrency + "/" + quoteCurrency));
        assertTrue(testFileContent.contains(rateDateDayOne + "\t" + rateValueDayOne));
        assertTrue(testFileContent.contains(rateDateDayTwo + "\t" + rateValueDayTwo));
        assertTrue(testFileContent.contains(rateDateDayThree + "\t" + rateValueDayThree));

    }

    @AfterEach
    public void tearDown() {

        File testFile = new File(testFilePath);
        testFile.delete();

    }

}
