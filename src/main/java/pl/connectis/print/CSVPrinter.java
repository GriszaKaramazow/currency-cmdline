package pl.connectis.print;

import lombok.extern.slf4j.Slf4j;
import pl.connectis.model.ExchangeRates;

@Slf4j
public class CSVPrinter extends PlainTextPrinter {

    private final String filePath;

    public CSVPrinter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void print(ExchangeRates exchangeRates) {

        log.info("Printing to csv file.");

        printToTextFile(exchangeRates, filePath, ",");

    }

}
