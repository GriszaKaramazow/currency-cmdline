package pl.connectis.request;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

import java.time.LocalDate;

public class SimpleUrl extends GenericUrl {

    @Key
    public String base;
    @Key
    public String symbols;

    public SimpleUrl(LocalDate exchangeDate) {
        super("https://api.ratesapi.io/api/" + exchangeDate);
    }

}
