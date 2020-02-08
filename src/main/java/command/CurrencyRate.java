package command;

import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.time.LocalDate;

@Command(name = "exchange")
public class CurrencyRate {

    @Option(names = {"-a", "-amount"}, description = "amount of money to exchange")
    private Double currencyInputAmount;

    @Option(names = {"-f", "--from"}, description = "select input currency")
    private String inputCurrency;

    @Option(names = {"-t", "--to"}, description = "select output currency")
    private String getOutputCurrency;

    @Option(names = {"-i", "--input"}, description = "input data")
    private String inputData;

    @Option(names = {"-o", "--output"}, description = "output data")
    private String outputData;

    @Option(names = {"-d", "--date"}, description = "date of monet exchange")
    private LocalDate exchangeDate;

    @Option(names = {"-h", "--help"}, usageHelp = true)
    private boolean help;
    
}
