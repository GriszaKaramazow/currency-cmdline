package pl.connectis.model;

import com.google.api.client.util.Key;
import lombok.*;

import java.util.TreeMap;

@Getter
@Setter
public class HistoryTable {

    @Key("base")
    private String baseCurrency;

    @Key
    private String date;

    @Key
    private TreeMap<String, TreeMap<String, Double>> rates;

}