package command;

import lombok.Getter;
import lombok.SneakyThrows;
import model.CurrencySymbol;
import model.HistoryTable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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


    private void printExchangeRateTable(HistoryTable historyTable) {
        HashMap<String, HashMap<String, Double>> outerHashMap = historyTable.getRates();
        outerHashMap.forEach((key, value) -> printDateAndRate(key, value));

    }

    private void printDateAndRate(String date, HashMap<String, Double> innerHashMap) {
        System.out.print(date + "\t\t");
        innerHashMap.forEach((key, value) -> printRate(key, value));
        System.out.println();
    }

    private void printRate(String outputCurrency, Double rate) {

        System.out.print(baseAmount + " ");
        System.out.print(inputCurrency + " = ");
        System.out.print(baseAmount *rate + " ");
        System.out.print(outputCurrency + "\t\t");

    }





}
