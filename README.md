# currency-cmdline

This simple command line app generates currencies exchange rate tables for selected period. 
The app acquires exchange rates published by the European Central Bank from external WebAPI. 
The data are available starting from 1999-01-04 for AUD, BGN, BRL, CAD, CHF, CNY, CZK, DKK, EUR, GBP, HKD, HRK, HUF, 
IDR, ILS, INR, ISK, JPY, KRW, MXN, MYR, NOK, NZD, PHP, PLN, RON, RUB, SEK, SGD, THB, TRY, USD, ZAR.

## getting started

Clone, compile and use :)

### built with

* [Maven](https://maven.apache.org/) - Dependency Management
* [picocli](https://github.com/remkop/picocli) - Command line interface
* [Google HTTP Client Library for Java](https://googleapis.github.io/google-http-java-client/) - HTTP requests and JSON parsing
* [Apache POI](https://poi.apache.org/) - Generates xls and xlsx files
* [Project Lombok](https://projectlombok.org/) - Reduces boiler-plate code
* [Exchangerate API](https://exchangeratesapi.io/) - WebAPI delivering foreign exchange rates published by the European Central Bank

### requirements

* [Java](https://www.java.com//download/) - Compiler
* [Maven](https://maven.apache.org/) - Dependency Management

#### ```simple``` 
Prints to console an exchange rate for a single date.

For more details use ```single --help``` or ```single -h```.

#### ```history``` 
Generates table with exchange rates for selected period starting and prints it to console or to file.

For more details use ```history --help``` or ```history -h```.
