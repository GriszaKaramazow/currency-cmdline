package print;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PrintToText {

    private final List<List<String>> fileContent;

    public PrintToText(List<List<String>> fileContent) {

        this.fileContent = fileContent;

    }

    public boolean saveAsTXT(String filePath) {

        return saveAsTextFile(filePath, "\t");

    }

    public boolean saveAsCSV(String filePath) {

        return saveAsTextFile(filePath, ",");

    }

    public boolean saveAsTextFile(String filePath, String delimiter) {

        try {

            FileWriter fileWriter = new FileWriter(filePath);

            for (List<String> fileLine : fileContent) {
                String lineText = String.join(delimiter, fileLine);
                fileWriter.write(lineText + "\n");
            }

            fileWriter.close();
            return true;

        } catch (IOException exception) {

            System.out.println("An error occurred during saving data to file");
            return false;

        }

    }

}
