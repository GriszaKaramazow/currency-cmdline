package pl.connectis.print;

import pl.connectis.model.ExchangeRates;

public class IPrinterTXT extends Printable implements Printer {

    private final String filePath;

    public IPrinterTXT(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void print(ExchangeRates exchangeRates) {

        printToTextFile(exchangeRates, filePath, "\t");

    }

}
