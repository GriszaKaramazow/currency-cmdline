package print;

import model.HistoryTable;

import java.util.*;

public class FileContent {

    private final HistoryTable historyTable;

    private List<List<String>> fileContent = new ArrayList<>();

    public FileContent(HistoryTable historyTable) {
        this.historyTable = historyTable;
    }

    private void addTitleRowToFileContent() {

        List<String> titleRow = new ArrayList<>();
        titleRow.add("Date");

        List<String> currencies = getCurrenciesAsSortedList();

        for (String currency : currencies) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(historyTable.getBase());
            stringBuilder.append("/");
            stringBuilder.append(currency);
            titleRow.add(stringBuilder.toString());
        }

        fileContent.add(titleRow);

    }

    public void prepareFileContent() {

        addTitleRowToFileContent();

        List<String> dates = getDatesAsSortedList();

        for (String date : dates) {
            TreeMap<String, Double> ratesForDate = historyTable.getRates().get(date);
            fileContent.add(getRatesAsList(date, ratesForDate));
        }

    }

    private List<String> getRatesAsList(String date, TreeMap<String, Double> rates) {

        List<String> tableLine = new ArrayList<>();
        tableLine.add(date);

        List<String> currenciesList = getCurrenciesAsSortedList();

        for (String currency : currenciesList) {
            tableLine.add(String.valueOf(rates.get(currency)));
        }

        return tableLine;

    }

    private List<String> getCurrenciesAsSortedList() {

        TreeMap<String, Double> ratesMap = historyTable.getRates().get(historyTable.getRates().firstKey());
        Set<String> currenciesSet = ratesMap.keySet();
        List<String> currencies = new ArrayList<>(currenciesSet);
        Collections.sort(currencies);
        return currencies;

    }

    private List<String> getDatesAsSortedList() {

        Set<String> datesSet = historyTable.getRates().keySet();
        List<String> datesList = new ArrayList<>(datesSet);
        Collections.sort(datesList);
        return datesList;

    }

    public List<List<String>> getFileContent() {
        return fileContent;
    }
}
