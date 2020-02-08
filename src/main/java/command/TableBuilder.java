package command;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import model.HistoryTable;
import model.SimpleTable;

import java.io.IOException;

public class TableBuilder {

    private HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private JsonFactory JSON_FACTORY = new JacksonFactory();

    private final String httpRequestAddress;
    private HttpRequest httpRequest;

    public TableBuilder(String httpRequestAddress) {
        this.httpRequestAddress = httpRequestAddress;
    }

    public void getRequest() throws IOException {
        HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(
                (HttpRequest request) -> {
                    request.setParser(new JsonObjectParser(JSON_FACTORY));});
        httpRequest = requestFactory.buildGetRequest(new GenericUrl(httpRequestAddress));
    }

    public SimpleTable getSimpleTable() {
        try {
            return httpRequest.execute().parseAs(SimpleTable.class);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public HistoryTable getHistoryTable() {
        try {
            return httpRequest.execute().parseAs(HistoryTable.class);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
