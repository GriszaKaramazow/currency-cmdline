package print;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class PrintToExcel {

    private final List<List<String>> fileContent;

    public PrintToExcel(List<List<String>> fileContent) {
        this.fileContent = fileContent;
    }

    public void saveAsXLS(String filePath) {

        saveAsExcel(new HSSFWorkbook(), filePath);

    }

    public void saveAsXLSX(String filePath) {

        saveAsExcel(new XSSFWorkbook(), filePath);

    }

    private void saveAsExcel(Workbook workbook, String filePath) {

        Sheet sheet = workbook.createSheet("Rate");

        int rowCounter = 0;

        for (List<String> line : fileContent) {

            Row row = sheet.createRow(++rowCounter);

            int columnCounter = 0;

            for (String field : line) {
                Cell cell = row.createCell(++columnCounter);
                if (rowCounter == 1) {
                    cell.setCellValue(field);
                } else if (columnCounter == 1) {
                    cell.setCellValue(LocalDate.parse(field));
                } else {
                    cell.setCellValue(Double.valueOf(field));
                }

            }

        }

        try {
            File myFile = new File(filePath);
            myFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
