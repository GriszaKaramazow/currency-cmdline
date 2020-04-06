package pl.connectis.dto;

import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;

import java.util.Map;

public class ExchangeRatesMapper {

    public ExchangeRates mapHistoryRatesDTO(HistoryRatesDTO historyRatesDTO) {

        ExchangeRates exchangeRates = new ExchangeRates();

        String baseCurrency = historyRatesDTO.getBaseCurrency();
        Map<String, Map<String, Double>> rates = historyRatesDTO.getRates();

        for (String rateDate : rates.keySet()) {

            exchangeRates = mapSingleDayRatesDTO(exchangeRates,
                    new SingleDayRatesDTO(baseCurrency, rateDate, rates.get(rateDate)));

        }

        return exchangeRates;

    }

    public ExchangeRates mapSingleDayRatesDTO(ExchangeRates exchangeRates, SingleDayRatesDTO singleDayRatesDTO) {

        String baseCurrency = singleDayRatesDTO.getBaseCurrency();
        String rateDate = singleDayRatesDTO.getRateDate();
        Map<String, Double> rates = singleDayRatesDTO.getRates();

        for (String quoteCurrency : rates.keySet()) {

            exchangeRates.addSingleRate(new SingleRate(baseCurrency, quoteCurrency, rateDate, rates.get(quoteCurrency)));

        }

        return exchangeRates;

    }

    public ExchangeRates mapSingleDayRatesDTO(SingleDayRatesDTO singleDayRatesDTO) {

        return mapSingleDayRatesDTO(new ExchangeRates(), singleDayRatesDTO);

    }

}
