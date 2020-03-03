package pl.connectis.print;

import java.util.List;

public class CsvPrinterImpl extends Printable implements Printer {

    private final String filePath;
    private final List<List<String>> fileContent;

    public CsvPrinterImpl(String filePath, List<List<String>> fileContent) {
        this.filePath = filePath;
        this.fileContent = fileContent;
    }

    @Override
    public boolean print() {

        return printToTextFile(fileContent, filePath, ",");

    }

}
