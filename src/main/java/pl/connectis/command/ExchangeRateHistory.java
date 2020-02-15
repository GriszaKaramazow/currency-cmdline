package pl.connectis.command;

import lombok.Getter;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import pl.connectis.model.CurrencySymbol;
import pl.connectis.model.HistoryTable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.connectis.print.FileContent;
import pl.connectis.print.PrintToConsole;
import pl.connectis.print.PrintToFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Command(name = "history",
        usageHelpAutoWidth = true,
        headerHeading = "history%n%n",
        header = "Prints to console an exchange rate for a single date starting from 1999-01-04.%n%nSupported currencies:%n" +
                "AUD, BGN, BRL, CAD, CHF, CNY, CZK, DKK, EUR, GBP, HKD, HRK, HUF, IDR, ILS, INR, %n" +
                "ISK, JPY, KRW, MXN, MYR, NOK, NZD, PHP, PLN, RON, RUB, SEK, SGD, THB, TRY, USD, ZAR.%n ",
        optionListHeading = "%nOptions:%n")
public class ExchangeRateHistory implements Runnable{

    @Option(names = {"-h", "--help"},
            usageHelp = true,
            description = "Display help menu.")
    private boolean usageHelpRequested;

    @Option(names = {"-b", "--base"},
            paramLabel = "<symbol>",
            defaultValue = "EUR",
            description = "Enter a symbol of a base currency. Default: ${DEFAULT-VALUE}.")
    private CurrencySymbol baseCurrency = CurrencySymbol.EUR;

    @Option(names = {"-q", "--quote"},
            paramLabel = "<symbol>",
            arity = "1..*",
            required = true,
            description = "Enter symbols of quote currencies separated by space (ie. -q EUR USD GBP). Required value.")
    private List<CurrencySymbol> quoteCurrencies;

    @Option(names = {"-s", "--start"},
            paramLabel = "<date>",
            required = true,
            description = "Enter a start date of a period in yyyy-MM-dd format starting from 1999-01-04. Required value.")
    private LocalDate startDate;

    @Option(names = {"-e", "--end"},
            paramLabel = "<date>",
            description = "Enter an end date of a period in yyyy-MM-dd format. Default: ${DEFAULT-VALUE}.")
    private LocalDate endDate = LocalDate.now();

    @Option(names = {"-f", "--file"},
            paramLabel = "FILE",
            description = "Enter a file (txt, csv, xls or xlsx) to save to or do not use, to print to console.")
    private String filePath;

    @Override
    public void run() {

        if (startDate.isBefore(LocalDate.of(1999,1,4)) ||
                endDate.isBefore(LocalDate.of(1999,1,4))) {
            AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW) +
                    "The exchange rate data are available from 1999-01-04" + Ansi.ansi().reset());
            return;
        }

        if (startDate.isAfter(endDate)) {
            AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW) + "from " + startDate +
                    " to " + endDate + "' is invalid period" + Ansi.ansi().reset());
            return;
        }

        String httpAddress = buildHttpRequestAddress();
        TableBuilder tableBuilder = new TableBuilder(httpAddress);
        tableBuilder.getExchangeRatesFromApi();
        HistoryTable historyTable = tableBuilder.getHistoryTable();

        if (filePath == null) {
            PrintToConsole printToConsole = new PrintToConsole(historyTable);
            printToConsole.showOnScreen();
        } else {
            FileContent fileContent = new FileContent(historyTable);
            fileContent.prepareFileContent();
            PrintToFile printToFile = new PrintToFile(filePath, fileContent);
            printToFile.printFileContentToFile();
        }

    }

    private String buildHttpRequestAddress() {

        StringBuilder stringBuilder = new StringBuilder("https://api.ratesapi.io/api/history?start_at=");
        stringBuilder.append(startDate);
        stringBuilder.append("&end_at=");
        stringBuilder.append(endDate);
        stringBuilder.append("&base=");
        stringBuilder.append(baseCurrency.toString());
        stringBuilder.append("&symbols=");
        String symbols = quoteCurrencies.stream()
                .map(Enum::toString)
                .collect(Collectors.joining(","));
        stringBuilder.append(symbols);
        return stringBuilder.toString();

    }

}
