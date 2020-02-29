package pl.connectis.command;

import lombok.Getter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.connectis.model.CurrencySymbol;
import pl.connectis.model.SimpleTable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Command(name = "single",
         sortOptions = false,
         usageHelpAutoWidth = true,
         headerHeading = "single%n%n",
         header = "Prints to console an exchange rate for a single date starting from 1999-01-04.%n",
         optionListHeading = "%nOptions:%n",
         footerHeading = "%nSupported currencies:%n",
         footer = "AUD, BGN, BRL, CAD, CHF, CNY, CZK, DKK, EUR, GBP, HKD, HRK, HUF, IDR, ILS, INR, ISK, JPY, KRW, " +
                 "MXN, MYR, NOK, NZD, PHP, PLN, RON, RUB, SEK, SGD, THB, TRY, USD, ZAR.")
public class ExchangeRateSingle implements Runnable{

    @Option(names = {"-h", "--help"},
            usageHelp = true,
            description = "Display help menu.")
    private boolean usageHelpRequested;

    @Option(names = {"-b", "--base"},
            paramLabel = "<symbol>",
            defaultValue = "EUR",
            description = "Enter a symbol of a base currency. Default: ${DEFAULT-VALUE}.")
    private CurrencySymbol baseCurrency = CurrencySymbol.EUR;

    @Option(names = {"-q", "--quote"},
            paramLabel = "<symbol>",
            arity = "1..*",
            required = true,
            description = "Enter symbols of quote currencies separated by space (ie. -q EUR USD GBP). Required value.")
    private List<CurrencySymbol> quoteCurrencies;

    @Option(names = {"-d", "--date"},
            paramLabel = "<date>",
            description = "Enter a date of exchange rate in yyyy-MM-dd format starting from 1999-01-04. Default: ${DEFAULT-VALUE}.")
    private LocalDate exchangeDate = LocalDate.now();

    private final String yellowFontColor = "\u001b[33m";
    private final String resetFontColor = "\u001b[0m";

    @Override
    public void run() {

        if (exchangeDate.isBefore(LocalDate.of(1999,1,4))) {
            System.out.println(yellowFontColor + "The exchange rate data are available from 1999-01-04" + resetFontColor);
            return;
        }

        ExchangeRatesRequester exchangeRatesRequester = new ExchangeRatesRequester(generateRequestAddress());
        SimpleTable simpleTable = exchangeRatesRequester.getSimpleTable();

        if (!validateDate(simpleTable)) {
            System.out.println(yellowFontColor + "Exchange rate unavailable for " + exchangeDate + resetFontColor);
        }

        printExchangeRateTable(simpleTable);

    }

    private String generateRequestAddress() {

        StringBuilder stringBuilder = new StringBuilder("https://api.ratesapi.io/api/");
        stringBuilder.append(exchangeDate);
        stringBuilder.append("?base=");
        stringBuilder.append(baseCurrency);
        stringBuilder.append("&symbols=");
        String symbols = quoteCurrencies.stream()
                .map(Enum::toString)
                .collect(Collectors.joining(","));
        stringBuilder.append(symbols);
        return stringBuilder.toString();

    }

    private boolean validateDate(SimpleTable simpleTable) {

        String receivedDateString = simpleTable.getDate();
        LocalDate receivedDate = LocalDate.parse(receivedDateString);
        return receivedDate.equals(exchangeDate);

    }

    private void printExchangeRateTable(SimpleTable simpleTable) {

        System.out.println("The exchange rate for " + simpleTable.getDate());
        HashMap<String, Double> rateHashMap = simpleTable.getRates();
        rateHashMap.forEach(this::printRate);

    }

    private void printRate(String quoteCurrency, Double rate) {

        System.out.print(quoteCurrency + "/");
        System.out.print(baseCurrency + ": ");
        System.out.println(rate);

    }
}
