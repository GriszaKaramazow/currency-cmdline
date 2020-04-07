package pl.connectis.print;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

public class XLSPrinterTests {

    private final String testFilePath = "target/test.xls";

    @Test
    public void testsPrintingToXLS() throws IOException {

        // given
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
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(testFilePath));
        HSSFSheet sheet = workbook.getSheet("Rates");

        assertEquals("Rate date", getCellValue(sheet, 0, 0));
        assertEquals(baseCurrency + "/" + quoteCurrency, getCellValue(sheet, 0, 1));
        assertEquals(rateDateDayOne, getCellValue(sheet, 1, 0));
        assertEquals(String.valueOf(rateValueDayOne), getCellValue(sheet, 1, 1));
        assertEquals(rateDateDayTwo, getCellValue(sheet, 2, 0));
        assertEquals(String.valueOf(rateValueDayTwo), getCellValue(sheet, 2, 1));
        assertEquals(rateDateDayThree, getCellValue(sheet, 3, 0));
        assertEquals(String.valueOf(rateValueDayThree), getCellValue(sheet, 3, 1));

        workbook.close();

    }

    @AfterEach
    public void tearDown() {

        File testFile = new File(testFilePath);
        testFile.delete();

    }

    private String getCellValue(HSSFSheet sheet, int row, int cell) {

        String cellValueString = sheet.getRow(row).getCell(cell).toString();

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            cellValueString = String.valueOf(LocalDate.parse(cellValueString, formatter));

        } finally {

            return cellValueString;

        }

    }

}
