package command;

import lombok.Getter;
import model.CurrencySymbol;
import model.SimpleTable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Command(name = "single",
         usageHelpAutoWidth = true,
         headerHeading = "exchange-rate single%n%n",
         header = "Prints to console an exchange rate for a single date starting from 1999-01-04.%n%nSupported currencies:%n" +
                "AUD, BGN, BRL, CAD, CHF, CNY, CZK, DKK, EUR, GBP, HKD,%n" +
                "HRK, HUF, IDR, ILS, INR, ISK, JPY, KRW, MXN, MYR, NOK,%n" +
                "NZD, PHP, PLN, RON, RUB, SEK, SGD, THB, TRY, USD, ZAR.%n",
         optionListHeading = "%nOptions:%n")

public class ExchangeRateSingle implements Runnable{

    @Option(names = {"-h", "--help"},
            usageHelp = true,
            description = "Display help menu.")
    private boolean usageHelpRequested;

    @Option(names = {"-b", "--base"},
            paramLabel = "<symbol>",
            defaultValue = "EUR",
            description = "Enter symbol of a base currency. Default: ${DEFAULT-VALUE}.")
    private CurrencySymbol baseCurrency = CurrencySymbol.EUR;

    @Option(names = {"-q", "--quote"},
            paramLabel = "<symbol>",
            arity = "1..*",
            description = "Enter symbols of quote currencies separated by space (ie. -q EUR USD GBP). Required value.")
    private List<CurrencySymbol> quoteCurrencies;

    @Option(names = {"-d", "--date"},
            paramLabel = "<date>",
            description = "Enter a date of exchange rate in yyyy-MM-dd format starting from 1999-01-04. Default: ${DEFAULT-VALUE}.")
    private LocalDate exchangeDate = LocalDate.now();

    @Override
    public void run() {

        if(exchangeDate.isBefore(LocalDate.of(1999,01,04))) {
            System.out.println("The exchange rate data are available from 1999-01-04");
            return;
        }

        TableBuilder tableBuilder = new TableBuilder(generateRequestAddress());
        tableBuilder.getExchangeRatesFromApi();
        SimpleTable simpleTable = tableBuilder.getSimpleTable();

        if (!validateDate(simpleTable)) {
            System.out.println("Exchange rate unavailable for " + exchangeDate.toString());
            System.out.println("A later exchange rate is available for " + simpleTable.getDate());
            return;
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
                .map(currencySymbol -> currencySymbol.toString())
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
