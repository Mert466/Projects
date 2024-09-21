import controller.Group;
import controller.GroupMetrics;
import controller.MealType;
import data.EFoodPreference;
import data.EGender;
import data.Pair;
import data.Person;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The GroupMetricsTest class contains unit tests for the GroupMetrics class.
 */
public class GroupMetricsTest {

    /**
     * Tests the generateMetrics method with empty lists of groups and replacement pairs.
     * Expects all metrics to be initialized to zero.
     */
    @Test
    public void testGenerateMetrics_EmptyGroupsAndReplacementList() {
        // Create an empty list of groups and replacement pairs
        List<Group> groups = new ArrayList<>();
        List<Pair> replacementList = new ArrayList<>();

        // Create a GroupMetrics object
        GroupMetrics groupMetrics = new GroupMetrics(groups, replacementList);

        // Verify that all metrics are initialized to zero
        assertEquals(0, groupMetrics.getCreatedGroupsCount(), "Created groups count should be 0");
        assertEquals(0, groupMetrics.getReplacementGroupCount(), "Replacement group count should be 0");
        assertEquals(0.0, groupMetrics.getAvgGenderDiversity(), "Average gender diversity should be 0.0");
        assertEquals(0.0, groupMetrics.getAvgAgeDifference(), "Average age difference should be 0.0");
        assertEquals(0.0, groupMetrics.getAvgFoodPreferenceDeviation(), "Average food preference deviation should be 0.0");
        assertEquals(0.0, groupMetrics.getPathLength(), "Path length should be 0.0");
    }

    /**
     * Tests the generateMetrics method with non-empty lists of groups and replacement pairs.
     * Expects the metrics to be calculated correctly based on the provided groups and pairs.
     */
    @Test
    public void testGenerateMetrics_NonEmptyGroupsAndReplacementList() {
        // Create a list of groups with pairs
        List<Group> groups = new ArrayList<>();
        groups.add(createGroupWithPairs(
                createPair(createPerson("1", "John", EGender.MALE), createPerson("2", "Jane", EGender.FEMALE)),
                createPair(createPerson("3", "Alice", EGender.FEMALE), createPerson("4", "Bob", EGender.MALE)),
                createPair(createPerson("5", "Eve", EGender.FEMALE), createPerson("6", "Adam", EGender.MALE))
        ));

        // Create a list of replacement pairs
        List<Pair> replacementList = Arrays.asList(
                createPair(createPerson("5", "Eve", EGender.FEMALE), createPerson("6", "Adam", EGender.MALE)),
                createPair(createPerson("7", "Carol", EGender.MALE), createPerson("8", "Dave", EGender.FEMALE))
        );

        // Create a GroupMetrics object
        GroupMetrics groupMetrics = new GroupMetrics(groups, replacementList);

        // Verify the generated metrics
        assertEquals(1, groupMetrics.getCreatedGroupsCount(), "Created groups count should be 1");
        assertEquals(2, groupMetrics.getReplacementGroupCount(), "Replacement group count should be 2");
        assertEquals(0.0, groupMetrics.getAvgGenderDiversity(), "Average gender diversity should be 0.0");
        assertEquals(0.0, groupMetrics.getAvgAgeDifference(), "Average age difference should be 0.0");
        assertEquals(0.0, groupMetrics.getAvgFoodPreferenceDeviation(), "Average food preference deviation should be 0.0");
        assertEquals(0.0, groupMetrics.getPathLength(), "Path length should be 0.0");
    }

    /**
     * Creates a Group object with the specified pair.
     *
     * @param pair the pair to be added to the group
     * @return the created Group object
     */
    private Group createGroupWithPairs(Pair pair) {
        Person person1 = pair.getPerson1();
        Person person2 = pair.getPerson2();
        Pair pair2 = createPair(createPerson("2", "Jane", EGender.FEMALE), createPerson("3", "Alice", EGender.FEMALE));
        Pair pair3 = createPair(createPerson("4", "Bob", EGender.MALE), createPerson("5", "Eve", EGender.FEMALE));
        return new Group(pair, pair2, pair3, null);
    }


    /**
     * Creates a Pair object with the specified persons.
     *
     * @param person1 the first person in the pair
     * @param person2 the second person in the pair
     * @return the created Pair object
     */
    private Pair createPair(Person person1, Person person2) {
        return new Pair(person1, person2);
    }

