package pl.connectis;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.connectis.command.ExchangeRateHistory;
import pl.connectis.command.ExchangeRateSingle;
import pl.connectis.exception.ShortErrorMessageHandler;

@Command(name = "currency-cmdline-0.3.0.jar",
        subcommands = {
            ExchangeRateSingle.class,
            ExchangeRateHistory.class}
)
public class Main implements Runnable {

    @Option(names = {"-h", "--help"},
            usageHelp = true,
            description = "Displays help menu.")
    private boolean usageHelpRequested;

    public static void main(String... args) {

        CommandLine commandLine = new CommandLine(new Main());
        commandLine.setCaseInsensitiveEnumValuesAllowed(true)
                .setParameterExceptionHandler(new ShortErrorMessageHandler())
                .execute(args);

    }

    public void run() {

        System.out.println("Use -h or --help to display help menu.");

    }

}


