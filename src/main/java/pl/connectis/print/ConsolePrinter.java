package pl.connectis.print;

import lombok.extern.slf4j.Slf4j;
import pl.connectis.model.ExchangeRates;

import java.util.List;

@Slf4j
public class ConsolePrinter extends BasePrinter {

    @Override
    public void print(ExchangeRates exchangeRates) {

        log.info("Printing to console.");

        printToConsole(exchangeRates);

    }

    void printToConsole(ExchangeRates exchangeRates) {

        for (List<String> line : prepareFileContent(exchangeRates)) {

            log.info(String.join("\t\t", line));

        }

    }

}
