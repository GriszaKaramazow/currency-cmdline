package file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PrintToTXT {

    public void printToTXT(String filePath, List<List<String>> fileContent) {

        try {

            File myFile = new File(filePath);
            myFile.createNewFile();
            FileWriter fileWriter = new FileWriter(filePath);

            for (List<String> fileLine : fileContent) {
                String lineText = fileLine.stream()
                        .map(text -> text.toString())
                        .collect(Collectors.joining("\t"));
                fileWriter.write(lineText + "\n");
            }

            fileWriter.close();

        } catch (IOException e) {

            System.out.println("An error occurred");
            e.printStackTrace();

        }

    }

}
