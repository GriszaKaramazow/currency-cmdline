package pl.connectis.command;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.connectis.dto.ExchangeRatesMapper;
import pl.connectis.dto.SingleDayRatesDTO;
import pl.connectis.model.CurrencySymbol;
import pl.connectis.model.ExchangeRates;
import pl.connectis.print.PrinterFactory;
import pl.connectis.request.ExchangeRatesRequester;
import pl.connectis.request.SimpleUrl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Command(name = "single",
         sortOptions = false,
         usageHelpAutoWidth = true,
         headerHeading = "single%n%n",
         header = "Prints to console an exchange rate for a single date starting from 1999-01-04.%n",
         optionListHeading = "%nOptions:%n",
         footerHeading = "%nSupported currencies:%n",
         footer = "AUD, BGN, BRL, CAD, CHF, CNY, CZK, DKK, EUR, GBP, HKD, HRK, HUF, IDR, ILS, INR, ISK, JPY, KRW, " +
                 "MXN, MYR, NOK, NZD, PHP, PLN, RON, RUB, SEK, SGD, THB, TRY, USD, ZAR.")
public class ExchangeRateSingle implements Runnable{

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

    @Option(names = {"-d", "--date"},
            paramLabel = "<date>",
            description = "Enter a date of exchange rate in yyyy-MM-dd format starting from 1999-01-04. Default: ${DEFAULT-VALUE}.")
    private LocalDate exchangeDate = LocalDate.now();

    @Option(names = {"-f", "--file"},
            paramLabel = "FILE",
            description = "Enter a file (txt, csv, xls or xlsx) to save to or do not use, to print to console.")
    private String filePath;

    @Override
    public void run() {

        if (exchangeDate.isBefore(LocalDate.of(1999,1,4))) {
            log.warn("The exchange rate data are available from 1999-01-04.");
            return;
        }

        ExchangeRatesRequester exchangeRatesRequester = new ExchangeRatesRequester(getSimpleUrl());

        SingleDayRatesDTO singleDayRatesDTO;

        try {

            singleDayRatesDTO = exchangeRatesRequester.getSingleDayRates();

        } catch (IOException exception) {

            log.error("Error requesting data from the API.", exception);
            return;
        }

        ExchangeRatesMapper exchangeRatesMapper = new ExchangeRatesMapper();
        ExchangeRates exchangeRates = exchangeRatesMapper.mapSingleDatesDTO(singleDayRatesDTO);

        if (!validateDate(singleDayRatesDTO)) {
            log.info("An exchange rate unavailable for " + exchangeDate + ".");
        }

        PrinterFactory printerFactory = new PrinterFactory(filePath);
        printerFactory.print(exchangeRates);

    }

    private SimpleUrl getSimpleUrl() {

        SimpleUrl simpleUrl = new SimpleUrl(exchangeDate);
        simpleUrl.base = String.valueOf(baseCurrency);
        String symbols = quoteCurrencies.stream()
                .map(Enum::toString)
                .collect(Collectors.joining(","));
        simpleUrl.symbols = symbols;
        return simpleUrl;

    }

    private boolean validateDate(SingleDayRatesDTO singleDayRatesDTO) {

        String receivedDate = singleDayRatesDTO.getRateDate();
        return receivedDate.equals(String.valueOf(exchangeDate));

    }

}
