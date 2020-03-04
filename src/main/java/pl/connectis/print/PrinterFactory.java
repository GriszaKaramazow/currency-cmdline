package pl.connectis.print;

import lombok.extern.slf4j.Slf4j;
import pl.connectis.model.ExchangeRates;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class PrinterFactory {

    private final String filePath;

    public PrinterFactory(String filePath) {
        this.filePath = filePath;
    }

    public void print(ExchangeRates exchangeRates) {

        Printer printer = getPrinter();
        printer.print(exchangeRates);

    }

    private Printer getPrinter() {

        String fileExtension = getFileExtension();

        switch (fileExtension) {

            case "txt":

                log.info("Printing to txt file.");
                return new IPrinterTXT(filePath);

            case "csv":

                log.info("Printing to csv file.");
                return new IPrinterCSV(filePath);

            case "xls":

                log.info("Printing to xls file.");
                return new IPrinterXLS(filePath);

            case "xlsx":

                log.info("Printing to xlsx file.");
                return new IPrinterXLSX(filePath);

            default:

                log.info("Printing to console.");
                return new IPrinterConsole();

        }
    }

    private String getFileExtension() {

        String fileExtension = "";

        if (filePath == null) {
            return fileExtension;
        }

        int indexOfDot = filePath.lastIndexOf('.');

        if (indexOfDot <= 0) {
            log.warn("Inappropriate file path: '" + filePath + "'.");
            return fileExtension;
        } else {
            fileExtension = filePath.substring(indexOfDot + 1);
            log.debug("File extension: '" + fileExtension + "'.");
        }

        if (!Arrays.asList("txt", "csv", "xls", "xlsx").contains(fileExtension)) {
            log.warn("'" + fileExtension + "' is unsupported file format.");
        } else {
            createFile();
        }

        return fileExtension;

    }

    private void createFile() {

        File myFile = new File(filePath);

        try {

            myFile.createNewFile();
            log.debug("The file '" + filePath + "' have been created.");

        } catch (IOException exception) {

            log.error("Inappropriate file path: '" + filePath + "'.", exception);

        }

    }


}
