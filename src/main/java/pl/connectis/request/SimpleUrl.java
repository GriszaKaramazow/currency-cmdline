package pl.connectis.request;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

import java.time.LocalDate;

public class SimpleUrl extends GenericUrl {

    public SimpleUrl(LocalDate exchangeDate) {
        super("https://api.ratesapi.io/api/" + String.valueOf(exchangeDate));
    }

    @Key
    public String base;

    @Key
    public String symbols;

}
