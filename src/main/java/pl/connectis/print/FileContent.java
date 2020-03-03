//package pl.connectis.print;
//
//import pl.connectis.dto.HistoryRatesDTO;
//
//import java.util.*;
//
//public class FileContent {
//
//    private final HistoryRatesDTO historyRatesDTO;
//
//    private List<List<String>> fileContent = new ArrayList<>();
//
//    public FileContent(HistoryRatesDTO historyRatesDTO) {
//        this.historyRatesDTO = historyRatesDTO;
//        prepareFileContent();
//    }
//
//    private void addTitleRowToFileContent() {
//
//        List<String> titleRow = new ArrayList<>();
//        titleRow.add("Date");
//
//        List<String> currencies = getCurrenciesAsSortedList();
//
//        for (String currency : currencies) {
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append(historyRatesDTO.getBaseCurrency());
//            stringBuilder.append("/");
//            stringBuilder.append(currency);
//            titleRow.add(stringBuilder.toString());
//        }
//
//        fileContent.add(titleRow);
//
//    }
//
//    private void prepareFileContent() {
//
//        addTitleRowToFileContent();
//
//        List<String> dates = getDatesAsSortedList();
//
//        for (String date : dates) {
//            Map<String, Double> ratesForDate = historyRatesDTO.getRates().get(date);
//            fileContent.add(getRatesAsList(date, ratesForDate));
//        }
//
//    }
//
//    private List<String> getRatesAsList(String date, Map<String, Double> rates) {
//
//        List<String> tableLine = new ArrayList<>();
//        tableLine.add(date);
//
//        List<String> currenciesList = getCurrenciesAsSortedList();
//
//        for (String currency : currenciesList) {
//            tableLine.add(String.valueOf(rates.get(currency)));
//        }
//
//        return tableLine;
//
//    }
//
//    private List<String> getCurrenciesAsSortedList() {
//
//        Map<String, Double> ratesMap = historyRatesDTO.getRates().entrySet().stream().findFirst().get().getValue();
//        Set<String> currenciesSet = ratesMap.keySet();
//        List<String> currencies = new ArrayList<>(currenciesSet);
//        Collections.sort(currencies);
//        return currencies;
//
//    }
//
//    private List<String> getDatesAsSortedList() {
//
//        Set<String> datesSet = historyRatesDTO.getRates().keySet();
//        List<String> datesList = new ArrayList<>(datesSet);
//        Collections.sort(datesList);
//        return datesList;
//
//    }
//
//    public List<List<String>> getFileContent() {
//        return fileContent;
//    }
//}
