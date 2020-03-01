package pl.connectis.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.*;

import java.util.TreeMap;

@Getter
@Setter
public class HistoryRates extends GenericJson {

    @Key("base")
    private String baseCurrency;

    @Key
    private TreeMap<String, TreeMap<String, Double>> rates;

}
