package pl.connectis.command;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.extern.slf4j.Slf4j;
import pl.connectis.model.HistoryTable;
import pl.connectis.model.SimpleTable;

import java.io.IOException;

@Slf4j
public class ExchangeRatesRequester {

    private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private final JsonFactory JSON_FACTORY = new JacksonFactory();

    private final GenericUrl genericUrl;

    public ExchangeRatesRequester(GenericUrl genericUrl) {
        this.genericUrl = genericUrl;
    }

    private HttpRequest getHttpRequester() {
        HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(
                (HttpRequest request) -> {
                    request.setParser(new JsonObjectParser(JSON_FACTORY));});
        try {
            HttpRequest httpRequest = requestFactory.buildGetRequest(genericUrl);
            return httpRequest;
        } catch (IOException exception) {
            log.error("An error occurred during building http request", exception);
        }
        return null;
    }

    public SimpleTable getSimpleTable() {
        HttpRequest httpRequest = getHttpRequester();
        try {
            return httpRequest.execute().parseAs(SimpleTable.class);
        } catch (IOException exception) {
            log.error("An error occurred during parsing http request", exception);
        }
        return null;
    }

    public HistoryTable getHistoryTable() {
        HttpRequest httpRequest = getHttpRequester();
        try {
            return httpRequest.execute().parseAs(HistoryTable.class);
        } catch (IOException exception) {
            log.error("An error occurred during parsing http request", exception);
        }
        return null;
    }

}
