package pl.connectis;

import picocli.CommandLine;
import pl.connectis.command.ExchangeRate;
import pl.connectis.exception.ShortErrorMessageHandler;

public class Main {

    public static void main(String... args) {

        CommandLine commandLine = new CommandLine(new ExchangeRate());
        commandLine.setCaseInsensitiveEnumValuesAllowed(true)
                .setParameterExceptionHandler(new ShortErrorMessageHandler())
                .execute(args);

    }

}


