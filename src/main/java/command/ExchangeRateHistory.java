package command;

import lombok.Getter;
import lombok.SneakyThrows;
import model.CurrencySymbol;
import model.HistoryTable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
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

    @Option(names = {"-o", "--output"}, description = "Output file. Default: print to console")
    private String outputData;

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("ExchangeRateHistory");

        System.out.println(generateRequestAddress());
        TableBuilder tableBuilder = new TableBuilder(generateRequestAddress());
        tableBuilder.getRequest();
        HistoryTable historyTable= tableBuilder.getHistoryTable();
        printExchangeRateTable(historyTable);
        printExchangeRareTable(historyTable);
    }

    private String generateRequestAddress() {

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
    
    private void printExchangeRareTable(HistoryTable historyTable) {
        List<List<String>> exchangeRareTable = createExchangeRateTable(historyTable);

        for (List<String> line : exchangeRareTable) {
            for (String cell : line) {
                System.out.print(cell);
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    private List<String> createTitleRowForExchangeRateTable(HistoryTable historyTable) {

        List<String> titleRow = new ArrayList<>();
        titleRow.add("Date");

        List<String> currencies = getCurrenciesSymbolsAsSortedList(historyTable);

        for (String currency : currencies) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(inputCurrency);
            stringBuilder.append("/");
            stringBuilder.append(currency);
            titleRow.add(stringBuilder.toString());
        }

        return titleRow;

    }

    private List<List<String>> createExchangeRateTable(HistoryTable historyTable) {

        List<List<String>> exchangeRateTable = new ArrayList<>();
        exchangeRateTable.add(createTitleRowForExchangeRateTable(historyTable));

        List<String> dates = getDatesAsSortedList(historyTable);

        for (String date : dates) {
            TreeMap<String, Double> ratesForDate = historyTable.getRates().get(date);
            exchangeRateTable.add(getRatesAsList(date, ratesForDate));
        }
        return exchangeRateTable;
    }

    private List<String> getRatesAsList(String date, TreeMap<String, Double> rates) {

        List<String> tableLine = new ArrayList<>();
        tableLine.add(date);

        List<String> currenciesList = new ArrayList<>(rates.keySet());

        for (String currency : currenciesList) {
            tableLine.add(String.valueOf(rates.get(currency)));
        }

        return tableLine;

    }

    private List<String> getCurrenciesSymbolsAsSortedList(HistoryTable historyTable) {

        TreeMap<String, Double> rates = historyTable.getRates().get(historyTable.getRates().firstKey());
        List<String> currencies = new ArrayList<>(rates.keySet());
        Collections.sort(currencies);
        return currencies;

    }

    private List<String> getDatesAsSortedList(HistoryTable historyTable) {

        List<String> dates = new ArrayList<>(historyTable.getRates().keySet());
        Collections.sort(dates);
        return dates;

    }

    private void printExchangeRateTable(HistoryTable historyTable) {

        TreeMap<String, TreeMap<String, Double>> outerHashMap = historyTable.getRates();
        outerHashMap.forEach((key, value) -> printDateAndRate(key, value));

    }

    private void printDateAndRate(String date, TreeMap<String, Double> innerHashMap) {
        System.out.print(date + "\t\t");
        innerHashMap.forEach((key, value) -> printRate(key, value));
        System.out.println();
    }

    private void printRate(String outputCurrency, Double rate) {

        System.out.print(baseAmount + " ");
        System.out.print(inputCurrency + " = ");
        System.out.print(baseAmount * rate + " ");
        System.out.print(outputCurrency + "\t\t");

    }
    
}
