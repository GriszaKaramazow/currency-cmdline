package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Table {

    private final String table;

    private final String currency;

    private final String code;

    private final List<Rate> rates;

}
