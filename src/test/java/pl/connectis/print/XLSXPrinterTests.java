package pl.connectis.print;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;
import pl.connectis.utils.TestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XLSXPrinterTests {

    private final TestUtils testUtils = new TestUtils();

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
        FileInputStream fileInputStream = new FileInputStream(testFilePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheet("Rates");

        assertEquals("Rate date", testUtils.getCellValue(sheet, 0, 0));
        assertEquals(baseCurrency + "/" + quoteCurrencyCNY, testUtils.getCellValue(sheet, 0, 1));
        assertEquals(baseCurrency + "/" + quoteCurrencyPHP, testUtils.getCellValue(sheet, 0, 2));
        assertEquals(baseCurrency + "/" + quoteCurrencyTHB, testUtils.getCellValue(sheet, 0, 3));

        assertEquals(rateDateDayOne, testUtils.getCellValue(sheet, 1, 0));
        assertEquals(String.valueOf(rateValueDayOneCNY), testUtils.getCellValue(sheet, 1, 1));
        assertEquals(String.valueOf(rateValueDayOnePHP), testUtils.getCellValue(sheet, 1, 2));
        assertEquals(String.valueOf(rateValueDayOneTHB), testUtils.getCellValue(sheet, 1, 3));

        assertEquals(rateDateDayTwo, testUtils.getCellValue(sheet, 2, 0));
        assertEquals(String.valueOf(rateValueDayTwoCNY), testUtils.getCellValue(sheet, 2, 1));
        assertEquals(String.valueOf(rateValueDayTwoPHP), testUtils.getCellValue(sheet, 2, 2));
        assertEquals(String.valueOf(rateValueDayTwoTHB), testUtils.getCellValue(sheet, 2, 3));

        assertEquals(rateDateDayThree, testUtils.getCellValue(sheet, 3, 0));
        assertEquals(String.valueOf(rateValueDayThreeCNY), testUtils.getCellValue(sheet, 3, 1));
        assertEquals(String.valueOf(rateValueDayThreePHP), testUtils.getCellValue(sheet, 3, 2));
        assertEquals(String.valueOf(rateValueDayThreeTHB), testUtils.getCellValue(sheet, 3, 3));

        workbook.close();
        fileInputStream.close();
        new File(testFilePath).delete();

    }

}
