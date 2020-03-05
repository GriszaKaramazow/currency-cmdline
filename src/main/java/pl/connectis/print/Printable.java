package pl.connectis.print;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
abstract class Printable {

    private List<List<String>> prepareFileContent(ExchangeRates exchangeRates) {

        List<List<String>> fileContent = new ArrayList<>();

        String baseCurrency = exchangeRates.getHistoryRates().get(0).getBaseCurrency();
        List<String> quoteCurrencies = new ArrayList<>();
        List<String> rateDates = new ArrayList<>();

        for (SingleRate singleRate : exchangeRates.getHistoryRates()) {

            if (!quoteCurrencies.contains(singleRate.getQuoteCurrency())) {
                quoteCurrencies.add(singleRate.getQuoteCurrency());
            }

            if (!rateDates.contains(singleRate.getRateDate())) {
                rateDates.add(singleRate.getRateDate());
            }

        }

        Collections.sort(quoteCurrencies);
        Collections.sort(rateDates);

        fileContent.add(createTitleRowOfFileContent(baseCurrency, quoteCurrencies));

        for (String rateDate : rateDates) {

            List<String> currentRow = new ArrayList<>();
            currentRow.add(rateDate);

            for (String quoteCurrency : quoteCurrencies) {

                for (SingleRate singleRate : exchangeRates.getHistoryRates()) {

                    if (singleRate.getRateDate().equals(rateDate) && singleRate.getQuoteCurrency().equals(quoteCurrency)) {
                        currentRow.add(String.valueOf(singleRate.getRateValue()));
                    }

                }

            }

            fileContent.add(currentRow);

        }

        return fileContent;

    }

    private List<String> createTitleRowOfFileContent(String baseCurrency, List<String> quoteCurrencies) {

        List<String> titleRow = new ArrayList<>();
        titleRow.add("Rate date");

        for (String quoteCurrency : quoteCurrencies) {

            titleRow.add(baseCurrency + "/" + quoteCurrency);

        }

        return titleRow;

    }

    void printToConsole(ExchangeRates exchangeRates) {

        for (List<String> line : prepareFileContent(exchangeRates)) {

            log.info(String.join("\t\t", line));

        }

    }

    void printToTextFile(ExchangeRates exchangeRates, String filePath, String delimiter) {

        try {

            FileWriter fileWriter = new FileWriter(filePath);

            for (List<String> fileLine : prepareFileContent(exchangeRates)) {

                String lineText = String.join(delimiter, fileLine);
                fileWriter.write(lineText + "\n");

            }

            fileWriter.close();
            log.info("The data have been saved to '" + filePath + "'.");

        } catch (IOException exception) {

            log.error("An error occurred during saving data to the file", exception);

        }

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
