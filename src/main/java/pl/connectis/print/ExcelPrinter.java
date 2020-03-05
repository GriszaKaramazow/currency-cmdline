package pl.connectis.print;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import pl.connectis.model.ExchangeRates;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
abstract class ExcelPrinter extends BasePrinter {

    @Override
    public void print(ExchangeRates exchangeRates) {

    }

    void printToExcelFile(ExchangeRates exchangeRates, Workbook workbook, String filePath) {

        Sheet sheet = workbook.createSheet("Rate");

        CellStyle boldCellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)10);
        font.setBold(true);
        boldCellStyle.setFont(font);

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat((short) 14);

        int rowCounter = 0;

        for (List<String> line : prepareFileContent(exchangeRates)) {

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
            log.info("The data have been saved to '" + filePath + "'.");

        } catch (IOException exception) {

            log.error("An error occurred during saving data to the file", exception);

        }

    }

}
