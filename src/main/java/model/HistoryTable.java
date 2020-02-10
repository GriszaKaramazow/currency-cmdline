package model;

import com.google.api.client.util.Key;
import lombok.*;

import java.util.TreeMap;

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
    private TreeMap<String, TreeMap<String, Double>> rates;

    private double baseAmount;

}
