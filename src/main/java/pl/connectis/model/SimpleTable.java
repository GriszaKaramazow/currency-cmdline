package pl.connectis.model;

import com.google.api.client.util.Key;
import lombok.*;

import java.util.HashMap;

@Getter
@Setter
public class SimpleTable {

    @Key("base")
    private String baseCurrency;

    @Key
    private String date;

    @Key
    private HashMap<String, Double> rates;

}
