package pl.connectis.dto;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.Data;

import java.util.Map;

@Data
public class HistoryRatesDTO extends GenericJson {

    @Key("base")
    private String baseCurrency;

    @Key
    private Map<String, Map<String, Double>> rates;

}
