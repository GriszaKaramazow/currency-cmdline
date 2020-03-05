package pl.connectis.print;

import pl.connectis.model.ExchangeRates;

public class TXTPrinter extends PlainTextPrinter {

    private final String filePath;

    public TXTPrinter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void print(ExchangeRates exchangeRates) {


        printToTextFile(exchangeRates, filePath, "\t");

    }

}
