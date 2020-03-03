//package pl.connectis.print;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//@Slf4j
//public class PrintToFile {
//
//    private final String filePath;
//    private final FileContent fileContent;
//
//    private String fileExtension;
//
//    private final String yellowFontColor = "\u001b[33m";
//    private final String resetFontColor = "\u001b[0m";
//
//    public PrintToFile(String filePath, FileContent fileContent) {
//
//        this.filePath = filePath;
//        setFileExtension();
//        this.fileContent = fileContent;
//
//    }
//
//    public void printFileContentToFile() {
//
//        if (!validateFileExtension()) {
//            return;
//        }
//
//        if (!validateFilePath()) {
//            return;
//        }
//
//        switch (fileExtension) {
//
//            case "txt":
//                PrintToText printToTXT = new PrintToText(fileContent.getFileContent());
//                printResult(printToTXT.saveAsTXT(filePath));
//                break;
//
//            case "csv":
//                PrintToText printToCSV = new PrintToText(fileContent.getFileContent());
//                printResult(printToCSV.saveAsCSV(filePath));
//                break;
//
//            case "xls":
//                PrintToExcel printToXLS = new PrintToExcel(fileContent.getFileContent());
//                printResult(printToXLS.saveAsXLS(filePath));
//                break;
//
//            case "xlsx":
//                PrintToExcel printToXLSX = new PrintToExcel(fileContent.getFileContent());
//                printResult(printToXLSX.saveAsXLSX(filePath));
//                break;
//        }
//
//    }
//
//    private boolean validateFilePath() {
//
//        File myFile = new File(filePath);
//
//        try {
//
//            myFile.createNewFile();
//            log.debug("File '" + filePath + "' successfully created.");
//            return true;
//
//        } catch (IOException exception) {
//
//            log.error(yellowFontColor + "Inappropriate file path '" + filePath + "'."  + resetFontColor, exception);
//            return false;
//
//        }
//
//    }
//
//    private boolean validateFileExtension() {
//
//        if (fileExtension == null) {
//            log.error(yellowFontColor + "An inappropriate file '" + filePath + "'." + resetFontColor);
//            return false;
//        } else if (!Arrays.asList("txt", "csv", "xls", "xlsx").contains(fileExtension)) {
//            log.error(yellowFontColor + fileExtension + " is unsupported file format." + resetFontColor);
//            return false;
//        }
//
//        return true;
//
//    }
//
//    private void printResult(boolean result) {
//
//        if (result) {
//            log.info(yellowFontColor + "The data have been saved successfully to '" + filePath + "'." + resetFontColor);
//        } else {
//            log.error(yellowFontColor + "An error occurred during saving the data to file." + resetFontColor);
//        }
//
//    }
//
//    private void setFileExtension() {
//
//        String fileExtension = "";
//        int i = filePath.lastIndexOf('.');
//
//        if (i <= 0) {
//            this.fileExtension = null;
//            log.debug("An inappropriate file format: '" + filePath + "'.");
//        } else {
//            this.fileExtension = filePath.substring(i + 1);
//            log.debug("File extension: " + fileExtension);
//        }
//
//    }
//
//}
