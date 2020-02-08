package command;

import lombok.Getter;
import model.CurrencySymbol;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.time.LocalDate;

@Getter
@Command(name = "single")
public class ExchangeRateSingle implements Runnable{

    @Option(names = {"-a", "--amount"}, paramLabel = " ", description = "amount of money to exchange")
    private Double currencyInputAmount;

    @Option(names = {"-f", "--from"}, defaultValue = "EUR", description = "Select input currency. Default: ${DEFAULT-VALUE}")
    private String inputCurrency = String.valueOf(CurrencySymbol.EUR);

    @Option(names = {"-t", "--to"}, description = "select output currency")
    private String outputCurrency;

    @Option(names = {"-d", "--date"}, description = "date of money exchange. Default: today (${DEFAULT-VALUE})")
    private LocalDate exchangeDate = LocalDate.now();

    @Override
    public void run() {
        System.out.println("ExchangeRateSingle");
        System.out.println("currencyInputAmount: " + currencyInputAmount);
        System.out.println("inputCurrency: " + inputCurrency);
        System.out.println("outputCurrency: " + outputCurrency);
        System.out.println("exchangeDate: " + exchangeDate);
    }
}
