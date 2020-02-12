import command.ExchangeRateHistory;
import command.ExchangeRateSingle;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;


@Command(name = "exchange-rate",
        subcommands = {
            ExchangeRateSingle.class,
            ExchangeRateHistory.class}
)
public class Main {


    @Option(names = {"-h", "--help"},
            usageHelp = true,
            description = "Displays help menu.")
    private boolean usageHelpRequested;

    public static void main(String[] args) {

        CommandLine commandLine = new CommandLine(new Main());
        commandLine.setCaseInsensitiveEnumValuesAllowed(true).execute(args);

    }

}
