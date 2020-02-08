import model.CurrencySymbol;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ApiRequestCreator {

    private final StringBuilder stringBuilder = new StringBuilder("https://api.ratesapi.io/api/");

    public ApiRequestCreator() {
    }

    private void setDate(LocalDate date) {

        if (date != null) {
            stringBuilder.append(date);
        }

    }

    private void setHistory(LocalDate startDate, LocalDate endDate) {

        if (startDate != null && endDate != null) {
            stringBuilder.append("");

        }

    }

    private void setInputCurrency(CurrencySymbol inputCurrency) {

        if (inputCurrency == null) {

        }

    }

    private void setOutputCurrency(CurrencySymbol outputCurrency) {

        if (outputCurrency == null) {
            stringBuilder.append("?symbols=");
            stringBuilder.append(outputCurrency.toString());
        }

    }

    private void setOutputCurrency(List<CurrencySymbol> outputCurrencies) {

        if (outputCurrencies != null) {
            stringBuilder.append("?symbols=");
            String symbols = outputCurrencies.stream()
                    .map(currencySymbol -> currencySymbol.toString())
                    .collect(Collectors.joining(","));
            stringBuilder.append(symbols);
        }

    }

    private String getRequest(LocalDate date,
                              CurrencySymbol inputCurrency,
                              CurrencySymbol outputCurrency) {

        setDate(date);
        setInputCurrency(inputCurrency);
        setOutputCurrency(outputCurrency);
        return stringBuilder.toString();

    }

    private String getRequest(LocalDate date,
                              CurrencySymbol inputCurrency,
                              List<CurrencySymbol> outputCurrencies) {

        setDate(date);
        setInputCurrency(inputCurrency);
        setOutputCurrency(outputCurrencies);
        return stringBuilder.toString();

    }

    private String getRequest(LocalDate startDate,
                              LocalDate endDate,
                              CurrencySymbol inputCurrency,
                              CurrencySymbol outputCurrency) {

        setHistory(startDate, endDate);
        setInputCurrency(inputCurrency);
        setOutputCurrency(outputCurrency);
        return stringBuilder.toString();

    }

    private String getRequest(LocalDate startDate, LocalDate endDate, CurrencySymbol inputCurrency, List<CurrencySymbol> outputCurrencies) {
        setHistory(startDate, endDate);
        setInputCurrency(inputCurrency);
        setOutputCurrency(outputCurrencies);
        return stringBuilder.toString();
    }

}
