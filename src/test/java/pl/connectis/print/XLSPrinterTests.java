package pl.connectis.print;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;
import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;
import pl.connectis.utils.TestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XLSPrinterTests {

    private final TestUtils testUtils = new TestUtils();

    @Test
    public void testsPrintingToXLS() throws IOException {

        // given
        String testFilePath = "target/test.xls";

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
        Printer printer = new XLSPrinter(testFilePath);
        printer.print(exchangeRates);

        // then
        FileInputStream fileInputStream = new FileInputStream(testFilePath);
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = workbook.getSheet("Rates");

        assertEquals("Rate date", testUtils.getCellValue(sheet, 0, 0));
        assertEquals(baseCurrency + "/" + quoteCurrency, testUtils.getCellValue(sheet, 0, 1));
        assertEquals(rateDateDayOne, testUtils.getCellValue(sheet, 1, 0));
        assertEquals(String.valueOf(rateValueDayOne), testUtils.getCellValue(sheet, 1, 1));
        assertEquals(rateDateDayTwo, testUtils.getCellValue(sheet, 2, 0));
        assertEquals(String.valueOf(rateValueDayTwo), testUtils.getCellValue(sheet, 2, 1));
        assertEquals(rateDateDayThree, testUtils.getCellValue(sheet, 3, 0));
        assertEquals(String.valueOf(rateValueDayThree), testUtils.getCellValue(sheet, 3, 1));

        workbook.close();
        fileInputStream.close();
        new File(testFilePath).delete();

    }

}
