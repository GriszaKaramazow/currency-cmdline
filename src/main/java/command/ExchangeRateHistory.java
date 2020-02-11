package command;

import print.FileContent;
import print.PrintToConsole;
import print.PrintToExcel;
import print.PrintToText;
import lombok.Getter;
import lombok.SneakyThrows;
import model.CurrencySymbol;
import model.HistoryTable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Command(name = "history")
public class ExchangeRateHistory implements Runnable{

    @Option(names = {"-a", "--amount"}, description = "amount of money to exchange")
    private Double baseAmount = 1d;

    @Option(names = {"-f", "--from"}, defaultValue = "EUR", description = "Select input currency. Default: ${DEFAULT-VALUE}")
    private CurrencySymbol inputCurrency = CurrencySymbol.EUR;

    @Option(names = {"-t", "--to"}, arity = "1..*", description = "select output currency")
    private List<CurrencySymbol> outputCurrencies;

    @Option(names = {"-s", "--start"}, required = true, description = "date of money exchangeDefault: today (${DEFAULT-VALUE})")
    private LocalDate startDate;

    @Option(names = {"-e", "--end"}, description = "")
    private LocalDate endDate = LocalDate.now();

    @Option(names = {"-o", "--output"}, description = "Output file (txt, csv, xls or xlsx). Default: print to console")
    private String outputData;



    @SneakyThrows
    @Override
    public void run() {

        String httpAddress = buildHttpRequestAddress();
        TableBuilder tableBuilder = new TableBuilder(httpAddress);
        tableBuilder.getExchangeRatesFromApi();
        HistoryTable historyTable = tableBuilder.getHistoryTable();
        historyTable.setBaseAmount(baseAmount);

        PrintToConsole printToConsole = new PrintToConsole(historyTable);
        printToConsole.printExchangeRateTable();

        FileContent fileContent = new FileContent(historyTable);
        fileContent.prepareFileContent();

        PrintToText printToText = new PrintToText(fileContent.getFileContent());
        printToText.saveAsTXT("test.txt");
        printToText.saveAsCSV("test.csv");

        PrintToExcel printToExcel = new PrintToExcel(fileContent.getFileContent());
        printToExcel.saveAsXLS("test.xls");
        printToExcel.saveAsXLSX("test.xlsx");

    }

    private String buildHttpRequestAddress() {

        StringBuilder stringBuilder = new StringBuilder("https://api.ratesapi.io/api/history?start_at=");
        stringBuilder.append(startDate);
        stringBuilder.append("&end_at=");
        stringBuilder.append(endDate);
        stringBuilder.append("&base=");
        stringBuilder.append(inputCurrency.toString());
        stringBuilder.append("&symbols=");
        String symbols = outputCurrencies.stream()
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

    private boolean validateFileExtension(String filePath) {

        List<String> supportedFileExtensions = Arrays.asList("txt", "csv", "xls", "xlsx");
        String fileExtension = getFileExtension(filePath);
        return supportedFileExtensions.contains(fileExtension);

    }

    private String getFileExtension(String filePath) {

        String fileExtension = "";

        int i = filePath.lastIndexOf('.');

        if (i > 0) {
            fileExtension = filePath.substring(i + 1);
        }

        return fileExtension;
    }
    
}
