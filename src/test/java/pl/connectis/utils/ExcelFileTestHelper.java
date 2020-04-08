package pl.connectis.utils;

import java.io.IOException;

public abstract class ExcelFileTestHelper {

    public abstract String getCellValue(int row, int cell);

    public abstract void closeDeleteFile() throws IOException;

}
