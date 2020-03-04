package pl.connectis.print;

import java.util.List;

public class IPrinterCSV extends Printable implements Printer {

    private final String filePath;
    private final List<List<String>> fileContent;

    public IPrinterCSV(String filePath, List<List<String>> fileContent) {
        this.filePath = filePath;
        this.fileContent = fileContent;
    }

    @Override
    public boolean print() {

        return printToTextFile(fileContent, filePath, ",");

    }

}
