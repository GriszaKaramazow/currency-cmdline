package pl.connectis.utils;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestUtils {

    public String getCellValue(HSSFSheet sheet, int row, int cell) {

        String cellValueString = sheet.getRow(row).getCell(cell).toString();

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            cellValueString = String.valueOf(LocalDate.parse(cellValueString, formatter));

        } finally {

            return cellValueString;

        }

    }

    public String getCellValue(XSSFSheet sheet, int row, int cell) {

        String cellValueString = sheet.getRow(row).getCell(cell).toString();

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            cellValueString = String.valueOf(LocalDate.parse(cellValueString, formatter));

        } finally {

            return cellValueString;

        }

    }

}
