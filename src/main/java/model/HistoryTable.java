package model;

import com.google.api.client.util.Key;
import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HistoryTable {

    @Key
    private String base;

    @Key
    private String date;

    @Key
    private HashMap<String, HashMap<String, Double>> rates;

}
