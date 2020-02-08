import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import command.CurrencyRate;
import model.Table;
import picocli.CommandLine;

import java.io.IOException;

public class Main {

    static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    static JsonFactory JSON_FACTORY = new JacksonFactory();

    public static void main(String[] args) throws IOException {

        CommandLine commandLine = new CommandLine(new CurrencyRate());
        commandLine.parseArgs(args);
        if (commandLine.isUsageHelpRequested()); {
            commandLine.usage(System.out);
        }
        Table table = getTable("http://api.nbp.pl/api/exchangerates/rates/A/EUR");
        System.out.println(table.toString());

    }

    static Table getTable(String tableURL) throws IOException {

        HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(
                (HttpRequest request) -> {request.setParser(new JsonObjectParser(JSON_FACTORY));});
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(tableURL));
        Table table = request.execute().parseAs(Table.class);
        return table;

    }
}
