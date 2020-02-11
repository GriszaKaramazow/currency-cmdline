package print;

import java.io.File;
import java.io.IOException;

public class PrintToFile {

    private final String filePath;
    private final FileContent fileContent;

    private String fileExtension;

    public PrintToFile(String filePath, FileContent fileContent) {
        this.filePath = filePath;
        setFileExtension();
        this.fileContent = fileContent;
    }

    public void printFileContentToFile() {

        if (!validateFilePath()) {
            return;
        }

        switch (fileExtension) {

            case "txt":
                PrintToText printToTXT = new PrintToText(fileContent.getFileContent());
                printToTXT.saveAsTXT(filePath);
                break;

            case "csv":
                PrintToText printToCSV = new PrintToText(fileContent.getFileContent());
                printToCSV.saveAsCSV(filePath);
                break;

            case "xls":
                PrintToExcel printToXLS = new PrintToExcel(fileContent.getFileContent());
                printToXLS.saveAsXLS(filePath);
                break;

            case "xlsx":
                PrintToExcel printToXLSX = new PrintToExcel(fileContent.getFileContent());
                printToXLSX.saveAsXLSX(filePath);
                break;

            default:
                System.out.println("Unsupported file format (" + fileExtension + ")");

        }

    }

    private boolean validateFilePath() {
        File myFile = new File(filePath);
        try {
            myFile.createNewFile();
            return true;
        } catch (IOException exception) {
            System.out.println("Inappropiate file path " + filePath);
            return false;
        }
    }

    private void setFileExtension() {

        String fileExtension = "";
        int i = filePath.lastIndexOf('.');

        if (i > 0) {
            fileExtension = filePath.substring(i + 1);
        }

        this.fileExtension = fileExtension;

    }
}
