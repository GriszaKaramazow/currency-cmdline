package pl.connectis.request;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.api.client.http.GenericUrl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import pl.connectis.dto.HistoryRatesDTO;
import pl.connectis.dto.SingleDayRatesDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(Lifecycle.PER_CLASS)
public class ExchangeRatesRequesterTests {

    private final WireMockServer mockedServer = new WireMockServer();

    @BeforeAll
    public void startServer() {

        mockedServer.start();

    }

    @AfterAll
    public void stopServer() {

        mockedServer.stop();

    }

    @Test
    public void testsSingleCurrencyGetSingleDayRates() throws IOException {

        // given
        String testUrl = "/2004-11-11?base=USD&symbols=CHF";

        stubFor(get(urlEqualTo(testUrl))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("testsSingleCurrencyGetSingleDayRatesResponseBody.json")));

        GenericUrl genericUrl = new GenericUrl("http://localhost:8080" + testUrl);
        ExchangeRatesRequester exchangeRatesRequester = new ExchangeRatesRequester(genericUrl);

        // when
        SingleDayRatesDTO singleDayRatesDTOResult = exchangeRatesRequester.getSingleDayRates();

        // then
        String baseCurrency = "USD";
        String quoteCurrency = "CHF";
        String rateDate = "2004-11-11";
        Double rateValue = 1.1785104732;

        Map<String, Double> rate = new HashMap<>();
        rate.put(quoteCurrency, rateValue);
        SingleDayRatesDTO singleDayRatesDTOExpected = new SingleDayRatesDTO(baseCurrency, rateDate, rate);

        assertTrue(singleDayRatesDTOExpected.equals(singleDayRatesDTOResult));

    }

    @Test
    public void testsMultipleCurrenciesGetSingleDayRates() throws IOException {

        // given
        String testUrl = "/2016-01-10?base=GBP&symbols=DKK,NOK,SEK";

        stubFor(get(urlEqualTo(testUrl))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("testsMultipleCurrenciesGetSingleDayRatesResponseBody.json")));

        // when
        GenericUrl genericUrl = new GenericUrl("http://localhost:8080" + testUrl);
        ExchangeRatesRequester exchangeRatesRequester = new ExchangeRatesRequester(genericUrl);
        SingleDayRatesDTO singleDayRatesDTOResult = exchangeRatesRequester.getSingleDayRates();

        // then
        String baseCurrency = "GBP";
        String quoteCurrencyDKK = "DKK";
        String quoteCurrencyNOK = "NOK";
        String quoteCurrencySEK = "SEK";
        String rateDate = "2016-01-08";
        Double rateValueDKK = 10.0106013232;
        Double rateValueNOK = 12.9913176505;
        Double rateValueSEK = 12.4317288208;

        Map<String, Double> rate = new HashMap<>();
        rate.put(quoteCurrencyDKK, rateValueDKK);
        rate.put(quoteCurrencyNOK, rateValueNOK);
        rate.put(quoteCurrencySEK, rateValueSEK);
        SingleDayRatesDTO singleDayRatesDTOExpected = new SingleDayRatesDTO(baseCurrency, rateDate, rate);
        assertTrue(singleDayRatesDTOExpected.equals(singleDayRatesDTOResult));

    }

    @Test
    public void testsSingleCurrencyGetHistoryRates() throws IOException {

        // given
        String testUrl = "/history?start_at=2016-05-25&end_at=2016-05-27&base=ZAR&symbols=INR";

        stubFor(get(urlEqualTo(testUrl))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("testsSingleCurrencyGetHistoryRatesResponseBody.json")));

        // when
        GenericUrl genericUrl = new GenericUrl("http://localhost:8080" + testUrl);
        ExchangeRatesRequester exchangeRatesRequester = new ExchangeRatesRequester(genericUrl);
        HistoryRatesDTO historyRatesDTOResult = exchangeRatesRequester.getHistoryRates();

        // then
        String baseCurrency = "ZAR";
        String quoteCurrency = "INR";
        String rateDateDayOne = "2016-05-25";
        Double rateValueDayOne = 4.297592547;
        String rateDateDayTwo = "2016-05-26";
        Double rateValueDayTwo = 4.3014095829;
        String rateDateDayThree = "2016-05-27";
        Double rateValueDayThree = 4.2902741484;

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


        HistoryRatesDTO historyRatesDTOExpected = new HistoryRatesDTO(baseCurrency, rates);

        assertTrue(historyRatesDTOExpected.equals(historyRatesDTOResult));

    }

    @Test
    public void testsMultipleCurrenciesGetHistoryRates() throws IOException {

        // given
        String testUrl = "/history?start_at=2019-04-16&end_at=2019-04-20&base=BRL&symbols=TRY,CZK,RON";

        stubFor(get(urlEqualTo(testUrl))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("testsMultipleCurrenciesGetHistoryRatesResponseBody.json")));

        // when
        GenericUrl genericUrl = new GenericUrl("http://localhost:8080" + testUrl);
        ExchangeRatesRequester exchangeRatesRequester = new ExchangeRatesRequester(genericUrl);
        HistoryRatesDTO historyRatesDTOResult = exchangeRatesRequester.getHistoryRates();

        // then
        String baseCurrency = "BRL";
        String rateDateDayOne = "2019-04-16";
        String rateDateDayTwo = "2019-04-17";
        String rateDateDayThree = "2019-04-18";
        String quoteCurrencyCZK = "CZK";
        Double rateValueDayOneCZK = 5.8428577934;
        Double rateValueDayTwoCZK = 5.8250465423;
        Double rateValueDayThreeCZK = 5.8096186038;
        String quoteCurrencyRON = "RON";
        Double rateValueDayOneRON = 1.0841491735;
        Double rateValueDayTwoRON = 1.0812559597;
        Double rateValueDayThreeRON = 1.0771840927;
        String quoteCurrencyTRY = "TRY";
        Double rateValueDayOneTRY = 1.4962433405;
        Double rateValueDayTwoTRY = 1.474980702;
        Double rateValueDayThreeTRY = 1.4813826177;

        Map<String, Map<String, Double>> rates = new HashMap<>();

        Map<String, Double> rateDayOne = new HashMap<>();
        rateDayOne.put(quoteCurrencyCZK, rateValueDayOneCZK);
        rateDayOne.put(quoteCurrencyRON, rateValueDayOneRON);
        rateDayOne.put(quoteCurrencyTRY, rateValueDayOneTRY);
        rates.put(rateDateDayOne, rateDayOne);

        Map<String, Double> rateDayTwo = new HashMap<>();
        rateDayTwo.put(quoteCurrencyCZK, rateValueDayTwoCZK);
        rateDayTwo.put(quoteCurrencyRON, rateValueDayTwoRON);
        rateDayTwo.put(quoteCurrencyTRY, rateValueDayTwoTRY);
        rates.put(rateDateDayTwo, rateDayTwo);

        Map<String, Double> rateDayThree = new HashMap<>();
        rateDayThree.put(quoteCurrencyCZK, rateValueDayThreeCZK);
        rateDayThree.put(quoteCurrencyRON, rateValueDayThreeRON);
        rateDayThree.put(quoteCurrencyTRY, rateValueDayThreeTRY);
        rates.put(rateDateDayThree, rateDayThree);

        HistoryRatesDTO historyRatesDTOExpected = new HistoryRatesDTO(baseCurrency, rates);

        assertTrue(historyRatesDTOExpected.equals(historyRatesDTOResult));

    }

}
