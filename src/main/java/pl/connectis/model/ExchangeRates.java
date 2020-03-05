package pl.connectis.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExchangeRates {

    private List<SingleRate> historyRates = new ArrayList<>();

    public void addSingleRate(SingleRate singleRate) {

        historyRates.add(singleRate);

    }

}
