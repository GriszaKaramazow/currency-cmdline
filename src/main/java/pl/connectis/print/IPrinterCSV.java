package pl.connectis.print;

import pl.connectis.model.ExchangeRates;

public class IPrinterCSV extends Printable implements Printer {

    private final String filePath;

    public IPrinterCSV(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void print(ExchangeRates exchangeRates) {

        printToTextFile(exchangeRates, filePath, ",");

    }

}
