package command;

import lombok.Getter;
import model.CurrencySymbol;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.time.LocalDate;

@Getter
@Command(name = "exchange")
public class CurrencyRate {

    @Option(names = {"-a", "--amount"}, paramLabel = " ", description = "amount of money to exchange")
    private Double currencyInputAmount;

    @Option(names = {"-f", "--from"}, defaultValue = "EUR", description = "Select input currency. Default: ${DEFAULT-VALUE}")
    private String inputCurrency = String.valueOf(CurrencySymbol.EUR);

    @Option(names = {"-t", "--to"}, description = "select output currency")
    private String getOutputCurrency;

    @Option(names = {"-i", "--input"}, description = "input data")
    private String inputData;

    @Option(names = {"-o", "--output"}, description = "output data")
    private String outputData;

    @Option(names = {"-d", "--date"}, description = "date of monet exchange")
    private LocalDate exchangeDate = LocalDate.now();

    @Option(names = {"-h", "--help"}, usageHelp = true)
    private boolean help;
    
}
