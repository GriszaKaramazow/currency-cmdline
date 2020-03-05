package pl.connectis.model;

import lombok.Data;

@Data
public class SingleRate {

    private final String baseCurrency;

    private final String quoteCurrency;

    private final String rateDate;

    private final Double rateValue;

}
