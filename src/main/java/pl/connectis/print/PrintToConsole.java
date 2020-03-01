package pl.connectis.print;

import pl.connectis.model.HistoryRates;

import java.util.TreeMap;

public class PrintToConsole {

    private final HistoryRates historyRates;

    public PrintToConsole(HistoryRates historyRates) {
        this.historyRates = historyRates;
    }

    public void showOnScreen() {

        TreeMap<String, TreeMap<String, Double>> outerTreeMap = historyRates.getRates();
        outerTreeMap.forEach((key, value) -> printDateAndRate(key, value));

    }

    private void printDateAndRate(String date, TreeMap<String, Double> innerTreeMap) {

        System.out.print(date + "\t\t");
        innerTreeMap.forEach((key, value) -> printRate(key, value));
        System.out.println();

    }

    private void printRate(String quoteCurrency, Double rate) {

        System.out.print(quoteCurrency + "/");
        System.out.print(historyRates.getBaseCurrency() + ": ");
        System.out.print(rate + "\t\t");

    }

}
