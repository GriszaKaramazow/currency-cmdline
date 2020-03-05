package pl.connectis.print;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.connectis.model.ExchangeRates;

@Slf4j
public class XLSXPrinter extends ExcelPrinter {

    private final String filePath;

    public XLSXPrinter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void print(ExchangeRates exchangeRates) {

        log.info("Printing to xlsx file.");

        printToExcelFile(exchangeRates, new XSSFWorkbook(), filePath);

    }

}