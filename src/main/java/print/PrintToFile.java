package print;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PrintToFile {

    private final List<List<String>> fileContent;

    public PrintToFile(List<List<String>> fileContent) {

        this.fileContent = fileContent;

    }

    public void saveAsTXT(String filePath) {

        saveAsTextFile(filePath, "\t");

    }

    public void saveAsCSV(String filePath) {

        saveAsTextFile(filePath, ",");

    }

    public void saveAsTextFile(String filePath, String delimiter) {

        try {

            File myFile = new File(filePath);
            myFile.createNewFile();
            FileWriter fileWriter = new FileWriter(filePath);

            for (List<String> fileLine : fileContent) {
                String lineText = fileLine.stream()
                        .map(text -> text.toString())
                        .collect(Collectors.joining(delimiter));
                fileWriter.write(lineText + "\n");
            }

            fileWriter.close();

        } catch (IOException exception) {

            System.out.println("An error occurred");
            exception.printStackTrace();

        }

    }

}
