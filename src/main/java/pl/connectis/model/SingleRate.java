package pl.connectis.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SingleRate {

    private final String baseCurrency;

    private final String quoteCurrency;

    private final String rateDate;

    private final Double rateValue;

    public SingleRate(String baseCurrency, String quoteCurrency, String rateDate, Double rateValue) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.rateDate = rateDate;
        this.rateValue = rateValue;
    }

}
