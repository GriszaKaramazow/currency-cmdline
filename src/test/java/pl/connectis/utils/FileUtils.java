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

        try {

            return new String(Files.readAllBytes(Paths.get(testFilePath)));

        } catch (IOException exception) {

            log.error("Unable to read a file: '" + testFilePath + "'.");
            throw new IllegalStateException("Unable to read a file: '" + testFilePath + "'.");

        }

    }

    public void deleteFile() {

        new File(testFilePath).delete();

    }

}
