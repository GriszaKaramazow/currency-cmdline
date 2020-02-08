package command;

import lombok.Getter;
import model.CurrencySymbol;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.time.LocalDate;

@Getter
@Command(name = "history")
public class ExchangeRateHistory implements Runnable{

    @Option(names = {"-a", "--amount"}, description = "amount of money to exchange")
    private Double currencyInputAmount;

    // CURRENCIES
    @Option(names = {"-f", "--from"}, defaultValue = "EUR", description = "Select input currency. Default: ${DEFAULT-VALUE}")
    private String inputCurrency = String.valueOf(CurrencySymbol.EUR);

    @Option(names = {"-t", "--to"}, description = "select output currency")
    private String outputCurrency;

    // DATES
    @Option(names = {"-s", "--start"}, description = "date of money exchange")
    private LocalDate startDate;

    @Option(names = {"-e", "--end"}, arity = "2", description = "")
    private LocalDate endDate;

    @Option(names = {"-o", "--output"}, description = "Output file. Default: print to console")
    private String outputData;

    @Override
    public void run() {
        System.out.println("ExchangeRateHistory");
        System.out.println("currencyInputAmount: " + currencyInputAmount);
        System.out.println("inputCurrency: " + inputCurrency);
        System.out.println("outputCurrency: " + outputCurrency);
        System.out.println("startDate: " + startDate);
        System.out.println("endDate: " + endDate);
        System.out.println("outputData: " + outputData);
    }
}
