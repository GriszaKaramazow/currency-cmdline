package pl.connectis.request;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.extern.slf4j.Slf4j;
import pl.connectis.dto.HistoryRatesDTO;
import pl.connectis.dto.SingleDayRatesDTO;

import java.io.IOException;

@Slf4j
public class ExchangeRatesRequester {

    private final HttpTransport httpTransport = new NetHttpTransport();
    private final JsonFactory jacksonFactory = new JacksonFactory();

    private final GenericUrl genericUrl;

    public ExchangeRatesRequester(GenericUrl genericUrl) {
        this.genericUrl = genericUrl;
    }

    private HttpRequest getHttpRequester() throws IOException {

        HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
        HttpRequest httpRequest = requestFactory.buildGetRequest(genericUrl);
        httpRequest.setParser(new JsonObjectParser(jacksonFactory));
        return httpRequest;

    }

    public SingleDayRatesDTO getSingleDayRates() throws IOException {

        HttpRequest httpRequest = getHttpRequester();
        return httpRequest.execute().parseAs(SingleDayRatesDTO.class);

    }

    public HistoryRatesDTO getHistoryRates() throws IOException {

        HttpRequest httpRequest = getHttpRequester();
        return httpRequest.execute().parseAs(HistoryRatesDTO.class);

    }

}
