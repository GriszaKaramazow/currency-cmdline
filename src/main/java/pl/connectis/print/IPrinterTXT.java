package pl.connectis.print;

import java.util.List;

public class IPrinterTXT extends Printable implements Printer {

    private final String filePath;
    private final List<List<String>> fileContent;

    public IPrinterTXT(String filePath, List<List<String>> fileContent) {
        this.filePath = filePath;
        this.fileContent = fileContent;
    }

    @Override
    public boolean print() {

        return printToTextFile(fileContent, filePath, "\t");

    }

}
