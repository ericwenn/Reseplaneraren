package se.ericwenn.reseplaneraren.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericwenn on 8/1/16.
 */
public class Util {
    public static <T> List<T> removeDuplicates(List<T> target, DuplicateChecker<T> checker) {
        List<Object> uniquesSeen = new ArrayList<>();
        List<T> duplicates = new ArrayList<>();
        for (T t : target) {
            if(uniquesSeen.contains(checker.uniqueAttribute(t))) {
                duplicates.add(t);
            } else {
                uniquesSeen.add(checker.uniqueAttribute(t));
            }
        }
        target.removeAll(duplicates);
        return target;
    }

    public interface DuplicateChecker<T> {
        Object uniqueAttribute(T original);
    }

}
