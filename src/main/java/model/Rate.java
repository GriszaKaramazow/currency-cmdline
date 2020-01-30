package model;

import com.google.api.client.util.Key;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rate {

    @Key
    private String no;

    // TODO: Use LocalDate instead of String
    @Key
    private String effectiveDate;

    @Key
    private double mid;

}
