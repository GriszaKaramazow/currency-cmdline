package pl.connectis.print;

import lombok.extern.slf4j.Slf4j;
import pl.connectis.model.ExchangeRates;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
abstract class PlainTextPrinter extends BasePrinter {

    @Override
    public void print(ExchangeRates exchangeRates) {

    }

    void printToTextFile(ExchangeRates exchangeRates, String filePath, String delimiter) {

        try {

            FileWriter fileWriter = new FileWriter(filePath);

            for (List<String> fileLine : prepareFileContent(exchangeRates)) {

                String lineText = String.join(delimiter, fileLine);
                fileWriter.write(lineText + "\n");

            }

            fileWriter.close();
            log.info("The data have been saved to '" + filePath + "'.");

        } catch (IOException exception) {

            log.error("An error occurred during saving data to the file", exception);

        }

    }
}
