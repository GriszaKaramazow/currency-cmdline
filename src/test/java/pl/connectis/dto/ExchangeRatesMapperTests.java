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
        String baseCurrency = "EUR";
        String quoteCurrency = "PLN";
        String rateDate = "2020-02-28";
        Double rateValue = 4.3259;

        Map<String, Double> rate = new HashMap<>();
        rate.put(quoteCurrency, rateValue);
        SingleDayRatesDTO testSubject = new SingleDayRatesDTO(baseCurrency, rateDate, rate);

        // when
        ExchangeRates exchangeRatesResult = exchangeRatesMapper.mapSingleDayRatesDTO(testSubject);

        // then
        SingleRate singleRate = new SingleRate(baseCurrency, quoteCurrency, rateDate, rateValue);
        ExchangeRates exchangeRatesExpected = new ExchangeRates();
        exchangeRatesExpected.addSingleRate(singleRate);

        assertTrue(compareExchangeRates(exchangeRatesExpected, exchangeRatesResult));

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
        ExchangeRates exchangeRatesResult = exchangeRatesMapper.mapSingleDayRatesDTO(testSubject);

        // then
        ExchangeRates exchangeRatesExpected = new ExchangeRates();
        exchangeRatesExpected.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyEUR, rateDate, rateValueEUR));
        exchangeRatesExpected.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyGBP, rateDate, rateValueGBP));
        exchangeRatesExpected.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyUSD, rateDate, rateValueUSD));

        assertTrue(compareExchangeRates(exchangeRatesExpected, exchangeRatesResult));

    }

    @Test
    public void testSingleCurrencyMapHistoryRatesDTO () {

        // given
        String baseCurrency = "CAD";
        String quoteCurrency = "HUF";
        String rateDateDayOne = "2019-06-03";
        Double rateValueDayOne = 214.4191283614;
        String rateDateDayTwo = "2019-06-04";
        Double rateValueDayTwo = 213.0898021309;
        String rateDateDayThree = "2019-06-05";
        Double rateValueDayThree = 213.3443928334;

        Map<String, Map<String, Double>> rates = new HashMap<>();

        Map<String, Double> rateDayOne = new HashMap<>();
        rateDayOne.put(quoteCurrency, rateValueDayOne);
        rates.put(rateDateDayOne, rateDayOne);

        Map<String, Double> rateDayTwo = new HashMap<>();
        rateDayTwo.put(quoteCurrency, rateValueDayTwo);
        rates.put(rateDateDayTwo, rateDayTwo);

        Map<String, Double> rateDayThree = new HashMap<>();
        rateDayThree.put(quoteCurrency, rateValueDayThree);
        rates.put(rateDateDayThree, rateDayThree);

        HistoryRatesDTO testSubject = new HistoryRatesDTO(baseCurrency, rates);

        // when
        ExchangeRates exchangeRatesResult = exchangeRatesMapper.mapHistoryRatesDTO(testSubject);

        // then
        ExchangeRates exchangeRatesExpected = new ExchangeRates();
        exchangeRatesExpected.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrency, rateDateDayOne, rateValueDayOne));
        exchangeRatesExpected.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrency, rateDateDayTwo, rateValueDayTwo));
        exchangeRatesExpected.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrency, rateDateDayThree, rateValueDayThree));

        assertTrue(compareExchangeRates(exchangeRatesExpected, exchangeRatesResult));

    }

    @Test
    public void testMultipleCurrenciesMapHistoryRatesDTO() {

        // given
        String baseCurrency = "NZD";
        String rateDateDayOne = "2019-12-11";
        String rateDateDayTwo = "2019-12-12";
        String rateDateDayThree = "2019-12-13";
        String quoteCurrencyCNY = "CNY";
        Double rateValueDayOneCNY = 4.6064760104;
        Double rateValueDayTwoCNY = 4.6358689223;
        Double rateValueDayThreeCNY = 4.6168434777;
        String quoteCurrencyPHP = "PHP";
        Double rateValueDayOnePHP = 33.253958875;
        Double rateValueDayTwoPHP = 33.4058913995;
        Double rateValueDayThreePHP = 33.4504830202;
        String quoteCurrencyTHB = "THB";
        Double rateValueDayOneTHB = 19.8050106358;
        Double rateValueDayTwoTHB = 19.8781497693;
        Double rateValueDayThreeTHB = 19.9899247318;

        Map<String, Map<String, Double>> rates = new HashMap<>();

        Map<String, Double> rateDayOne = new HashMap<>();
        rateDayOne.put(quoteCurrencyCNY, rateValueDayOneCNY);
        rateDayOne.put(quoteCurrencyPHP, rateValueDayOnePHP);
        rateDayOne.put(quoteCurrencyTHB, rateValueDayOneTHB);
        rates.put(rateDateDayOne, rateDayOne);

        Map<String, Double> rateDayTwo = new HashMap<>();
        rateDayTwo.put(quoteCurrencyCNY, rateValueDayTwoCNY);
        rateDayTwo.put(quoteCurrencyPHP, rateValueDayTwoPHP);
        rateDayTwo.put(quoteCurrencyTHB, rateValueDayTwoTHB);
        rates.put(rateDateDayTwo, rateDayTwo);

        Map<String, Double> rateDayThree = new HashMap<>();
        rateDayThree.put(quoteCurrencyCNY, rateValueDayThreeCNY);
        rateDayThree.put(quoteCurrencyPHP, rateValueDayThreePHP);
        rateDayThree.put(quoteCurrencyTHB, rateValueDayThreeTHB);
        rates.put(rateDateDayThree, rateDayThree);

        HistoryRatesDTO testSubject = new HistoryRatesDTO(baseCurrency, rates);

        // when
        ExchangeRates exchangeRatesResult = exchangeRatesMapper.mapHistoryRatesDTO(testSubject);

        // then
        ExchangeRates exchangeRatesExpected = new ExchangeRates();
        exchangeRatesExpected.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyCNY, rateDateDayOne, rateValueDayOneCNY));
        exchangeRatesExpected.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyPHP, rateDateDayOne, rateValueDayOnePHP));
        exchangeRatesExpected.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyTHB, rateDateDayOne, rateValueDayOneTHB));
        exchangeRatesExpected.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyCNY, rateDateDayTwo, rateValueDayTwoCNY));
        exchangeRatesExpected.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyPHP, rateDateDayTwo, rateValueDayTwoPHP));
        exchangeRatesExpected.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyTHB, rateDateDayTwo, rateValueDayTwoTHB));
        exchangeRatesExpected.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyCNY, rateDateDayThree, rateValueDayThreeCNY));
        exchangeRatesExpected.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyPHP, rateDateDayThree, rateValueDayThreePHP));
        exchangeRatesExpected.addSingleRate(
                new SingleRate(baseCurrency, quoteCurrencyTHB, rateDateDayThree, rateValueDayThreeTHB));

        assertTrue(compareExchangeRates(exchangeRatesExpected, exchangeRatesResult));

    }

    private boolean compareExchangeRates(ExchangeRates exchangeRatesA, ExchangeRates exchangeRatesB) {

        List<SingleRate> singleRatesListA = exchangeRatesA.getHistoryRates();
        List<SingleRate> singleRatesListB = exchangeRatesB.getHistoryRates();
        return singleRatesListA.containsAll(singleRatesListB) && singleRatesListB.containsAll(singleRatesListA);

    }

}
