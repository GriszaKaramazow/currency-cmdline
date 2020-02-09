package command;

import lombok.Getter;
import lombok.SneakyThrows;
import model.CurrencySymbol;
import model.SimpleTable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Command(name = "single")
public class ExchangeRateSingle implements Runnable{

    @Option(names = {"-a", "--amount"}, paramLabel = " ", description = "amount of money to exchange")
    private Double baseAmount = 1d;

    @Option(names = {"-f", "--from"}, defaultValue = "EUR", description = "Select input currency. Default: ${DEFAULT-VALUE}")
    private CurrencySymbol inputCurrency = CurrencySymbol.EUR;

    @Option(names = {"-t", "--to"}, arity = "1..*", description = "select output currency")
    private List<CurrencySymbol> outputCurrencies;

    @Option(names = {"-d", "--date"}, description = "date of money exchange. Default: today (${DEFAULT-VALUE})")
    private LocalDate exchangeDate = LocalDate.now();

    @SneakyThrows
    @Override
    public void run() {

        TableBuilder tableBuilder = new TableBuilder(generateRequestAddress());
        tableBuilder.getExchangeRatesFromApi();
        SimpleTable simpleTable = tableBuilder.getSimpleTable();
        printExchangeRateTable(simpleTable);

    }

    private String generateRequestAddress() {

        StringBuilder stringBuilder = new StringBuilder("https://api.ratesapi.io/api/");
        stringBuilder.append(exchangeDate);
        stringBuilder.append("?base=");
        stringBuilder.append(inputCurrency);
        stringBuilder.append("&symbols=");
        String symbols = outputCurrencies.stream()
                .map(currencySymbol -> currencySymbol.toString())
                .collect(Collectors.joining(","));
        stringBuilder.append(symbols);
        return stringBuilder.toString();

    }

    private void printExchangeRateTable(SimpleTable simpleTable) {

        System.out.println("The exchange rate for " + simpleTable.getDate());
        HashMap<String, Double> rateHashMap = simpleTable.getRates();
        rateHashMap.forEach(this::printRate);

    }

    private void printRate(String outputCurrency, Double rate) {

        System.out.print(baseAmount + " ");
        System.out.print(inputCurrency + " = ");
        System.out.print(baseAmount *rate + " ");
        System.out.println(outputCurrency);

    }
}
