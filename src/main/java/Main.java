import command.ExchangeRateHistory;
import command.ExchangeRateSingle;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;

@Command(name = "exchange-rate",
        subcommands = {
            ExchangeRateSingle.class,
            ExchangeRateHistory.class}
)
public class Main implements Runnable {

    // VERSION
    @Option(names = {"-v", "--version"}, versionHelp = true, description = "Display version info")
    boolean versionInfoRequested;

    // HELP
    @Option(names = {"?", "-h", "--help"}, usageHelp = true)
    private boolean usageHelpRequested;

    public static void main(String[] args) throws IOException {

        CommandLine commandLine = new CommandLine(new Main());
        commandLine.execute(args);

    }

    @Override
    public void run() {

        System.out.println("Main");
    }
}
