package pl.connectis.print;

import org.junit.jupiter.api.Test;
import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;
import pl.connectis.utils.ExcelFileTestHelper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XLSPrinterTests {

    @Test
    public void testsPrintingToXLS() throws IOException {

        // given
        String testFilePath = "target/test.xls";

        ExchangeRates exchangeRates = new ExchangeRates();
        exchangeRates.addSingleRate(
                new SingleRate("CAD", "HUF", "2019-06-03", 214.4191283614));
        exchangeRates.addSingleRate(
                new SingleRate("CAD", "HUF", "2019-06-04", 213.0898021309));
        exchangeRates.addSingleRate(
                new SingleRate("CAD", "HUF", "2019-06-05", 213.3443928334));

        Printer printer = new XLSPrinter(testFilePath);

        // when
        printer.print(exchangeRates);

        // then
        ExcelFileTestHelper fileTestHelper = ExcelFileTestHelper.createXLSFileHelper(testFilePath);

        assertEquals("Rate date", fileTestHelper.getCellValueString(0, 0));
        assertEquals("CAD/HUF", fileTestHelper.getCellValueString(0, 1));
        assertEquals("2019-06-03", fileTestHelper.getCellValueDateAsString(1, 0));
        assertEquals(214.4191283614, fileTestHelper.getCellValueDouble(1, 1));
        assertEquals("2019-06-04", fileTestHelper.getCellValueDateAsString(2, 0));
        assertEquals(213.0898021309, fileTestHelper.getCellValueDouble(2, 1));
        assertEquals("2019-06-05", fileTestHelper.getCellValueDateAsString(3, 0));
        assertEquals(213.3443928334, fileTestHelper.getCellValueDouble(3, 1));

        fileTestHelper.closeDeleteFile();

    }

}
