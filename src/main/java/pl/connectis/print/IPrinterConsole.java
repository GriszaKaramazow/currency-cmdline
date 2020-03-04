package pl.connectis.print;

import java.util.List;

public class IPrinterConsole implements Printer {

    private final List<List<String>> fileContent;

    public IPrinterConsole(List<List<String>> fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public boolean print() {


        return true;
    }

}
