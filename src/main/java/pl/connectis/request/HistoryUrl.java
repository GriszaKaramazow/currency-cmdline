package pl.connectis.request;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

public class HistoryUrl extends GenericUrl {

    public HistoryUrl() {
        super("https://api.ratesapi.io/api/history");
    }

    @Key
    public String start_at;

    @Key
    public String end_at;

    @Key
    public String base;

    @Key
    public String symbols;

}
