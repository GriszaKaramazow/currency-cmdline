package pl.connectis.utils;

import java.io.IOException;

public abstract class ExcelFileTestHelper {

    public abstract String getCellValueString(int row, int cell);

    public abstract double getCellValueDouble(int row, int cell);

    public abstract String getCellValueDateAsString(int row, int cell);

    public abstract void closeDeleteFile() throws IOException;

}
