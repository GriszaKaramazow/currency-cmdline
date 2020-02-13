package pl.connectis.print;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class PrintToExcel {

    private final List<List<String>> fileContent;

    public PrintToExcel(List<List<String>> fileContent) {
        this.fileContent = fileContent;
    }

    public boolean saveAsXLS(String filePath) {

        return saveAsExcel(new HSSFWorkbook(), filePath);

    }

    public boolean saveAsXLSX(String filePath) {

        return saveAsExcel(new XSSFWorkbook(), filePath);

    }

    private boolean saveAsExcel(Workbook workbook, String filePath) {

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
                    cell.setCellValue(Double.parseDouble(field));
                }

            }

        }

        try {

            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            return true;

        } catch (IOException exception) {

            return false;

        }

    }

}
