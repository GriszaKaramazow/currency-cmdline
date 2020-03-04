package pl.connectis.print;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
abstract class Printable {

    boolean printToTextFile(List<List<String>> fileContent, String filePath, String delimiter) {

        try {

            FileWriter fileWriter = new FileWriter(filePath);

            for (List<String> fileLine : fileContent) {
                String lineText = String.join(delimiter, fileLine);
                fileWriter.write(lineText + "\n");
            }

            fileWriter.close();
            return true;

        } catch (IOException exception) {

            log.error("An error occurred during saving data to the file", exception);
            return false;

        }

    }

    boolean printToExcelFile(List<List<String>> fileContent, Workbook workbook, String filePath) {

        Sheet sheet = workbook.createSheet("Rate");

        CellStyle boldCellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)10);
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

            log.error("An error occurred during saving data to the file", exception);
            return false;

        }

    }

    void printToConsole(List<List<String>> fileContent) {

        for (List<String> line : fileContent) {

            log.info(String.join("\t\t", line));

        }

    }

}
