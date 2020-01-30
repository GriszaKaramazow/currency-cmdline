package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Rate {

    private final String no;

    private final LocalDate effectiveDate;

    private final double rate;

}
