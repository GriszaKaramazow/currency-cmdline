package pl.connectis.command;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Slf4j
@Command(name = "currency-cmdline-0.4.0.jar",
        subcommands = {
                ExchangeRateSingle.class,
                ExchangeRateHistory.class}
)
public class ExchangeRate implements Runnable {

    @Option(names = {"-h", "--help"},
            usageHelp = true,
            description = "Displays help menu.")
    private boolean usageHelpRequested;

    public void run() {

        log.info("Use -h or --help to display help menu.");

    }

}
