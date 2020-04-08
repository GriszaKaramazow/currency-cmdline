package pl.connectis.print;

import org.junit.jupiter.api.Test;
import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;
import pl.connectis.utils.ExcelFileTestHelper;
import pl.connectis.utils.XLSXFileTestHelper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XLSXPrinterTests {

    @Test
    public void testsPrintingToXLSX() throws IOException {

        // given
        String testFilePath = "target/test.xlsx";

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
                new SingleRate(baseCurrency, quoteCurrencyCNY, rateDateDayOne, rateValueDayOneCNY));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyPHP, rateDateDayOne, rateValueDayOnePHP));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyTHB, rateDateDayOne, rateValueDayOneTHB));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyCNY, rateDateDayTwo, rateValueDayTwoCNY));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyPHP, rateDateDayTwo, rateValueDayTwoPHP));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyTHB, rateDateDayTwo, rateValueDayTwoTHB));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyCNY, rateDateDayThree, rateValueDayThreeCNY));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyPHP, rateDateDayThree, rateValueDayThreePHP));
        exchangeRates.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyTHB, rateDateDayThree, rateValueDayThreeTHB));

        Printer printer = new XLSXPrinter(testFilePath);

        // when
        printer.print(exchangeRates);

        // then
        ExcelFileTestHelper fileTestHelper = new XLSXFileTestHelper(testFilePath);

        assertEquals("Rate date", fileTestHelper.getCellValueString(0, 0));
        assertEquals(baseCurrency + "/" + quoteCurrencyCNY, fileTestHelper.getCellValueString(0, 1));
        assertEquals(baseCurrency + "/" + quoteCurrencyPHP, fileTestHelper.getCellValueString(0, 2));
        assertEquals(baseCurrency + "/" + quoteCurrencyTHB, fileTestHelper.getCellValueString(0, 3));

        assertEquals(rateDateDayOne, fileTestHelper.getCellValueDateAsString(1, 0));
        assertEquals(rateValueDayOneCNY, fileTestHelper.getCellValueDouble(1, 1));
        assertEquals(rateValueDayOnePHP, fileTestHelper.getCellValueDouble(1, 2));
        assertEquals(rateValueDayOneTHB, fileTestHelper.getCellValueDouble(1, 3));

        assertEquals(rateDateDayTwo, fileTestHelper.getCellValueDateAsString(2, 0));
        assertEquals(rateValueDayTwoCNY, fileTestHelper.getCellValueDouble(2, 1));
        assertEquals(rateValueDayTwoPHP, fileTestHelper.getCellValueDouble(2, 2));
        assertEquals(rateValueDayTwoTHB, fileTestHelper.getCellValueDouble(2, 3));

        assertEquals(rateDateDayThree, fileTestHelper.getCellValueDateAsString(3, 0));
        assertEquals(rateValueDayThreeCNY, fileTestHelper.getCellValueDouble(3, 1));
        assertEquals(rateValueDayThreePHP, fileTestHelper.getCellValueDouble(3, 2));
        assertEquals(rateValueDayThreeTHB, fileTestHelper.getCellValueDouble(3, 3));

        fileTestHelper.closeDeleteFile();

    }

}
