package pl.connectis.command;

import lombok.Getter;
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
         sortOptions = false,
         usageHelpAutoWidth = true,
         headerHeading = "history%n%n",
         header = "Prints to console an exchange rate for a single date starting from 1999-01-04.%n",
         optionListHeading = "%nOptions:%n",
         footerHeading = "%nSupported currencies:%n",
         footer = "AUD, BGN, BRL, CAD, CHF, CNY, CZK, DKK, EUR, GBP, HKD,%n" +
                  "HRK, HUF, IDR, ILS, INR, ISK, JPY, KRW, MXN, MYR, NOK,%n" +
                  "NZD, PHP, PLN, RON, RUB, SEK, SGD, THB, TRY, USD, ZAR.")
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

    private final String yellowFontColor = "\u001b[33m";
    private final String resetFontColor = "\u001b[0m";

    @Override
    public void run() {

        if (startDate.isBefore(LocalDate.of(1999,1,4)) ||
                endDate.isBefore(LocalDate.of(1999,1,4))) {
            System.out.println(yellowFontColor +  "The exchange rate data are available from 1999-01-04" +
                    resetFontColor);
            return;
        }

        if (startDate.isAfter(endDate)) {
            System.out.println(yellowFontColor + "'from " + startDate + " to " + endDate + "' is invalid period" +
                    resetFontColor);
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
