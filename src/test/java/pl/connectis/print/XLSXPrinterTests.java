package pl.connectis.print;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XLSXPrinterTests {

    private final String testFilePath = "target/test.xlsx";

    @Test
    public void testsPrintingToXLSX() throws IOException {

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

        // when
        Printer printer = new XLSXPrinter(testFilePath);
        printer.print(exchangeRates);

        // then
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(testFilePath));
        XSSFSheet sheet = workbook.getSheet("Rates");

        assertEquals("Rate date", getCellValue(sheet, 0, 0));
        assertEquals(baseCurrency + "/" + quoteCurrencyCNY, getCellValue(sheet, 0, 1));
        assertEquals(baseCurrency + "/" + quoteCurrencyPHP, getCellValue(sheet, 0, 2));
        assertEquals(baseCurrency + "/" + quoteCurrencyTHB, getCellValue(sheet, 0, 3));

        assertEquals(rateDateDayOne, getCellValue(sheet, 1, 0));
        assertEquals(String.valueOf(rateValueDayOneCNY), getCellValue(sheet, 1, 1));
        assertEquals(String.valueOf(rateValueDayOnePHP), getCellValue(sheet, 1, 2));
        assertEquals(String.valueOf(rateValueDayOneTHB), getCellValue(sheet, 1, 3));

        assertEquals(rateDateDayTwo, getCellValue(sheet, 2, 0));
        assertEquals(String.valueOf(rateValueDayTwoCNY), getCellValue(sheet, 2, 1));
        assertEquals(String.valueOf(rateValueDayTwoPHP), getCellValue(sheet, 2, 2));
        assertEquals(String.valueOf(rateValueDayTwoTHB), getCellValue(sheet, 2, 3));

        assertEquals(rateDateDayThree, getCellValue(sheet, 3, 0));
        assertEquals(String.valueOf(rateValueDayThreeCNY), getCellValue(sheet, 3, 1));
        assertEquals(String.valueOf(rateValueDayThreePHP), getCellValue(sheet, 3, 2));
        assertEquals(String.valueOf(rateValueDayThreeTHB), getCellValue(sheet, 3, 3));

        workbook.close();

    }

    @AfterEach
    public void tearDown() {

        File testFile = new File(testFilePath);
        testFile.delete();

    }

    private String getCellValue(XSSFSheet sheet, int row, int cell) {

        String cellValueString = sheet.getRow(row).getCell(cell).toString();

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            cellValueString = String.valueOf(LocalDate.parse(cellValueString, formatter));

        } finally {

            return cellValueString;

        }

    }
}
