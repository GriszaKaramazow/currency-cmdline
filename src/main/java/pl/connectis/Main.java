package pl.connectis;

import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.connectis.command.ExchangeRateHistory;
import pl.connectis.command.ExchangeRateSingle;


@Command(name = "exchange-rate",
        subcommands = {
            ExchangeRateSingle.class,
            ExchangeRateHistory.class}
)
public class Main implements Runnable {

    @Option(names = {"-h", "--help"},
            usageHelp = true,
            description = "Displays help menu.")
    private boolean usageHelpRequested;

    public static void main(String[] args) {

        CommandLine commandLine = new CommandLine(new Main());
        commandLine.setCaseInsensitiveEnumValuesAllowed(true)
                .execute(args);

    }

    @Override
    public void run() {

        System.out.println("Use -h or --help to display help menu.");

    }
}
