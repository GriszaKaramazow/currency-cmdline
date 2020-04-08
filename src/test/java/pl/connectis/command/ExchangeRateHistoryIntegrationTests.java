package pl.connectis.command;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import picocli.CommandLine;
import pl.connectis.utils.TestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(Lifecycle.PER_METHOD)
public class ExchangeRateHistoryIntegrationTests {

    private final CommandLine commandLine = new CommandLine(new ExchangeRate());
    private final TestUtils testUtils = new TestUtils();

    @Test
    public void testsPrintingToXLS() throws IOException {

        // given
        String testFilePath = "target/test.xls";
        String command = "history -b CAD -q HUF -s 2019-06-03 -e 2019-06-05 -f " + testFilePath;

        // when
        commandLine.execute(command.split(" "));

        // then
        FileInputStream fileInputStream = new FileInputStream(testFilePath);
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = workbook.getSheet("Rates");

        String baseCurrency = "CAD";
        String quoteCurrency = "HUF";
        String rateDateDayOne = "2019-06-03";
        Double rateValueDayOne = 214.4191283614;
        String rateDateDayTwo = "2019-06-04";
        Double rateValueDayTwo = 213.0898021309;
        String rateDateDayThree = "2019-06-05";
        Double rateValueDayThree = 213.3443928334;

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

    @Test
    public void testsPrintingToXLSX() throws IOException {

        // given
        String testFilePath = "target/test.xlsx";
        String command = "history -b NZD -q CNY PHP THB -s 2019-12-11 -e 2019-12-13 -f " + testFilePath;

        // when
        commandLine.execute(command.split(" "));

        // then
        FileInputStream fileInputStream = new FileInputStream(testFilePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheet("Rates");

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
