package pl.connectis.command;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.connectis.dto.HistoryRatesDTO;
import pl.connectis.model.CurrencySymbol;
import pl.connectis.model.ExchangeRates;
import pl.connectis.model.SingleRate;
import pl.connectis.request.ExchangeRatesRequester;
import pl.connectis.request.HistoryUrl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Command(name = "history",
         sortOptions = false,
         usageHelpAutoWidth = true,
         headerHeading = "history%n%n",
         header = "Generates table with exchange rates for selected period (from 1999-01-04) and prints it to the console or to a file.%n",
         optionListHeading = "%nOptions:%n",
         footerHeading = "%nSupported currencies:%n",
         footer = "AUD, BGN, BRL, CAD, CHF, CNY, CZK, DKK, EUR, GBP, HKD, HRK, HUF, IDR, ILS, INR, ISK, JPY, KRW, " +
                 "MXN, MYR, NOK, NZD, PHP, PLN, RON, RUB, SEK, SGD, THB, TRY, USD, ZAR.")
public class ExchangeRateHistory implements Runnable{

    @Option(names = {"-h", "--help"},
            usageHelp = true,
            description = "Display help menu.")
    private boolean usageHelpRequested;

    @Option(names = {"-b", "--base"},
            paramLabel = "<symbol>",
            defaultValue = "EUR",
            description = "Enter a symbol of a base currency. Default: ${DEFAULT-VALUE}.")
    private CurrencySymbol baseCurrency = CurrencySymbol.EUR;

    @Option(names = {"-q", "--quote"},
            paramLabel = "<symbol>",
            arity = "1..*",
            required = true,
            description = "Enter symbols of quote currencies separated by space (ie. -q EUR USD GBP). Required value.")
    private List<CurrencySymbol> quoteCurrencies;

    @Option(names = {"-s", "--start"},
            paramLabel = "<date>",
            required = true,
            description = "Enter a start date of a period in yyyy-MM-dd format starting from 1999-01-04. Required value.")
    private LocalDate startDate;

    @Option(names = {"-e", "--end"},
            paramLabel = "<date>",
            description = "Enter an end date of a period in yyyy-MM-dd format. Default: ${DEFAULT-VALUE}.")
    private LocalDate endDate = LocalDate.now();

    @Option(names = {"-f", "--file"},
            paramLabel = "FILE",
            description = "Enter a file (txt, csv, xls or xlsx) to save to or do not use, to print to console.")
    private String filePath;

    private final String yellowFontColor = "\u001b[33m";
    private final String resetFontColor = "\u001b[0m";

    @Override
    public void run() {

        if (startDate.isBefore(LocalDate.of(1999,1,4))) {
            log.error(yellowFontColor +  "The exchange rate data are available from 1999-01-04" + resetFontColor);
            return;
        }

        if (startDate.isAfter(endDate)) {
            log.error(yellowFontColor + "'from " + startDate + " to " + endDate + "' is invalid period" + resetFontColor);
            return;
        }

        ExchangeRatesRequester exchangeRatesRequester = new ExchangeRatesRequester(getHistoryUrl());

        HistoryRatesDTO historyRatesDTO = exchangeRatesRequester.getHistoryRates();

        ExchangeRates exchangeRates = new ExchangeRates(historyRatesDTO);

//        if (filePath == null) {
//            PrintToConsole printToConsole = new PrintToConsole(historyRatesDTO);
//            printToConsole.showOnScreen();
//        } else {
//            FileContent fileContent = new FileContent(historyRatesDTO);
//            PrintToFile printToFile = new PrintToFile(filePath, fileContent);
//            printToFile.printFileContentToFile();
//        }
        printRate(exchangeRates);

    }

    private HistoryUrl getHistoryUrl() {
        HistoryUrl historyUrl = new HistoryUrl();
        historyUrl.start_at = String.valueOf(startDate);
        historyUrl.end_at = String.valueOf(endDate);
        historyUrl.base = String.valueOf(baseCurrency);
        String symbols = quoteCurrencies.stream()
                .map(Enum::toString)
                .collect(Collectors.joining(","));
        historyUrl.symbols = symbols;
        return historyUrl;
    }

    private void printRate(ExchangeRates exchangeRates) {

        for (SingleRate singleRate : exchangeRates.getHistoryRates()) {
            log.info(singleRate.toString());
        }

    }

}

