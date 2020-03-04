package pl.connectis.print;

import pl.connectis.model.ExchangeRates;


public class IPrinterConsole extends Printable implements Printer {

    @Override
    public void print(ExchangeRates exchangeRates) {

        printToConsole(exchangeRates);

    }

}
