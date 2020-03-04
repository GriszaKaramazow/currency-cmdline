package pl.connectis.print;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import pl.connectis.model.ExchangeRates;

public class IPrinterXLS extends Printable implements Printer {

    private final String filePath;

    public IPrinterXLS(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void print(ExchangeRates exchangeRates) {

        printToExcelFile(exchangeRates, new HSSFWorkbook(), filePath);

    }

}
