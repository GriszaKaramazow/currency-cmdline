package print;

import model.HistoryTable;

import java.util.TreeMap;

public class PrintToConsole {

    private final HistoryTable historyTable;

    public PrintToConsole(HistoryTable historyTable) {
        this.historyTable = historyTable;
    }

    public void printExchangeRateTable() {

        TreeMap<String, TreeMap<String, Double>> outerTreeMap = historyTable.getRates();
        outerTreeMap.forEach((key, value) -> printDateAndRate(key, value));

    }

    private void printDateAndRate(String date, TreeMap<String, Double> innerTreeMap) {

        System.out.print(date + "\t\t");
        innerTreeMap.forEach((key, value) -> printRate(key, value));
        System.out.println();

    }

    private void printRate(String outputCurrency, Double rate) {

        System.out.print(historyTable.getBaseAmount() + " ");
        System.out.print(historyTable.getBaseCurrency() + " = ");
        System.out.print(historyTable.getBaseAmount() * rate + " ");
        System.out.print(outputCurrency + "\t\t");

    }

}
