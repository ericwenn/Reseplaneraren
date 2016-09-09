package se.ericwenn.reseplaneraren.v2.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by ericwenn on 9/9/16.
 */
public class BubbleMapValueSort<K> implements MapValueSort<K, Double> {

    @Override
    public List<K> sort(Map<K, Double> toSort, Order order) {

        if( toSort.containsValue(null)) {
            throw new NullPointerException("Map contains null value");
        }

        List<K> result = new LinkedList<>();
        LinkedHashMap<K, Double> toSortCopy = new LinkedHashMap<>(toSort);
        List<Map.Entry<K, Double>> list = new LinkedList<>(toSort.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<K, Double>>() {
            @Override
            public int compare(Map.Entry<K, Double> lhs, Map.Entry<K, Double> rhs) {
                return lhs.getValue() == rhs.getValue() ? 0 : lhs.getValue() > rhs.getValue() ? -1 : 1;
            }
        });


        for (Map.Entry<K, Double> entry : list) {
            result.add(entry.getKey());

        }

        if( order == Order.ASC ) {
            Collections.reverse(result);
        }


        return result;
    }



}