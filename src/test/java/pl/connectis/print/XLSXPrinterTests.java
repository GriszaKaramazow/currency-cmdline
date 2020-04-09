package pl.connectis.print;

import org.junit.jupiter.api.Test;
import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;
import pl.connectis.utils.ExcelFileTestHelper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XLSXPrinterTests {

    @Test
    public void testsPrintingToXLSX() throws IOException {

        // given
        String testFilePath = "target/test.xlsx";

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

        Printer printer = new XLSXPrinter(testFilePath);

        // when
        printer.print(exchangeRates);

        // then
        ExcelFileTestHelper fileTestHelper = ExcelFileTestHelper.createXLSXFileHelper(testFilePath);

        assertEquals("Rate date", fileTestHelper.getCellValueString(0, 0));
        assertEquals("NZD/CNY", fileTestHelper.getCellValueString(0, 1));
        assertEquals("NZD/PHP", fileTestHelper.getCellValueString(0, 2));
        assertEquals("NZD/THB", fileTestHelper.getCellValueString(0, 3));

        assertEquals("2019-12-11", fileTestHelper.getCellValueDateAsString(1, 0));
        assertEquals(4.6064760104, fileTestHelper.getCellValueDouble(1, 1));
        assertEquals(33.253958875, fileTestHelper.getCellValueDouble(1, 2));
        assertEquals(19.8050106358, fileTestHelper.getCellValueDouble(1, 3));

        assertEquals("2019-12-12", fileTestHelper.getCellValueDateAsString(2, 0));
        assertEquals(4.6358689223, fileTestHelper.getCellValueDouble(2, 1));
        assertEquals(33.4058913995, fileTestHelper.getCellValueDouble(2, 2));
        assertEquals(19.8781497693, fileTestHelper.getCellValueDouble(2, 3));

        assertEquals("2019-12-13", fileTestHelper.getCellValueDateAsString(3, 0));
        assertEquals(4.6168434777, fileTestHelper.getCellValueDouble(3, 1));
        assertEquals(33.4504830202, fileTestHelper.getCellValueDouble(3, 2));
        assertEquals(19.9899247318, fileTestHelper.getCellValueDouble(3, 3));

        fileTestHelper.closeDeleteFile();

    }

}
