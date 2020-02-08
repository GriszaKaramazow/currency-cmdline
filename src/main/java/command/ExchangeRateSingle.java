package command;

import lombok.Getter;
import lombok.SneakyThrows;
import model.CurrencySymbol;
import model.SimpleTable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.time.LocalDate;
import java.util.HashMap;

@Getter
@Command(name = "single")
public class ExchangeRateSingle implements Runnable{

    @Option(names = {"-a", "--amount"}, paramLabel = " ", description = "amount of money to exchange")
    private Double currencyInputAmount = 1d;

    @Option(names = {"-f", "--from"}, defaultValue = "EUR", description = "Select input currency. Default: ${DEFAULT-VALUE}")
    private CurrencySymbol inputCurrency = CurrencySymbol.EUR;

    @Option(names = {"-t", "--to"}, description = "select output currency")
    private CurrencySymbol outputCurrency;

    @Option(names = {"-d", "--date"}, description = "date of money exchange. Default: today (${DEFAULT-VALUE})")
    private LocalDate exchangeDate = LocalDate.now();

    @SneakyThrows
    @Override
    public void run() {
        TableBuilder tableBuilder = new TableBuilder(generateRequestAddress());
        tableBuilder.getRequest();
        SimpleTable simpleTable = tableBuilder.getSimpleTable();
        printExchangeRateTable(simpleTable);

    }

    private String generateRequestAddress() {

        StringBuilder stringBuilder = new StringBuilder("https://api.ratesapi.io/api/");
        stringBuilder.append(exchangeDate);
        stringBuilder.append("?base=");
        stringBuilder.append(inputCurrency.toString());

        if (inputCurrency != null) {
            stringBuilder.append("&symbols=");
            stringBuilder.append(outputCurrency.toString());
        }

        return stringBuilder.toString();

    }

    private void printExchangeRateTable(SimpleTable simpleTable) {

        System.out.println("The exchange rate for " + simpleTable.getDate());
        HashMap<String, Double> rateHashMap = simpleTable.getRates();
        rateHashMap.forEach(this::printRate);

    }

    private void printRate(String outputCurrency, Double rate) {

        System.out.print(currencyInputAmount + " ");
        System.out.print(inputCurrency + " = ");
        System.out.print(currencyInputAmount*rate + " ");
        System.out.println(outputCurrency);

    }
}
