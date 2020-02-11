package command;

import lombok.Getter;
import model.CurrencySymbol;
import model.HistoryTable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import print.FileContent;
import print.PrintToConsole;
import print.PrintToFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Command(name = "history",
        usageHelpAutoWidth = true,
        headerHeading = "exchange-rate history%n%n",
        header = "Prints to console an exchange rate for a single date.%n%nSupported currencies:%n" +
                "@|fg(yellow) AUD, BGN, BRL, CAD, CHF, CNY, CZK, DKK, EUR, GBP, HKD,|@%n" +
                "HRK, HUF, IDR, ILS, INR, ISK, JPY, KRW, MXN, MYR, NOK,%n" +
                "NZD, PHP, PLN, RON, RUB, SEK, SGD, THB, TRY, USD, ZAR.%n ",
        optionListHeading = "%nOptions:%n")
public class ExchangeRateHistory implements Runnable{

    @Option(names = {"-h", "--help"},
            usageHelp = true,
            description = "Display help menu.")
    private boolean usageHelpRequested;

    @Option(names = {"-b", "--base"},
            paramLabel = "SYMBOL",
            defaultValue = "EUR",
            description = "Enter symbol of a base currency. Default: ${DEFAULT-VALUE}.")
    private CurrencySymbol baseCurrency = CurrencySymbol.EUR;

    @Option(names = {"-q", "--quote"},
            arity = "1..*",
            paramLabel = "SYMBOL",
            description = "Enter symbols of quote currencies separated by space (ie. -q EUR USD GBP). Required value.")
    private List<CurrencySymbol> quoteCurrencies;

    @Option(names = {"-s", "--start"},
            paramLabel = "DATE",
            required = true,
            description = "Enter a start date of a period in yyyy-MM-dd format. Required value.")
    private LocalDate startDate;

    @Option(names = {"-e", "--end"},
            paramLabel = "DATE",
            description = "Enter an end date of a period in yyyy-MM-dd format. Default: ${DEFAULT-VALUE}.")
    private LocalDate endDate = LocalDate.now();

    @Option(names = {"-f", "--file"},
            paramLabel = "FILE",
            description = "Enter a file (txt, csv, xls or xlsx) to save to or do not use to print to console.")
    private String filePath;

    @Override
    public void run() {

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
                .map(currencySymbol -> currencySymbol.toString())
                .collect(Collectors.joining(","));
        stringBuilder.append(symbols);
        return stringBuilder.toString();

    }
    
    private void printExchangeRareTable(FileContent fileContent) {
        List<List<String>> exchangeRareTable = fileContent.getFileContent();

        for (List<String> line : exchangeRareTable) {
            for (String cell : line) {
                System.out.print(cell);
                System.out.print("\t");
            }
            System.out.println();
        }
    }


    
}
