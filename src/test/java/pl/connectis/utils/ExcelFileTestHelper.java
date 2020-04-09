package pl.connectis.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Data
public class ExcelFileTestHelper {

    private final String testFilePath;
    private final FileInputStream fileInputStream;
    private final Workbook workbook;
    private final Sheet sheet;

    private ExcelFileTestHelper(String testFilePath, FileInputStream fileInputStream, Workbook workbook) throws IOException {
        this.testFilePath = testFilePath;
        this.fileInputStream = fileInputStream;
        this.workbook = workbook;
        this.sheet = workbook.getSheet("Rates");
    }

    public static ExcelFileTestHelper createXLSXFileHelper(String filepath) {

        try {

            FileInputStream fileInputStream = new FileInputStream(filepath);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            return new ExcelFileTestHelper(filepath, fileInputStream, workbook);

        } catch (IOException exception) {

            log.error("Unable to read a workbook from a file: " + filepath + "'.");
            throw new IllegalStateException("Unable to read a workbook from a file: " + filepath + "'.");

        }

    }

    public static ExcelFileTestHelper createXLSFileHelper(String filepath) {

        try {

            FileInputStream fileInputStream = new FileInputStream(filepath);
            Workbook workbook = new HSSFWorkbook(fileInputStream);
            return new ExcelFileTestHelper(filepath, fileInputStream, workbook);

        } catch (IOException exception) {

            log.error("Unable to read a workbook from a file: " + filepath + "'.");
            throw new IllegalStateException("Unable to read a workbook from a file: " + filepath + "'.");

        }

    }

    public String getCellValueString(int row, int cell) {

        return sheet.getRow(row).getCell(cell).getStringCellValue();

    }

    public double getCellValueDouble(int row, int cell) {

        return sheet.getRow(row).getCell(cell).getNumericCellValue();

    }

    public String getCellValueDateAsString(int row, int cell) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        String cellValueString = sheet.getRow(row).getCell(cell).toString();
        return String.valueOf(LocalDate.parse(cellValueString, formatter));

    }

    public void closeDeleteFile() throws IOException {

        workbook.close();
        fileInputStream.close();
        new File(testFilePath).delete();

    }

}
