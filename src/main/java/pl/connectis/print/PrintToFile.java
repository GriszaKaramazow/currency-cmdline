package pl.connectis.print;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.File;
import java.io.IOException;

public class PrintToFile {

    private final String filePath;
    private final FileContent fileContent;

    private String fileExtension;

    private final String yellowFontColor = "\u001b[33m";
    private final String resetFontColor = "\u001b[0m";

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
                printResult(printToTXT.saveAsTXT(filePath));
                break;

            case "csv":
                PrintToText printToCSV = new PrintToText(fileContent.getFileContent());
                printResult(printToCSV.saveAsCSV(filePath));
                break;

            case "xls":
                PrintToExcel printToXLS = new PrintToExcel(fileContent.getFileContent());
                printResult(printToXLS.saveAsXLS(filePath));
                break;

            case "xlsx":
                PrintToExcel printToXLSX = new PrintToExcel(fileContent.getFileContent());
                printResult(printToXLSX.saveAsXLSX(filePath));
                break;

            default:
                System.out.println(yellowFontColor + fileExtension + " is unsupported file format." + resetFontColor);

        }

    }

    private boolean validateFilePath() {

        File myFile = new File(filePath);

        try {

            myFile.createNewFile();
            return true;

        } catch (IOException exception) {

            System.out.println(yellowFontColor + "Inappropriate file path '" + filePath + "'."  + resetFontColor);
            return false;

        }

    }

    private void printResult(boolean result) {

        if (result) {
            System.out.println(yellowFontColor + "The data have been saved successfully to '" + filePath + "'." +
                    resetFontColor);
        } else {
            System.out.println(yellowFontColor + "An error occurred during saving the data to file." + resetFontColor);
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
