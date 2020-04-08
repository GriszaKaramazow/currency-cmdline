package pl.connectis.utils;

import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class XLSXFileTestHelper extends ExcelFileTestHelper {

    private final String testFilePath;
    private final FileInputStream fileInputStream;
    private final XSSFWorkbook workbook;
    private final XSSFSheet sheet;

    public XLSXFileTestHelper(String testFilePath) throws IOException {

        this.testFilePath = testFilePath;
        this.fileInputStream = new FileInputStream(testFilePath);
        this.workbook = new XSSFWorkbook(fileInputStream);
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
