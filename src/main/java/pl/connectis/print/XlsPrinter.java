package pl.connectis.print;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

public class XlsPrinter extends Printable implements Printer {

    private final String filePath;
    private final List<List<String>> fileContent;

    public XlsPrinter(String filePath, List<List<String>> fileContent) {
        this.filePath = filePath;
        this.fileContent = fileContent;
    }

    @Override
    public boolean print() {

        return printToExcelFile(fileContent, new HSSFWorkbook(), filePath);

    }

}
