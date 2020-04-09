package pl.connectis.dto;

import org.junit.jupiter.api.Test;
import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExchangeRatesMapperTests {

    private final ExchangeRatesMapper exchangeRatesMapper = new ExchangeRatesMapper();

    @Test
    public void testSingleCurrencyMapSingleDatesDTO() {

        // given
        Map<String, Double> rate = new HashMap<>();
        rate.put("PLN", 4.3259);
        SingleDayRatesDTO testSubject = new SingleDayRatesDTO("EUR", "2020-02-28", rate);

        // when
        ExchangeRates exchangeRatesResult = exchangeRatesMapper.mapSingleDayRatesDTO(testSubject);

        // then
        SingleRate singleRate = new SingleRate("EUR", "PLN", "2020-02-28", 4.3259);
        ExchangeRates exchangeRatesExpected = new ExchangeRates();
        exchangeRatesExpected.addSingleRate(singleRate);

        assertTrue(compareExchangeRates(exchangeRatesExpected, exchangeRatesResult));

    }

    @Test
    public void testMultipleCurrenciesMapSingleDatesDTO() {

        // given
        Map<String, Double> rates = new HashMap<>();
        rates.put("EUR", 0.0123856648);
        rates.put("GBP", 0.0110319117);
        rates.put("USD", 0.0137530422);
        SingleDayRatesDTO testSubject = new SingleDayRatesDTO("RUB", "2020-03-13", rates);

        // when
        ExchangeRates exchangeRatesResult = exchangeRatesMapper.mapSingleDayRatesDTO(testSubject);

        // then
        ExchangeRates exchangeRatesExpected = new ExchangeRates();
        exchangeRatesExpected.addSingleRate(
                new SingleRate("RUB", "EUR", "2020-03-13", 0.0123856648));
        exchangeRatesExpected.addSingleRate(
                new SingleRate("RUB", "GBP", "2020-03-13", 0.0110319117));
        exchangeRatesExpected.addSingleRate(
                new SingleRate("RUB", "USD", "2020-03-13", 0.0137530422));

        assertTrue(compareExchangeRates(exchangeRatesExpected, exchangeRatesResult));

    }

    @Test
    public void testSingleCurrencyMapHistoryRatesDTO() {

        // given
        Map<String, Map<String, Double>> rates = new HashMap<>();

        Map<String, Double> rateDayOne = new HashMap<>();
        rateDayOne.put("HUF", 214.4191283614);
        rates.put("2019-06-03", rateDayOne);

        Map<String, Double> rateDayTwo = new HashMap<>();
        rateDayTwo.put("HUF", 213.0898021309);
        rates.put("2019-06-04", rateDayTwo);

        Map<String, Double> rateDayThree = new HashMap<>();
        rateDayThree.put("HUF", 213.3443928334);
        rates.put("2019-06-05", rateDayThree);

        HistoryRatesDTO testSubject = new HistoryRatesDTO("CAD", rates);

        // when
        ExchangeRates exchangeRatesResult = exchangeRatesMapper.mapHistoryRatesDTO(testSubject);

        // then
        ExchangeRates exchangeRatesExpected = new ExchangeRates();
        exchangeRatesExpected.addSingleRate(
                new SingleRate("CAD", "HUF", "2019-06-03", 214.4191283614));
        exchangeRatesExpected.addSingleRate(
                new SingleRate("CAD", "HUF", "2019-06-04", 213.0898021309));
        exchangeRatesExpected.addSingleRate(
                new SingleRate("CAD", "HUF", "2019-06-05", 213.3443928334));

        assertTrue(compareExchangeRates(exchangeRatesExpected, exchangeRatesResult));

    }

    @Test
    public void testMultipleCurrenciesMapHistoryRatesDTO() {

        // given
        Map<String, Map<String, Double>> rates = new HashMap<>();

        Map<String, Double> rateDayOne = new HashMap<>();
        rateDayOne.put("CNY", 4.6064760104);
        rateDayOne.put("PHP", 33.253958875);
        rateDayOne.put("THB", 19.8050106358);
        rates.put("2019-12-11", rateDayOne);

        Map<String, Double> rateDayTwo = new HashMap<>();
        rateDayTwo.put("CNY", 4.6358689223);
        rateDayTwo.put("PHP", 33.4058913995);
        rateDayTwo.put("THB", 19.8781497693);
        rates.put("2019-12-12", rateDayTwo);

        Map<String, Double> rateDayThree = new HashMap<>();
        rateDayThree.put("CNY", 4.6168434777);
        rateDayThree.put("PHP", 33.4504830202);
        rateDayThree.put("THB", 19.9899247318);
        rates.put("2019-12-13", rateDayThree);

        HistoryRatesDTO testSubject = new HistoryRatesDTO("NZD", rates);

        // when
        ExchangeRates exchangeRatesResult = exchangeRatesMapper.mapHistoryRatesDTO(testSubject);

        // then
        ExchangeRates exchangeRatesExpected = new ExchangeRates();
        exchangeRatesExpected.addSingleRate(
                new SingleRate("NZD", "CNY", "2019-12-11", 4.6064760104));
        exchangeRatesExpected.addSingleRate(
                new SingleRate("NZD", "PHP", "2019-12-11", 33.253958875));
        exchangeRatesExpected.addSingleRate(
                new SingleRate("NZD", "THB", "2019-12-11", 19.8050106358));
        exchangeRatesExpected.addSingleRate(
                new SingleRate("NZD", "CNY", "2019-12-12", 4.6358689223));
        exchangeRatesExpected.addSingleRate(
                new SingleRate("NZD", "PHP", "2019-12-12", 33.4058913995));
        exchangeRatesExpected.addSingleRate(
                new SingleRate("NZD", "THB", "2019-12-12", 19.8781497693));
        exchangeRatesExpected.addSingleRate(
                new SingleRate("NZD", "CNY", "2019-12-13", 4.6168434777));
        exchangeRatesExpected.addSingleRate(
                new SingleRate("NZD", "PHP", "2019-12-13", 33.4504830202));
        exchangeRatesExpected.addSingleRate(
                new SingleRate("NZD", "THB", "2019-12-13", 19.9899247318));

        assertTrue(compareExchangeRates(exchangeRatesExpected, exchangeRatesResult));

    }

    private boolean compareExchangeRates(ExchangeRates exchangeRatesA, ExchangeRates exchangeRatesB) {

        List<SingleRate> singleRatesListA = exchangeRatesA.getHistoryRates();
        List<SingleRate> singleRatesListB = exchangeRatesB.getHistoryRates();
        return singleRatesListA.containsAll(singleRatesListB) && singleRatesListB.containsAll(singleRatesListA);

    }

}
