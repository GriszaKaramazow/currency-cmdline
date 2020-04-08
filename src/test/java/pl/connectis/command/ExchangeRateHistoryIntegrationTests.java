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

    @Test
    public void testsPrintingToXLSX() throws IOException {

        // given
        String testFilePath = "target/test.xlsx";
        String command = "history -b NZD -q CNY PHP THB -s 2019-12-11 -e 2019-12-13 -f " + testFilePath;

        // when
        commandLine.execute(command.split(" "));

        // then
        ExcelFileTestHelper fileTestHelper = new XLSXFileTestHelper(testFilePath);

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
