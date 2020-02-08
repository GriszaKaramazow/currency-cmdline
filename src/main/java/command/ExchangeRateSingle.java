package command;

import lombok.Getter;
import lombok.SneakyThrows;
import model.CurrencySymbol;
import model.SimpleTable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.time.LocalDate;

@Getter
@Command(name = "single")
public class ExchangeRateSingle implements Runnable{

    @Option(names = {"-a", "--amount"}, paramLabel = " ", description = "amount of money to exchange")
    private Double currencyInputAmount;

    @Option(names = {"-f", "--from"}, defaultValue = "EUR", description = "Select input currency. Default: ${DEFAULT-VALUE}")
    private CurrencySymbol inputCurrency = CurrencySymbol.EUR;

    @Option(names = {"-t", "--to"}, description = "select output currency")
    private CurrencySymbol outputCurrency;

    @Option(names = {"-d", "--date"}, description = "date of money exchange. Default: today (${DEFAULT-VALUE})")
    private LocalDate exchangeDate = LocalDate.now();

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("ExchangeRateSingle");
        System.out.println("currencyInputAmount: " + currencyInputAmount);
        System.out.println("inputCurrency: " + inputCurrency);
        System.out.println("outputCurrency: " + outputCurrency);
        System.out.println("exchangeDate: " + exchangeDate);
        System.out.println();
        System.out.println(generateRequestAddress());

        TableBuilder tableBuilder = new TableBuilder(generateRequestAddress());
        tableBuilder.getRequest();
        SimpleTable simpleTable = tableBuilder.getSimpleTable();
        simpleTable.toString();
    }

    private String generateRequestAddress() {
        StringBuilder stringBuilder = new StringBuilder("https://api.ratesapi.io/api/");
        stringBuilder.append(exchangeDate);
        stringBuilder.append("?base=");
        stringBuilder.append(inputCurrency.toString());
        stringBuilder.append("&symbols=");
        stringBuilder.append(outputCurrency.toString());
        return stringBuilder.toString();
    }
}
