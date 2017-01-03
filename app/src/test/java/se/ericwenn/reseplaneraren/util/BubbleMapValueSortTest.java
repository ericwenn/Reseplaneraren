package se.ericwenn.reseplaneraren.v2.util;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by ericwenn on 9/9/16.
 */
public class BubbleMapValueSortTest {

    BubbleMapValueSort<String> sorter;
    Map<String, Double> map;


    @Before
    public void setUp() throws Exception {
        sorter = new BubbleMapValueSort<>();
        map = new HashMap<>();
    }

    @Test
    public void testSort() throws Exception {
        String value1 = new String("Value 1");
        String value0 = new String("Value 0");
        String value05 = new String("Value 05");
        String value3 = new String("Value 3");
        map.put( value1, 1d);
        map.put( value0, 0d);
        map.put(value05, 0.5);
        map.put(value3, 3d);

        List<String> result = sorter.sort(map, MapValueSort.Order.DESC);

        assertTrue(result.get(0).equals(value3));
        assertTrue(result.get(1).equals(value1));
        assertTrue(result.get(2).equals(value05));

        assertTrue(result.get(3).equals(value0));
    }


    @Test
    public void testSizeDifference() {
        String value1 = new String("Value 1");
        String value0 = new String("Value 1");
        String value05 = new String("Value 1");
        String value3 = new String("Value 1");
        map.put( value1, 1d);
        map.put( value0, 0d);
        map.put(value05, 0.5);
        map.put(value3, 3d);

        List<String> result = sorter.sort(map, MapValueSort.Order.DESC);

        assertTrue(result.size() == map.size());
    }


    @Test
    public void testAscending() throws Exception {
        String value1 = new String("Value 1");
        String value0 = new String("Value 0");
        String value05 = new String("Value 0.5");
        String value3 = new String("Value 3");
        map.put( value1, 1d);
        map.put( value0, 0d);
        map.put(value05, 0.5);
        map.put(value3, 3d);

        List<String> result = sorter.sort(map, MapValueSort.Order.ASC);


        assertTrue( result.get(0).equals(value0));
        assertTrue( result.get(1).equals(value05));
        assertTrue( result.get(2).equals(value1));
        assertTrue( result.get(3).equals(value3));
    }


    @Test(expected = NullPointerException.class)
    public void testNullValue() throws Exception {
        String valueNull = null;

        map.put( valueNull, null);

        List<String> result = sorter.sort(map, MapValueSort.Order.ASC);

    }
}