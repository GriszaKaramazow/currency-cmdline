package pl.connectis.print;

import java.util.List;

public class ConsolePrinter implements Printer {

    private final List<List<String>> fileContent;

    public ConsolePrinter(List<List<String>> fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public boolean print() {

        return true;
    }

}
