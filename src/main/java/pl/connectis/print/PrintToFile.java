package pl.connectis.print;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

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
                AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW) + fileExtension +
                        " is unsupported file format." + Ansi.ansi().reset());

        }

    }

    private boolean validateFilePath() {

        File myFile = new File(filePath);

        try {

            myFile.createNewFile();
            return true;

        } catch (IOException exception) {

            AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW) +
                    "Inappropriate file path '" + filePath + "'."  + Ansi.ansi().reset());
            return false;

        }

    }

    private void printResult(boolean result) {

        if (result) {
            AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW) +
                    "The data have been saved successfully to '" + filePath + "'." + Ansi.ansi().reset());
        } else {
            AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW) +
                    "An error occurred during saving the data to file." + Ansi.ansi().reset());
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
