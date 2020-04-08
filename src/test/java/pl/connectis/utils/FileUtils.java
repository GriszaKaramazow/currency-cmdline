package pl.connectis.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Data
public class FileUtils {

    private final String testFilePath;

    public String getTextFileContentAsString() {

        String fileContent = "";

        try {

            fileContent = new String(Files.readAllBytes(Paths.get(testFilePath)));

        } catch (IOException exception) {

            log.error("Unable to read file: '" + testFilePath + "'.");
        }

        return fileContent;

    }

    public void deleteFile() {

        new File(testFilePath).delete();

    }

}
