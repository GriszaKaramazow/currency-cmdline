package pl.connectis.print;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.connectis.model.ExchangeRates;

public class IPrinterXLSX extends Printable implements Printer {

    private final String filePath;

    public IPrinterXLSX(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void print(ExchangeRates exchangeRates) {

        printToExcelFile(exchangeRates, new XSSFWorkbook(), filePath);

    }

}