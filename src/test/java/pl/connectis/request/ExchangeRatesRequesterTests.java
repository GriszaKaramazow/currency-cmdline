package pl.connectis.request;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.api.client.http.GenericUrl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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
        String baseCurrency = "USD";
        String quoteCurrency = "CHF";
        String rateDate = "2004-11-11";
        Double rateValue = 1.1785104732;

        String responseBody =
                "{" +
                        "\"base\":\"" + baseCurrency + "\"," +
                        "\"rates\":{" +
                            "\"" + quoteCurrency + "\":" + rateValue +
                        "}," +
                        "\"date\":\"" + rateDate + "\"" +
                        "}";

        stubFor(get(urlEqualTo("/2004-11-11?base=USD&symbols=CHF"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));

        // when
        GenericUrl genericUrl = new GenericUrl("http://localhost:8080/2004-11-11?base=USD&symbols=CHF");
        ExchangeRatesRequester exchangeRatesRequester = new ExchangeRatesRequester(genericUrl);
        SingleDayRatesDTO singleDayRatesDTOResult = exchangeRatesRequester.getSingleDayRates();

        // then
        Map<String, Double> rate = new HashMap<>();
        rate.put(quoteCurrency, rateValue);
        SingleDayRatesDTO singleDayRatesDTOExpected = new SingleDayRatesDTO(baseCurrency, rateDate, rate);
        assertTrue(singleDayRatesDTOExpected.equals(singleDayRatesDTOResult));

    }

    @Test
    public void testsMultipleCurrenciesGetSingleDayRates() throws IOException {

        // given
        String baseCurrency = "GBP";
        String quoteCurrencySEK = "SEK";
        String quoteCurrencyNOK = "NOK";
        String quoteCurrencyDKK = "DKK";
        String rateDate = "2016-01-08";
        Double rateValueSEK = 12.4317288208;
        Double rateValueNOK = 12.9913176505;
        Double rateValueDKK = 10.0106013232;

        String responseBody =
                "{" +
                        "\"base\":\"" + baseCurrency + "\"," +
                        "\"rates\":{" +
                            "\"" + quoteCurrencyDKK + "\":" + rateValueDKK + "," +
                            "\"" + quoteCurrencyNOK + "\":" + rateValueNOK + "," +
                            "\"" + quoteCurrencySEK + "\":" + rateValueSEK +
                        "}," +
                        "\"date\":\"" + rateDate + "\"" +
                        "}";

        stubFor(get(urlEqualTo("/2016-01-10?base=GBP&symbols=DKK,NOK,SEK"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));

        // when
        GenericUrl genericUrl = new GenericUrl("http://localhost:8080/2016-01-10?base=GBP&symbols=DKK,NOK,SEK");
        ExchangeRatesRequester exchangeRatesRequester = new ExchangeRatesRequester(genericUrl);
        SingleDayRatesDTO singleDayRatesDTOResult = exchangeRatesRequester.getSingleDayRates();

        // then
        Map<String, Double> rate = new HashMap<>();
        rate.put(quoteCurrencyDKK, rateValueDKK);
        rate.put(quoteCurrencyNOK, rateValueNOK);
        rate.put(quoteCurrencySEK, rateValueSEK);
        SingleDayRatesDTO singleDayRatesDTOExpected = new SingleDayRatesDTO(baseCurrency, rateDate, rate);
        assertTrue(singleDayRatesDTOExpected.equals(singleDayRatesDTOResult));

    }

}
