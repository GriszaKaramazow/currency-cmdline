package pl.connectis.print;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import pl.connectis.model.ExchangeRates;

@Slf4j
public class XLSPrinter extends ExcelPrinter {

    private final String filePath;

    public XLSPrinter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void print(ExchangeRates exchangeRates) {

        log.info("Printing to xls file.");

        printToExcelFile(exchangeRates, new HSSFWorkbook(), filePath);

    }

}
