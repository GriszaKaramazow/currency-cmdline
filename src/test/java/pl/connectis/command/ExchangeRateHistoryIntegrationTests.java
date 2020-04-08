package pl.connectis.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import picocli.CommandLine;
import pl.connectis.utils.ExcelFileTestHelper;
import pl.connectis.utils.XLSFileTestHelper;
import pl.connectis.utils.XLSXFileTestHelper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(Lifecycle.PER_METHOD)
public class ExchangeRateHistoryIntegrationTests {

    private final CommandLine commandLine = new CommandLine(new ExchangeRate());

    @Test
    public void testsPrintingToXLS() throws IOException {

        // given
        String testFilePath = "target/test.xls";
        String command = "history -b CAD -q HUF -s 2019-06-03 -e 2019-06-05 -f " + testFilePath;

        // when
        commandLine.execute(command.split(" "));

        // then
        ExcelFileTestHelper fileTestHelper = new XLSFileTestHelper(testFilePath);

        String baseCurrency = "CAD";
        String quoteCurrency = "HUF";
        String rateDateDayOne = "2019-06-03";
        Double rateValueDayOne = 214.4191283614;
        String rateDateDayTwo = "2019-06-04";
        Double rateValueDayTwo = 213.0898021309;
        String rateDateDayThree = "2019-06-05";
        Double rateValueDayThree = 213.3443928334;

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

    @Test
    public void testsPrintingToXLSX() throws IOException {

        // given
        String testFilePath = "target/test.xlsx";
        String command = "history -b NZD -q CNY PHP THB -s 2019-12-11 -e 2019-12-13 -f " + testFilePath;

        // when
        commandLine.execute(command.split(" "));

        // then
        ExcelFileTestHelper fileTestHelper = new XLSXFileTestHelper(testFilePath);

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
