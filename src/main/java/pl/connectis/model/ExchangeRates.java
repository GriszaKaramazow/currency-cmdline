package pl.connectis.model;

import lombok.Getter;
import pl.connectis.dto.HistoryRatesDTO;
import pl.connectis.dto.SingleDayRatesDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class ExchangeRates {

    private List<SingleRate> historyRates = new ArrayList<>();

    public ExchangeRates(HistoryRatesDTO historyRatesDTO) {

        createHistoryRateFromHistoryRateDTO(historyRatesDTO);

    }

    public ExchangeRates(SingleDayRatesDTO singleDayRatesDTO) {

        createHistoryRatesFromSingleDayRatesDTO(singleDayRatesDTO);

    }

    private void createHistoryRateFromHistoryRateDTO(HistoryRatesDTO historyRatesDTO) {

        String baseCurrency = historyRatesDTO.getBaseCurrency();
        Map<String, Map<String, Double>> rates = historyRatesDTO.getRates();

        for (String rateDate : rates.keySet()) {
            createHistoryRatesFromSingleDayRatesDTO(new SingleDayRatesDTO(baseCurrency, rateDate, rates.get(rateDate)));
        }

    }

    private void createHistoryRatesFromSingleDayRatesDTO(SingleDayRatesDTO singleDayRatesDTO) {

        String baseCurrency = singleDayRatesDTO.getBaseCurrency();
        String rateDate = singleDayRatesDTO.getRateDate();
        Map<String, Double> rates = singleDayRatesDTO.getRates();

        for (String quoteCurrency : rates.keySet()) {
            historyRates.add(new SingleRate(baseCurrency, quoteCurrency, rateDate, rates.get(quoteCurrency)));
        }

    }

}
