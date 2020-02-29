package pl.connectis.command;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import pl.connectis.model.HistoryTable;
import pl.connectis.model.SimpleTable;

import java.io.IOException;

public class ExchangeRatesRequester {

    private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private final JsonFactory JSON_FACTORY = new JacksonFactory();

    private final String httpRequestAddress;

    public ExchangeRatesRequester(String httpRequestAddress) {
        this.httpRequestAddress = httpRequestAddress;
    }

    private HttpRequest getHttpRequester(GenericUrl genericUrl) {
        HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(
                (HttpRequest request) -> {
                    request.setParser(new JsonObjectParser(JSON_FACTORY));});
        try {
            HttpRequest httpRequest = requestFactory.buildGetRequest(genericUrl);
            return httpRequest;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public SimpleTable getSimpleTable() {
        HttpRequest httpRequest = getHttpRequester(new GenericUrl(httpRequestAddress));
        try {
            return httpRequest.execute().parseAs(SimpleTable.class);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public HistoryTable getHistoryTable() {
        HttpRequest httpRequest = getHttpRequester(new GenericUrl(httpRequestAddress));
        try {
            return httpRequest.execute().parseAs(HistoryTable.class);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
