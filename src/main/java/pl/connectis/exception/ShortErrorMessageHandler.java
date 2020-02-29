package pl.connectis.exception;

import picocli.CommandLine;
import picocli.CommandLine.IParameterExceptionHandler;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.UnmatchedArgumentException;

import java.io.PrintWriter;
import java.util.Arrays;

public class ShortErrorMessageHandler implements IParameterExceptionHandler {

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
