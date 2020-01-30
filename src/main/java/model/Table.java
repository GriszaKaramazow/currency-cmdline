package model;

import com.google.api.client.util.Key;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Table {

    @Key
    private String table;

    @Key
    private String currency;

    @Key
    private String code;

    @Key
    private List<Rate> rates;

}
