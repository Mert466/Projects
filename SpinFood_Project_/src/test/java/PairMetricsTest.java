import controller.PairMetrics;
import data.EGender;
import data.Pair;
import data.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for PairMetrics.
 */
public class PairMetricsTest {

    private List<Pair> emptyPairs;
    private List<Person> emptyReplacementList;
    private List<Pair> pairs;
    private List<Person> replacementList;
    private PairMetrics emptyMetrics;
    private PairMetrics pairsMetrics;
    private PairMetrics pairsAndReplacementMetrics;

    @BeforeEach
    public void setup() {
        emptyPairs = new ArrayList<>();
        emptyReplacementList = new ArrayList<>();
        pairs = new ArrayList<>();
        replacementList = new ArrayList<>();

        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};
        String[] data3 = {"0", "3", "Mike", "meat", "35", "male", "yes", "3.0", "3.0", "3.0"};
        String[] data4 = {"0", "4", "Emily", "vegan", "28", "female", "no", "4.0", "4.0", "4.0"};
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);
        Person person3 = new Person(data3);
        Person person4 = new Person(data4);

        Pair pair1 = new Pair(person1, person2);
        Pair pair2 = new Pair(person3, person4);

        pairs.add(pair1);
        pairs.add(pair2);

        // Create replacement list
        Person replacementPerson1 = new Person(data1);
        Person replacementPerson2 = new Person(data2);

        replacementList.add(replacementPerson1);
        replacementList.add(replacementPerson2);

        emptyMetrics = new PairMetrics(emptyPairs, emptyReplacementList);
        pairsMetrics = new PairMetrics(pairs, emptyReplacementList);
        pairsAndReplacementMetrics = new PairMetrics(pairs, replacementList);
    }

    /**
     * Test case for empty metrics.
     */
    @Test
    public void testEmptyMetrics() {
        assertEquals(0, emptyMetrics.getCreatedPairsCount());
        assertEquals(0, emptyMetrics.getReplacementPairCount());
        assertEquals(0.0, emptyMetrics.getAvgGenderDiversity());
        assertEquals(0.0, emptyMetrics.getAvgAgeDifference());
        assertEquals(0.0, emptyMetrics.getAvgFoodPreferenceDeviation());
    }

    /**
     * Test case for metrics with pairs and an empty replacement list.
     */
    @Test
    public void testPairsMetricsWithEmptyReplacementList() {
        assertEquals(2, pairsMetrics.getCreatedPairsCount());
        assertEquals(0, pairsMetrics.getReplacementPairCount());
        assertEquals(0.0, pairsMetrics.getAvgGenderDiversity());
        assertEquals(1.0, pairsMetrics.getAvgAgeDifference());
        assertEquals(1.0, pairsMetrics.getAvgFoodPreferenceDeviation());
    }

    /**
     * Test case for metrics with pairs and a replacement list.
     */
    @Test
    public void testPairsAndReplacementMetrics() {
        assertEquals(2, pairsAndReplacementMetrics.getCreatedPairsCount());
        assertEquals(2, pairsAndReplacementMetrics.getReplacementPairCount());
        assertEquals(0.0, pairsAndReplacementMetrics.getAvgGenderDiversity());
        assertEquals(1.0, pairsAndReplacementMetrics.getAvgAgeDifference());
        assertEquals(1.0, pairsAndReplacementMetrics.getAvgFoodPreferenceDeviation());
    }

    /**
     * Test case for metrics with pairs and a single replacement person.
     */
    @Test
    public void testPairsAndSingleReplacementMetrics() {
        String[] dataReplacement = {"0", "4", "Emily", "vegan", "28", "female", "no", "4.0", "4.0", "4.0"};
        Person replacementPerson = new Person(dataReplacement);
        List<Person> singleReplacementList = new ArrayList<>();
        singleReplacementList.add(replacementPerson);

        PairMetrics pairAndSingleReplacementMetrics = new PairMetrics(pairs, singleReplacementList);

        assertEquals(2, pairAndSingleReplacementMetrics.getCreatedPairsCount());
        assertEquals(1, pairAndSingleReplacementMetrics.getReplacementPairCount());
        assertEquals(0.0, pairAndSingleReplacementMetrics.getAvgGenderDiversity());
        assertEquals(1.0, pairAndSingleReplacementMetrics.getAvgAgeDifference());
        assertEquals(1.0, pairAndSingleReplacementMetrics.getAvgFoodPreferenceDeviation());
    }

    /**
     * Test case for metrics with pairs having the same gender and age difference.
     */
    @Test
    public void testPairsWithSameGenderAndAgeDifferenceMetrics() {
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "25", "female", "no", "2.0", "2.0", "2.0"};
        String[] data3 = {"0", "3", "Mike", "meat", "25", "male", "yes", "3.0", "3.0", "3.0"};
        String[] data4 = {"0", "4", "Emily", "vegan", "25", "female", "no", "4.0", "4.0", "4.0"};

        Person person1 = new Person(data1);
        Person person2 = new Person(data2);
        Person person3 = new Person(data3);
        Person person4 = new Person(data4);

        Pair pairLocal1 = new Pair(person1, person2);
        Pair pairLocal2 = new Pair(person3, person4);

        List<Pair> pairsForTest;
        pairsForTest = new ArrayList<>();

        pairsForTest.add(pairLocal1);
        pairsForTest.add(pairLocal2);

        PairMetrics sameGenderAndAgeDifferenceMetrics = new PairMetrics(pairsForTest, emptyReplacementList);

        assertEquals(2, sameGenderAndAgeDifferenceMetrics.getCreatedPairsCount());
        assertEquals(0, sameGenderAndAgeDifferenceMetrics.getReplacementPairCount());
        assertEquals(0.0, sameGenderAndAgeDifferenceMetrics.getAvgGenderDiversity());
        assertEquals(0.0, sameGenderAndAgeDifferenceMetrics.getAvgAgeDifference());
        assertEquals(1.0, sameGenderAndAgeDifferenceMetrics.getAvgFoodPreferenceDeviation());
    }

    /**
     * Test case for metrics with pairs having the same food preference deviation.
     */
    @Test
    public void testPairsWithSameFoodPreferenceDeviationMetrics() {
        String[] data1 = {"0", "1", "John", "veggie", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "26", "male", "no", "2.0", "2.0", "2.0"};
        String[] data3 = {"0", "3", "Mike", "veggie", "25", "male", "yes", "3.0", "3.0", "3.0"};
        String[] data4 = {"0", "4", "Emily", "veggie", "27", "male", "no", "4.0", "4.0", "4.0"};

        Person person1 = new Person(data1);
        Person person2 = new Person(data2);
        Person person3 = new Person(data3);
        Person person4 = new Person(data4);

        Pair pairLocal1 = new Pair(person1, person2);
        Pair pairLocal2 = new Pair(person3, person4);

        List<Pair> pairsForTest;
        pairsForTest = new ArrayList<>();

        pairsForTest.add(pairLocal1);
        pairsForTest.add(pairLocal2);

        PairMetrics sameFoodPreferenceDeviationMetrics = new PairMetrics(pairsForTest, emptyReplacementList);

        assertEquals(0.0, sameFoodPreferenceDeviationMetrics.getAvgFoodPreferenceDeviation());
    }

    /**
     * Test case for metrics with pairs having different characteristics.
     */
    @Test
    public void testPairsWithDifferentMetrics() {
        PairMetrics differentMetrics = new PairMetrics(pairs, emptyReplacementList);

        assertEquals(2, differentMetrics.getCreatedPairsCount());
        assertEquals(0, differentMetrics.getReplacementPairCount());
        assertEquals(0.0, differentMetrics.getAvgGenderDiversity());
        assertEquals(1.0, differentMetrics.getAvgAgeDifference());
        assertEquals(1.0, differentMetrics.getAvgFoodPreferenceDeviation());
    }
}
