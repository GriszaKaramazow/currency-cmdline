package pl.connectis.print;

import lombok.extern.slf4j.Slf4j;
import pl.connectis.model.ExchangeRates;

@Slf4j
public class TXTPrinter extends PlainTextPrinter {

    private final String filePath;

    public TXTPrinter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void print(ExchangeRates exchangeRates) {

        log.info("Printing to txt file.");

        printToTextFile(exchangeRates, filePath, "\t");

    }

}
