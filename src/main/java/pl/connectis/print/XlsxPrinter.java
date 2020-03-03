package pl.connectis.print;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class XlsxPrinter extends Printable implements Printer {

    private final String filePath;
    private final List<List<String>> fileContent;

    public XlsxPrinter(String filePath, List<List<String>> fileContent) {
        this.filePath = filePath;
        this.fileContent = fileContent;
    }

    @Override
    public boolean print() {

        return printToExcelFile(fileContent, new XSSFWorkbook(), filePath);

    }

}