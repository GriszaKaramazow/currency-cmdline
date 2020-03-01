package pl.connectis.print;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
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

        CellStyle boldCellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        boldCellStyle.setFont(font);

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat((short) 14);

        int rowCounter = 0;

        for (List<String> line : fileContent) {

            Row row = sheet.createRow(rowCounter++);

            int columnCounter = 0;

            for (String field : line) {
                Cell cell = row.createCell(columnCounter++);
                sheet.setDefaultColumnWidth(10);
                if (rowCounter == 1) {
                    cell.setCellValue(field);
                    cell.setCellStyle(boldCellStyle);
                } else if (columnCounter == 1) {
                    cell.setCellValue(LocalDate.parse(field));
                    cell.setCellStyle(dateCellStyle);
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

            log.error("An error occurred during saving data to file", exception);
            return false;

        }

    }

}
