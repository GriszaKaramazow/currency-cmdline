package pl.connectis.print;

import org.junit.jupiter.api.Test;
import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;
import pl.connectis.utils.ExcelFileTestHelper;
import pl.connectis.utils.XLSFileTestHelper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XLSPrinterTests {

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

        Printer printer = new XLSPrinter(testFilePath);

        // when
        printer.print(exchangeRates);

        // then
        ExcelFileTestHelper fileTestHelper = new XLSFileTestHelper(testFilePath);

        assertEquals("Rate date", fileTestHelper.getCellValueString(0, 0));
        assertEquals(baseCurrency + "/" + quoteCurrency, fileTestHelper.getCellValueString(0, 1));
        assertEquals(rateDateDayOne, fileTestHelper.getCellValueDateAsString(1, 0));
        assertEquals(rateValueDayOne, fileTestHelper.getCellValueDouble(1, 1));
        assertEquals(rateDateDayTwo, fileTestHelper.getCellValueDateAsString(2, 0));
        assertEquals(rateValueDayTwo, fileTestHelper.getCellValueDouble(2, 1));
        assertEquals(rateDateDayThree, fileTestHelper.getCellValueDateAsString(3, 0));
        assertEquals(rateValueDayThree, fileTestHelper.getCellValueDouble(3, 1));

        fileTestHelper.closeDeleteFile();

    }

}
