package pl.connectis.dto;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class HistoryRatesDTO extends GenericJson {

    @Key("base")
    private String baseCurrency;

    @Key
    private Map<String, Map<String, Double>> rates;

}
