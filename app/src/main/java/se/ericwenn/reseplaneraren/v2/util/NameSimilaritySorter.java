package se.ericwenn.reseplaneraren.v2.util;

import java.util.List;

/**
 * Created by ericwenn on 9/8/16.
 */
public interface NameSimilaritySorter {

    List<NameSortable> sort(List<NameSortable> toSort, String sortAfter);

    interface NameSortable {
        String getName();
    }
}
