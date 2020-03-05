package pl.connectis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SingleRate {

    private final String baseCurrency;

    private final String quoteCurrency;

    private final String rateDate;

    private final Double rateValue;

}
