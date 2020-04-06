package pl.connectis.dto;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleDayRatesDTO extends GenericJson {

    @Key("base")
    private String baseCurrency;

    @Key("date")
    private String rateDate;

    @Key
    private Map<String, Double> rates;

}

