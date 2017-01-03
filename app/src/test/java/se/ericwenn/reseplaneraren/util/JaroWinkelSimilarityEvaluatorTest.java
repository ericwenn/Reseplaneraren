package se.ericwenn.reseplaneraren.v2.util;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by ericwenn on 9/8/16.
 */
public class JaroWinkelSimilarityEvaluatorTest {

    JaroWinkelSimilarityEvaluator evaluator;
    List<se.ericwenn.reseplaneraren.v2.util.NameSortable> toEvaluate;



    class NameSortableImpl implements se.ericwenn.reseplaneraren.v2.util.NameSortable {

        private String name;

        public NameSortableImpl(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    @Before
    public void beforeTest() {
        evaluator = new JaroWinkelSimilarityEvaluator();
        toEvaluate = new ArrayList<>();
    }

    @Test
    public void testEvaluate() throws Exception {

        NameSortableImpl korsvagen = new NameSortableImpl("Korsvägen");
        NameSortableImpl hjalmar = new NameSortableImpl("Hjalmarbrantingsplatsen");
        NameSortableImpl kungsports = new NameSortableImpl("Kungsportsplatsen");

        toEvaluate.add( korsvagen );
        toEvaluate.add( hjalmar );
        toEvaluate.add( kungsports );



        String similarityTo = "Kors";


        Map<NameSortable, Double> evaluatedMap = evaluator.evaluate(toEvaluate, similarityTo);



        // Assert
        assertTrue("Korsvägen is a better match than Hjalmarbrantingsplatsen", evaluatedMap.get(korsvagen) > evaluatedMap.get(hjalmar));
        assertTrue("Kungsportsplatsen is a better match than Hjalmarbrantingsplatsen", evaluatedMap.get(kungsports) > evaluatedMap.get(hjalmar));
        assertTrue("Korsvägen is a better match than Kungsportsplatsen", evaluatedMap.get(korsvagen) > evaluatedMap.get(kungsports));

    }

    @Test
    public void testNullSearch() {
        NameSortableImpl korsvagen = new NameSortableImpl("Korsvägen");
        NameSortableImpl hjalmar = new NameSortableImpl("Hjalmarbrantingsplatsen");
        NameSortableImpl kungsports = new NameSortableImpl("Kungsportsplatsen");

        toEvaluate.add( korsvagen );
        toEvaluate.add( hjalmar );
        toEvaluate.add( kungsports );

        String similarityTo = null;

        Map<NameSortable, Double> evaluatedMap = evaluator.evaluate(toEvaluate, similarityTo);

        // Assert
        assertTrue("Korsvägen has zero similarity to null", evaluatedMap.get(korsvagen) == 0);
        assertTrue("Kungsportsplatsen has zero similarity to null", evaluatedMap.get(kungsports) == 0);
        assertTrue("Korsvägen has zero similarity to null", evaluatedMap.get(korsvagen) == 0);

    }



    @Test
    public void testNullName() {
        NameSortableImpl nullName = new NameSortableImpl(null);
        toEvaluate.add( nullName );

        String similarityTo = "Random string";
        Map<NameSortable, Double> evaluatedMap = evaluator.evaluate(toEvaluate, similarityTo);

        assertTrue("nullName has zero similarity to any string", evaluatedMap.get(nullName) == 0);



    }
}