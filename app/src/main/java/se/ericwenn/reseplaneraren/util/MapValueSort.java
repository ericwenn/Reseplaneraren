package se.ericwenn.reseplaneraren.v2.util;

import java.util.List;
import java.util.Map;

/**
 * Created by ericwenn on 9/8/16.
 */
public interface MapValueSort<K,V> {

    List<K> sort(Map<K,V> toSort, Order order);

    public enum Order {
        ASC,
        DESC
    }
}
