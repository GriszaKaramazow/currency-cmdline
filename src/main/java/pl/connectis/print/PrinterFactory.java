package pl.connectis.print;

import lombok.extern.slf4j.Slf4j;
import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class PrinterFactory {

    private final String filePath;
    private final ExchangeRates exchangeRates;

    private String fileExtension;

    public PrinterFactory(String filePath, ExchangeRates exchangeRates) {

        this.filePath = filePath;
        this.exchangeRates = exchangeRates;

    }

    public void print() {

        List<List<String>> fileContent = prepareFileContent();

        if (filePath == null) {

            Printer printer = new IPrinterConsole(fileContent);
            printer.print();

        } else {

            setFileExtension();
            printToFile(fileContent);

        }

    }

    public void printToFile(List<List<String>> fileContent) {

        if (!validateFileExtension()) {
            return;
        }

        if (!validateFilePath()) {
            return;
        }
        
        Printer printer;

        switch (fileExtension) {

            case "txt":

                printer = new IPrinterTXT(filePath, fileContent);
                break;

            case "csv":

                printer = new IPrinterCSV(filePath, fileContent);
                break;

            case "xls":

                printer = new IPrinterXLS(filePath, fileContent);
                break;

            case "xlsx":

                printer = new IPrinterXLSX(filePath, fileContent);
                break;

            default:
                printer = new IPrinterConsole(fileContent);
        }
        
        printResult(printer.print());

    }

    private boolean validateFilePath() {

        File myFile = new File(filePath);

        try {

            myFile.createNewFile();
            log.debug("The file '" + filePath + "' have been created.");
            return true;

        } catch (IOException exception) {

            log.error("Inappropriate file path '" + filePath + "'.", exception);
            return false;

        }

    }

    private boolean validateFileExtension() {

        if (fileExtension == null) {
            log.error("An inappropriate file '" + filePath + "'.");
            return false;
        } else if (!Arrays.asList("txt", "csv", "xls", "xlsx").contains(fileExtension)) {
            log.error(fileExtension + " is unsupported file format.");
            return false;
        }

        return true;

    }

    private List<List<String>> prepareFileContent() {

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

                    if (singleRate.getRateDate() == rateDate && singleRate.getQuoteCurrency() == quoteCurrency) {
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
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(baseCurrency);
            stringBuilder.append("/");
            stringBuilder.append(quoteCurrency);
            titleRow.add(stringBuilder.toString());
        }

        return titleRow;

    }

    private void setFileExtension() {

        String fileExtension = "";
        int i = filePath.lastIndexOf('.');

        if (i <= 0) {
            this.fileExtension = null;
            log.debug("An inappropriate file format: '" + filePath + "'.");
        } else {
            this.fileExtension = filePath.substring(i + 1);
            log.debug("File extension: " + fileExtension);
        }

    }

    private void printResult(boolean result) {

        if (result) {
            log.info("The data have been saved to '" + filePath + "'.");
        } else {
            log.error("An error occurred during saving the data to the file.");
        }

    }

}
