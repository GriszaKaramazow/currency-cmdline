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

public class CSVPrinterTests {

    private final String testFilePath = "target/test.csv";

    @Test
    public void testsPrintingToTXT() throws IOException {

        // given
        String baseCurrency = "NZD";
        String rateDateDayOne = "2019-12-11";
        String rateDateDayTwo = "2019-12-12";
        String rateDateDayThree = "2019-12-13";
        String quoteCurrencyCNY = "CNY";
        Double rateValueDayOneCNY = 4.6064760104;
        Double rateValueDayTwoCNY = 4.6358689223;
        Double rateValueDayThreeCNY = 4.6168434777;
        String quoteCurrencyPHP = "PHP";
        Double rateValueDayOnePHP = 33.253958875;
        Double rateValueDayTwoPHP = 33.4058913995;
        Double rateValueDayThreePHP = 33.4504830202;
        String quoteCurrencyTHB = "THB";
        Double rateValueDayOneTHB = 19.8050106358;
        Double rateValueDayTwoTHB = 19.8781497693;
        Double rateValueDayThreeTHB = 19.9899247318;

        ExchangeRates exchangeRates = new ExchangeRates();
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyTHB, rateDateDayOne, rateValueDayOneTHB));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyPHP, rateDateDayOne, rateValueDayOnePHP));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyCNY, rateDateDayOne, rateValueDayOneCNY));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyTHB, rateDateDayTwo, rateValueDayTwoTHB));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyPHP, rateDateDayTwo, rateValueDayTwoPHP));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyCNY, rateDateDayTwo, rateValueDayTwoCNY));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyTHB, rateDateDayThree, rateValueDayThreeTHB));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyPHP, rateDateDayThree, rateValueDayThreePHP));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyCNY, rateDateDayThree, rateValueDayThreeCNY));

        // when
        Printer printer = new CSVPrinter(testFilePath);
        printer.print(exchangeRates);

        // then
        String testFileContent = new String(Files.readAllBytes(Paths.get(testFilePath)));

        assertTrue(testFileContent.contains(baseCurrency));
        assertTrue(testFileContent.contains(rateDateDayOne));
        assertTrue(testFileContent.contains(rateDateDayTwo));
        assertTrue(testFileContent.contains(rateDateDayThree));
        assertTrue(testFileContent.contains(quoteCurrencyTHB));
        assertTrue(testFileContent.contains(String.valueOf(rateValueDayOneTHB)));
        assertTrue(testFileContent.contains(String.valueOf(rateValueDayTwoTHB)));
        assertTrue(testFileContent.contains(String.valueOf(rateValueDayThreeTHB)));
        assertTrue(testFileContent.contains(quoteCurrencyPHP));
        assertTrue(testFileContent.contains(String.valueOf(rateValueDayOnePHP)));
        assertTrue(testFileContent.contains(String.valueOf(rateValueDayTwoPHP)));
        assertTrue(testFileContent.contains(String.valueOf(rateValueDayThreePHP)));
        assertTrue(testFileContent.contains(quoteCurrencyCNY));
        assertTrue(testFileContent.contains(String.valueOf(rateValueDayOneCNY)));
        assertTrue(testFileContent.contains(String.valueOf(rateValueDayTwoCNY)));
        assertTrue(testFileContent.contains(String.valueOf(rateValueDayThreeCNY)));

        assertTrue(testFileContent.contains(
                "Rate date," + baseCurrency + "/" + quoteCurrencyCNY + "," + baseCurrency + "/" + quoteCurrencyPHP
                + "," + baseCurrency + "/" + quoteCurrencyTHB));
        assertTrue(testFileContent.contains(
                rateDateDayOne + "," + rateValueDayOneCNY + "," + rateValueDayOnePHP + "," + rateValueDayOneTHB));
        assertTrue(testFileContent.contains(
                rateDateDayTwo + "," + rateValueDayTwoCNY + "," + rateValueDayTwoPHP + "," + rateValueDayTwoTHB));
        assertTrue(testFileContent.contains(
                rateDateDayThree + "," + rateValueDayThreeCNY + "," + rateValueDayThreePHP + "," + rateValueDayThreeTHB));

    }

    @AfterEach
    public void tearDown() {

        File testFile = new File(testFilePath);
        testFile.delete();

    }

}
