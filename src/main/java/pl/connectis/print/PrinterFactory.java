package pl.connectis.print;

import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrinterFactory {

    private final ExchangeRates exchangeRates;
    private List<List<String>> fileContent;

    public PrinterFactory(ExchangeRates exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public ConsolePrinter newConsolePrinter() {

        return new ConsolePrinter(fileContent);

    }

    public TxtPrinterImpl newTxtPrinter(String filePath) {

        return new TxtPrinterImpl(filePath, fileContent);

    }

    public CsvPrinterImpl newCsvPrinter(String filePath) {

        return new CsvPrinterImpl(filePath, fileContent);

    }

    private void prepareFileContent() {

        String baseCurrency = exchangeRates.getHistoryRates().get(0).getBaseCurrency();
        List<String> quoteCurrencies = new ArrayList<>();
        List<String> rateDates = new ArrayList<>();

        for (SingleRate singleRate : exchangeRates.getHistoryRates()) {

            if (!quoteCurrencies.contains(singleRate.getQuoteCurrency())) {
                quoteCurrencies.add(singleRate.getQuoteCurrency());
            }

            if (!rateDates.contains(singleRate.getRateDate())) {
                rateDates.add(singleRate.getRateDate());
            }

        }

        Collections.sort(quoteCurrencies);
        Collections.sort(rateDates);

        addTitleRowToFileContent(baseCurrency, quoteCurrencies);

        for (String rateDate : rateDates) {

            List<String> currentRow = new ArrayList<>();
            currentRow.add(rateDate);

            for (String quoteCurrency : quoteCurrencies) {

                for (SingleRate singleRate : exchangeRates.getHistoryRates()) {

                    if (singleRate.getRateDate() == rateDate && singleRate.getQuoteCurrency() == quoteCurrency) {
                        currentRow.add(String.valueOf(singleRate.getRateValue()));
                    }

                }

            }

            fileContent.add(currentRow);
        }

    }

    private void addTitleRowToFileContent(String baseCurrency, List<String> quoteCurrencies) {

        List<String> titleRow = new ArrayList<>();
        titleRow.add("Date");

        for (String quoteCurrency : quoteCurrencies) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(baseCurrency);
            stringBuilder.append("/");
            stringBuilder.append(quoteCurrency);
            titleRow.add(stringBuilder.toString());
        }

        fileContent.add(titleRow);

    }

}
