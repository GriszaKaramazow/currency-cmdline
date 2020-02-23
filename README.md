# currency-cmdline

This simple command line app generates currencies exchange rate tables for selected period. 
The app acquires exchange rates published by the European Central Bank from external WebAPI. 
The data are available starting from 1999-01-04 for AUD, BGN, BRL, CAD, CHF, CNY, CZK, DKK, EUR, GBP, HKD, HRK, HUF, 
IDR, ILS, INR, ISK, JPY, KRW, MXN, MYR, NOK, NZD, PHP, PLN, RON, RUB, SEK, SGD, THB, TRY, USD, ZAR.

---

## How tu use the app

The app requires Git, Apache Maven and Java Development Kit to build and run.

```
# Clone the repository with Git
$ git clone https://github.com/GriszaKaramazow/currency-cmdline
   
# Go into the repository
$ cd currency-cmdline
   
# Build JAR file with Maven 
$ mvn package 
   
# Run the app with Java Virtual Machine
$ java -jar currency-cmdline-0.3.0.jar -h
```

---

## Commands

### ```simple``` 

Prints to console an exchange rate for a single date.

#### Options

```
-h, --help      Display help menu.
-b, --base      Enter a symbol of a base currency. Default: EUR.
-q, --quote     Enter symbols of quote currencies separated by space (ie. -q EUR USD GBP). Required value.
-d, --date      Enter a date of exchange rate (yyyy-MM-dd). Default: today.
```

#### Examples

```java -jar currency-cmdline-0.3.0.jar single -h```  
displays help menu for command ```single```.&nbsp;&nbsp;


```java -jar currency-cmdline-0.3.0.jar single -q PLN```  
displays PLN/EUR exchange rate for today.&nbsp;&nbsp;

```java -jar currency-cmdline-0.3.0.jar single -b GBP -q EUR USD RUB -d 2019-12-13```  
displays EUR/GBP, USD/GBP and RUB/GBP exchange rates for 2019-12-13.&nbsp;&nbsp;

---

### ```history``` 

Generates a table with exchange rates for selected period starting and prints it to console or to file.

#### Options

```
-h, --help      Display help menu.
-b, --base      Enter a symbol of a base currency. Default: EUR.
-q, --quote     Enter symbols of quote currencies separated by space (ie. -q EUR USD GBP). Required value.
-s, --start     Enter a start date of a period (yyyy-MM-dd). Required value.
-e, --end       Enter an end date of a period (yyyy-MM-dd). Default: today.
-f, --file      Enter a file (txt, csv, xls or xlsx) to save to or do not use, to print to console.
```

#### Examples

```java -jar currency-cmdline-0.3.0.jar history -h```  
displays help menu for command ```history```.


```java -jar currency-cmdline-0.3.0.jar history -q PLN -s 2020-02-03```  
prints to console PLN/EUR exchange rates from between 2020-02-03 and today.

```java -jar currency-cmdline-0.3.0.jar history -b USD -q EUR GBP -s 2019-12-01 -e 2019-12-31 -f rates.xlsx```  
prints to ```rates.xlxs``` file EUR/USD and GBP/USD all exchange rates from december 2019.

---

## The app was build with

* [Maven](https://maven.apache.org/) - Dependency Management
* [picocli](https://github.com/remkop/picocli) - Command line interface
* [Google HTTP Client Library for Java](https://googleapis.github.io/google-http-java-client/) - HTTP requests and JSON parsing
* [Apache POI](https://poi.apache.org/) - Generates xls and xlsx files
* [Project Lombok](https://projectlombok.org/) - Reduces boiler-plate code
* [Exchangerate API](https://exchangeratesapi.io/) - WebAPI delivering foreign exchange rates published by the European Central Bank
