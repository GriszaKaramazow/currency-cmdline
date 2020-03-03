//package pl.connectis.print;
//
//import pl.connectis.dto.HistoryRatesDTO;
//
//import java.util.Map;
//
//public class PrintToConsole {
//
//    private final HistoryRatesDTO historyRatesDTO;
//
//    public PrintToConsole(HistoryRatesDTO historyRatesDTO) {
//        this.historyRatesDTO = historyRatesDTO;
//    }
//
//    public void showOnScreen() {
//
//        Map<String, Map<String, Double>> outerTreeMap = historyRatesDTO.getRates();
//        outerTreeMap.forEach((key, value) -> printDateAndRate(key, value));
//
//    }
//
//    private void printDateAndRate(String date, Map<String, Double> innerTreeMap) {
//
//        System.out.print(date + "\t\t");
//        innerTreeMap.forEach((key, value) -> printRate(key, value));
//        System.out.println();
//
//    }
//
//    private void printRate(String quoteCurrency, Double rate) {
//
//        System.out.print(quoteCurrency + "/");
//        System.out.print(historyRatesDTO.getBaseCurrency() + ": ");
//        System.out.print(rate + "\t\t");
//
//    }
//
//}
