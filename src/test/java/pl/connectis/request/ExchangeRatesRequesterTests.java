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
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Map<String, Double> rate = new HashMap<>();
        rate.put("CHF", 1.1785104732);
        SingleDayRatesDTO singleDayRatesDTOExpected = new SingleDayRatesDTO("USD", "2004-11-11", rate);

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
        Map<String, Double> rate = new HashMap<>();
        rate.put( "DKK", 10.0106013232);
        rate.put("NOK", 12.9913176505);
        rate.put("SEK", 12.4317288208);
        SingleDayRatesDTO singleDayRatesDTOExpected = new SingleDayRatesDTO("GBP", "2016-01-08", rate);
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
        Map<String, Map<String, Double>> rates = new HashMap<>();

        Map<String, Double> rateDayOne = new HashMap<>();
        rateDayOne.put("INR", 4.297592547);
        rates.put("2016-05-25", rateDayOne);

        Map<String, Double> rateDayTwo = new HashMap<>();
        rateDayTwo.put("INR", 4.3014095829);
        rates.put("2016-05-26", rateDayTwo);

        Map<String, Double> rateDayThree = new HashMap<>();
        rateDayThree.put("INR", 4.2902741484);
        rates.put("2016-05-27", rateDayThree);


        HistoryRatesDTO historyRatesDTOExpected = new HistoryRatesDTO("ZAR", rates);

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
        Map<String, Map<String, Double>> rates = new HashMap<>();

        Map<String, Double> rateDayOne = new HashMap<>();
        rateDayOne.put("CZK", 5.8428577934);
        rateDayOne.put("RON", 1.0841491735);
        rateDayOne.put("TRY", 1.4962433405);
        rates.put("2019-04-16", rateDayOne);

        Map<String, Double> rateDayTwo = new HashMap<>();
        rateDayTwo.put("CZK", 5.8250465423);
        rateDayTwo.put("RON", 1.0812559597);
        rateDayTwo.put("TRY", 1.474980702);
        rates.put("2019-04-17", rateDayTwo);

        Map<String, Double> rateDayThree = new HashMap<>();
        rateDayThree.put("CZK", 5.8096186038);
        rateDayThree.put("RON", 1.0771840927);
        rateDayThree.put("TRY", 1.4813826177);
        rates.put("2019-04-18", rateDayThree);

        HistoryRatesDTO historyRatesDTOExpected = new HistoryRatesDTO("BRL", rates);

        assertEquals(historyRatesDTOExpected, historyRatesDTOResult);

    }

}
