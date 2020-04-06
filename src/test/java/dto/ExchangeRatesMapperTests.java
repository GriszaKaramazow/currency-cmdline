package dto;

import org.junit.jupiter.api.Test;
import pl.connectis.dto.ExchangeRatesMapper;
import pl.connectis.dto.SingleDayRatesDTO;
import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExchangeRatesMapperTests {

    private final ExchangeRatesMapper exchangeRatesMapper = new ExchangeRatesMapper();

    @Test
    public void testSingleCurrencyMapSingleDatesDTO() {

        // given
        String baseCurrency = "EUR";
        String quoteCurrency = "PLN";
        String rateDate = "2020-02-28";
        Double rateValue = 4.3259;

        Map<String, Double> rate = new HashMap<>();
        rate.put(quoteCurrency, rateValue);
        SingleDayRatesDTO testSubject = new SingleDayRatesDTO(baseCurrency, rateDate, rate);

        // when
        ExchangeRates exchangeRatesResult = exchangeRatesMapper.mapSingleDatesDTO(testSubject);

        // then
        SingleRate singleRate = new SingleRate(baseCurrency, quoteCurrency, rateDate, rateValue);
        ExchangeRates exchangeRatesExpected = new ExchangeRates();
        exchangeRatesExpected.addSingleRate(singleRate);
        assertTrue(exchangeRatesExpected.equals(exchangeRatesResult));

    }

    @Test
    public void testMultipleCurrenciesMapSingleDatesDTO () {

        // given
        String baseCurrency = "RUB";
        String quoteCurrencyGBP = "GBP";
        String quoteCurrencyEUR = "EUR";
        String quoteCurrencyUSD = "USD";
        String rateDate = "2020-03-13";
        Double rateValueGBP = 0.0110319117;
        Double rateValueEUR = 0.0123856648;
        Double rateValueUSD = 0.0137530422;

        Map<String, Double> rates = new HashMap<>();
        rates.put(quoteCurrencyGBP, rateValueGBP);
        rates.put(quoteCurrencyEUR, rateValueEUR);
        rates.put(quoteCurrencyUSD, rateValueUSD);
        SingleDayRatesDTO testSubject = new SingleDayRatesDTO(baseCurrency, rateDate, rates);

        // when
        ExchangeRates exchangeRatesResult = exchangeRatesMapper.mapSingleDatesDTO(testSubject);

        // then
        ExchangeRates exchangeRatesExpected = new ExchangeRates();
        exchangeRatesExpected.addSingleRate(new SingleRate(baseCurrency, quoteCurrencyEUR, rateDate, rateValueEUR));
        exchangeRatesExpected.addSingleRate(new SingleRate(baseCurrency, quoteCurrencyGBP, rateDate, rateValueGBP));
        exchangeRatesExpected.addSingleRate(new SingleRate(baseCurrency, quoteCurrencyUSD, rateDate, rateValueUSD));
        assertTrue(exchangeRatesExpected.equals(exchangeRatesResult));

    }

}
