package pl.connectis.dto;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SingleDayRatesDTO extends GenericJson {

    @Key("base")
    private String baseCurrency;

    @Key("date")
    private String rateDate;

    @Key
    private Map<String, Double> rates;

}

