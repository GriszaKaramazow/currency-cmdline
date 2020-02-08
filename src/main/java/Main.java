import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import command.ExchangeRateHistory;
import command.ExchangeRateSingle;
import model.Table;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;

@Command(name = "exchange-rate",
        subcommands = {
            ExchangeRateSingle.class,
            ExchangeRateHistory.class}
)
public class Main implements Runnable {

    // VERSION
    @Option(names = {"-v", "--version"}, versionHelp = true, description = "Display version info")
    boolean versionInfoRequested;

    // HELP
    @Option(names = {"?", "-h", "--help"}, usageHelp = true)
    private boolean usageHelpRequested;

    static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    static JsonFactory JSON_FACTORY = new JacksonFactory();

    public static void main(String[] args) throws IOException {




        CommandLine commandLine = new CommandLine(new Main());
        commandLine.execute(args);

        Table table = getTable("https://api.exchangeratesapi.io/latest");
        System.out.println(table.toString());

    }

    static Table getTable(String tableURL) throws IOException {

        HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(
                (HttpRequest request) -> {request.setParser(new JsonObjectParser(JSON_FACTORY));});
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(tableURL));
        Table table = request.execute().parseAs(Table.class);
        return table;

    }

    @Override
    public void run() {
        System.out.println("Main");
    }
}
