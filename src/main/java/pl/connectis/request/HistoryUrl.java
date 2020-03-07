package pl.connectis.request;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

public class HistoryUrl extends GenericUrl {

    @Key("start_at")
    public String startAt;
    @Key("end_at")
    public String endAt;
    @Key
    public String base;
    @Key
    public String symbols;

    public HistoryUrl() {
        super("https://api.ratesapi.io/api/history");
    }

}
