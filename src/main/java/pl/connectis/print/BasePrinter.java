package pl.connectis.print;

import lombok.extern.slf4j.Slf4j;
import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
abstract class BasePrinter implements Printer {

    List<List<String>> preparePrintContent(ExchangeRates exchangeRates) {

        List<List<String>> fileContent = new ArrayList<>();

        String baseCurrency = exchangeRates.getHistoryRates().get(0).getBaseCurrency();
        List<String> quoteCurrencies = getQuoteCurrencies(exchangeRates);
        List<String> rateDates = getRateDates(exchangeRates);

        fileContent.add(createTitleRowOfFileContent(baseCurrency, quoteCurrencies));

        for (String rateDate : rateDates) {

            List<String> currentRow = new ArrayList<>();
            currentRow.add(rateDate);

            for (String quoteCurrency : quoteCurrencies) {

                for (SingleRate singleRate : exchangeRates.getHistoryRates()) {

                    if (singleRate.getRateDate().equals(rateDate) &&
                            singleRate.getQuoteCurrency().equals(quoteCurrency)) {
                        currentRow.add(String.valueOf(singleRate.getRateValue()));
                    }

                }

            }

            fileContent.add(currentRow);

        }

        return fileContent;

    }

    private List<String> getQuoteCurrencies(ExchangeRates exchangeRates) {

        List<String> quoteCurrencies = new ArrayList<>();

        for (SingleRate singleRate : exchangeRates.getHistoryRates()) {

            if (!quoteCurrencies.contains(singleRate.getQuoteCurrency())) {
                quoteCurrencies.add(singleRate.getQuoteCurrency());
            }

        }

        Collections.sort(quoteCurrencies);

        return quoteCurrencies;

    }

    private List<String> getRateDates(ExchangeRates exchangeRates) {

        List<String> rateDates = new ArrayList<>();

        for (SingleRate singleRate : exchangeRates.getHistoryRates()) {

            if (!rateDates.contains(singleRate.getRateDate())) {
                rateDates.add(singleRate.getRateDate());
            }

        }

        Collections.sort(rateDates);

        return rateDates;

    }

    private List<String> createTitleRowOfFileContent(String baseCurrency, List<String> quoteCurrencies) {

        List<String> titleRow = new ArrayList<>();
        titleRow.add("Rate date");

        for (String quoteCurrency : quoteCurrencies) {

            titleRow.add(baseCurrency + "/" + quoteCurrency);

        }

        return titleRow;

    }

}
