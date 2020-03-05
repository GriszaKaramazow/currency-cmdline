package pl.connectis.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ExchangeRates {

    private List<SingleRate> historyRates = new ArrayList<>();

    public void addSingleRate(SingleRate singleRate) {

        historyRates.add(singleRate);

    }

}
