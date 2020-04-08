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

        assertEquals("Rate date", fileTestHelper.getCellValue(0, 0));
        assertEquals(baseCurrency + "/" + quoteCurrency, fileTestHelper.getCellValue(0, 1));
        assertEquals(rateDateDayOne, fileTestHelper.getCellValue(1, 0));
        assertEquals(String.valueOf(rateValueDayOne), fileTestHelper.getCellValue(1, 1));
        assertEquals(rateDateDayTwo, fileTestHelper.getCellValue(2, 0));
        assertEquals(String.valueOf(rateValueDayTwo), fileTestHelper.getCellValue(2, 1));
        assertEquals(rateDateDayThree, fileTestHelper.getCellValue(3, 0));
        assertEquals(String.valueOf(rateValueDayThree), fileTestHelper.getCellValue(3, 1));

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

        assertEquals("Rate date", fileTestHelper.getCellValue(0, 0));
        assertEquals(baseCurrency + "/" + quoteCurrencyCNY, fileTestHelper.getCellValue(0, 1));
        assertEquals(baseCurrency + "/" + quoteCurrencyPHP, fileTestHelper.getCellValue(0, 2));
        assertEquals(baseCurrency + "/" + quoteCurrencyTHB, fileTestHelper.getCellValue(0, 3));

        assertEquals(rateDateDayOne, fileTestHelper.getCellValue(1, 0));
        assertEquals(String.valueOf(rateValueDayOneCNY), fileTestHelper.getCellValue(1, 1));
        assertEquals(String.valueOf(rateValueDayOnePHP), fileTestHelper.getCellValue(1, 2));
        assertEquals(String.valueOf(rateValueDayOneTHB), fileTestHelper.getCellValue(1, 3));

        assertEquals(rateDateDayTwo, fileTestHelper.getCellValue(2, 0));
        assertEquals(String.valueOf(rateValueDayTwoCNY), fileTestHelper.getCellValue(2, 1));
        assertEquals(String.valueOf(rateValueDayTwoPHP), fileTestHelper.getCellValue(2, 2));
        assertEquals(String.valueOf(rateValueDayTwoTHB), fileTestHelper.getCellValue(2, 3));

        assertEquals(rateDateDayThree, fileTestHelper.getCellValue(3, 0));
        assertEquals(String.valueOf(rateValueDayThreeCNY), fileTestHelper.getCellValue(3, 1));
        assertEquals(String.valueOf(rateValueDayThreePHP), fileTestHelper.getCellValue(3, 2));
        assertEquals(String.valueOf(rateValueDayThreeTHB), fileTestHelper.getCellValue(3, 3));

        fileTestHelper.closeDeleteFile();

    }

}
