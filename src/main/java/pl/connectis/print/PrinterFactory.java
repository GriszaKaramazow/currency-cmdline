package pl.connectis.print;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class PrinterFactory {

    private final List<String> SUPPORTED_EXTENSIONS = Arrays.asList("txt", "csv", "xls", "xlsx");

    private final String filePath;

    public PrinterFactory(String filePath) {
        this.filePath = filePath;
    }

    public Printer getPrinter() {

        String fileExtension = getFileExtension();

        Map<String, Printer> printersMap = new HashMap<>();
        printersMap.put("txt", new TXTPrinter(filePath));
        printersMap.put("csv", new CSVPrinter(filePath));
        printersMap.put("xls", new XLSPrinter(filePath));
        printersMap.put("xlsx", new XLSXPrinter(filePath));

        return printersMap.getOrDefault(fileExtension, new ConsolePrinter());

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

        if (!SUPPORTED_EXTENSIONS.contains(fileExtension)) {
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