    /**
     * Creates a Person object with the specified UUID, name, and gender.
     *
     * @param uuid   the UUID of the person
     * @param name   the name of the person
     * @param gender the gender of the person
     * @return the created Person object
     */
    /**
     * Creates a Person object with the specified UUID, name, and gender.
     *
     * @param uuid   the UUID of the person
     * @param name   the name of the person
     * @param gender the gender of the person
     * @return the created Person object
     */
    private Person createPerson(String uuid, String name, EGender gender) {
        String[] data = {uuid, "0", name, "none", "0", gender.toString(), "no", "", "", ""};
        return new Person(data);
    }

    /**
     * Creates a Group object with the specified pairs.
     *
     * @param pair1 the first pair
     * @param pair2 the second pair
     * @param pair3 the third pair
     * @return the created Group object
     */
    private Group createGroupWithPairs(Pair pair1, Pair pair2, Pair pair3) {
        return new Group(pair1, pair2, pair3, null);
    }

    /**
     * Tests the roundValue method of the GroupMetrics class.
     */
    @Test
    public void testRoundValue() {
        GroupMetrics groupMetrics = new GroupMetrics(new ArrayList<>(), new ArrayList<>());

        double val1 = 3.14159;
        double expected1 = 3.14;
        double actual1 = groupMetrics.roundValue(val1);
        assertEquals(expected1, actual1);

        double val2 = 2.71828;
        double expected2 = 2.72;
        double actual2 = groupMetrics.roundValue(val2);
        assertEquals(expected2, actual2);
    }

    /**
     * Tests the generateMetrics method of the GroupMetrics class with sample data.
     */
    @Test
    public void testGenerateMetrics_SampleData() {
        List<Group> groups = createSampleGroups();
        List<Pair> replacementList = createSampleReplacementPairs();

        GroupMetrics groupMetrics = new GroupMetrics(groups, replacementList);

        assertEquals(3, groupMetrics.getCreatedGroupsCount());
        assertEquals(2, groupMetrics.getReplacementGroupCount());
        assertEquals(0.0, groupMetrics.getAvgGenderDiversity());
        assertEquals(1.0, groupMetrics.getAvgAgeDifference());
        assertEquals(1.33, groupMetrics.getAvgFoodPreferenceDeviation());
        assertEquals(0.0, groupMetrics.getPathLength());
    }

    /**
     * Creates a list of sample groups for testing.
     *
     * @return the list of sample groups
     */
    private List<Group> createSampleGroups() {
        List<Group> groups = new ArrayList<>();

        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);
        Pair pair1 = new Pair(person1, person2);

        String[] data3 = {"0", "3", "Alex", "meat", "35", "male", "yes", "3.0", "3.0", "3.0"};
        String[] data4 = {"0", "4", "Emma", "vegan", "40", "female", "no", "4.0", "4.0", "4.0"};
        Person person3 = new Person(data3);
        Person person4 = new Person(data4);
        Pair pair2 = new Pair(person3, person4);

        String[] data5 = {"0", "5", "Mike", "veggie", "45", "male", "yes", "5.0", "5.0", "5.0"};
        String[] data6 = {"0", "6", "Lisa", "meat", "50", "female", "no", "6.0", "6.0", "6.0"};
        Person person5 = new Person(data5);
        Person person6 = new Person(data6);
        Pair pair3 = new Pair(person5, person6);

        Group group1 = new Group(pair1, pair2, pair3, MealType.STARTER);
        Group group2 = new Group(pair1, pair3, pair2, MealType.MAIN);
        Group group3 = new Group(pair2, pair1, pair3, MealType.DESSERT);

        groups.add(group1);
        groups.add(group2);
        groups.add(group3);

        return groups;
    }

    private List<Pair> createSampleReplacementPairs() {
        List<Pair> replacementPairs = new ArrayList<>();

        String[] data7 = {"0", "7", "Tom", "meat", "55", "male", "yes", "7.0", "7.0", "7.0"};
        String[] data8 = {"0", "8", "Sarah", "vegan", "60", "female", "no", "8.0", "8.0", "8.0"};
        Person person7 = new Person(data7);
        Person person8 = new Person(data8);
        Pair pair4 = new Pair(person7, person8);

        String[] data9 = {"0", "9", "Dave", "veggie", "65", "male", "no", "9.0", "9.0", "9.0"};
        String[] data10 = {"0", "10", "Emma", "meat", "70", "female", "yes", "10.0", "10.0", "10.0"};
        Person person9 = new Person(data9);
        Person person10 = new Person(data10);
        Pair pair5 = new Pair(person9, person10);

        replacementPairs.add(pair4);
        replacementPairs.add(pair5);

        return replacementPairs;
    }

}
