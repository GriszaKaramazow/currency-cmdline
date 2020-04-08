package pl.connectis.utils;

import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class XLSFileTestHelper extends ExcelFileTestHelper{

    private final String testFilePath;
    private final FileInputStream fileInputStream;
    private final HSSFWorkbook workbook;
    private final HSSFSheet sheet;

    public XLSFileTestHelper(String testFilePath) throws IOException {

        this.testFilePath = testFilePath;
        this.fileInputStream = new FileInputStream(testFilePath);
        this.workbook = new HSSFWorkbook(fileInputStream);
        this.sheet = workbook.getSheet("Rates");

    }

    public String getCellValue(int row, int cell) {

        String cellValueString = sheet.getRow(row).getCell(cell).toString();

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            cellValueString = String.valueOf(LocalDate.parse(cellValueString, formatter));

        } finally {

            return cellValueString;

        }

    }

    public void closeDeleteFile() throws IOException {

        workbook.close();
        fileInputStream.close();
        new File(testFilePath).delete();

    }

}
