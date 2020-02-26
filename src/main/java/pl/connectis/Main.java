package pl.connectis;

import picocli.CommandLine;
import picocli.CommandLine.*;
import picocli.CommandLine.Model.CommandSpec;
import pl.connectis.command.ExchangeRateHistory;
import pl.connectis.command.ExchangeRateSingle;

import java.io.PrintWriter;
import java.util.Arrays;

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

    private static class ShortErrorMessageHandler implements IParameterExceptionHandler {

        public int handleParseException(ParameterException exception, String[] args) {

            CommandLine commandLine = exception.getCommandLine();
            PrintWriter printWriter = commandLine.getErr();

            if (exception.getValue() == null) {
                printWriter.println(exception.getMessage());
            } else {
                String optionNameForValue = args[Arrays.asList(args).indexOf(exception.getValue()) - 1];
                printWriter.println("Invalid value for option '" + optionNameForValue +
                        "': '" + exception.getValue() + "'.");
            }

            UnmatchedArgumentException.printSuggestions(exception, printWriter);
            printWriter.print(commandLine.getHelp().fullSynopsis());

            CommandSpec commandSpec = commandLine.getCommandSpec();
            printWriter.printf("Try '%s --help' for more information.%n", commandSpec.qualifiedName());

            return commandLine.getExitCodeExceptionMapper() != null
                    ? commandLine.getExitCodeExceptionMapper().getExitCode(exception)
                    : commandSpec.exitCodeOnInvalidInput();

        }

    }

}


