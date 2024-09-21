import controller.ListAndCutElements;
import data.Pair;
import data.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Test class for ListAndCutElements.
 */
public class ListAndCutElementsTest {

    /**
     * Test case for getCutList method.
     */
    @Test
    public void testGetCutList() {
        // Create person data arrays
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};

        // Create Person objects
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);

        // Create a list of pairs for the cut list
        List<Pair> cutList = Arrays.asList(
                new Pair(person1, person2)
        );

        // Create a ListAndCutElements object
        ListAndCutElements listAndCutElements = new ListAndCutElements(cutList, new ArrayList<>());

        // Verify the cut list
        assertSame(cutList, listAndCutElements.getCutList(), "Cut list should be the same as the input list");
    }

    /**
     * Test case for getCutElements method.
     */
    @Test
    public void testGetCutElements() {
        // Create person data arrays
        String[] data1 = {"0", "3", "Alice", "meat", "28", "female", "yes", "1.5", "1.5", "1.5"};
        String[] data2 = {"0", "4", "Bob", "vegan", "32", "male", "no", "2.5", "2.5", "2.5"};

        // Create Person objects
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);

        // Create a list of pairs for the cut elements
        List<Pair> cutElements = Arrays.asList(
                new Pair(person1, person2)
        );

        // Create a ListAndCutElements object
        ListAndCutElements listAndCutElements = new ListAndCutElements(new ArrayList<>(), cutElements);

        // Verify the cut elements
        assertSame(cutElements, listAndCutElements.getCutElements(), "Cut elements should be the same as the input list");
    }

    /**
     * Test case for empty lists.
     */
    @Test
    public void testEmptyLists() {
        // Create empty lists
        List<Pair> cutList = new ArrayList<>();
        List<Pair> cutElements = new ArrayList<>();

        // Create a ListAndCutElements object
        ListAndCutElements listAndCutElements = new ListAndCutElements(cutList, cutElements);

        // Verify the cut lists
        assertSame(cutList, listAndCutElements.getCutList(), "Cut list should be the same as the input list");
        assertSame(cutElements, listAndCutElements.getCutElements(), "Cut elements should be the same as the input list");
    }

    /**
     * Test case for null lists.
     */
    @Test
    public void testNullLists() {
        // Create null lists
        List<Pair> cutList = null;
        List<Pair> cutElements = null;

        // Create a ListAndCutElements object
        ListAndCutElements listAndCutElements = new ListAndCutElements(cutList, cutElements);

        // Verify the cut lists
        assertEquals(cutList, listAndCutElements.getCutList(), "Cut list should be null");
        assertEquals(cutElements, listAndCutElements.getCutElements(), "Cut elements should be null");
    }

    /**
     * Test case for mixed lists.
     */
    @Test
    public void testMixedLists() {
        // Create person data arrays
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};
        String[] data3 = {"0", "3", "Alice", "meat", "28", "female", "yes", "1.5", "1.5", "1.5"};

        // Create Person objects
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);
        Person person3 = new Person(data3);

        // Create a list of pairs for the cut list
        List<Pair> cutList = Arrays.asList(
                new Pair(person1, person2)
        );

        // Create a list of pairs for the cut elements
        List<Pair> cutElements = Arrays.asList(
                new Pair(person2, person3)
        );

        // Create a ListAndCutElements object
        ListAndCutElements listAndCutElements = new ListAndCutElements(cutList, cutElements);

        // Verify the cut lists
        assertSame(cutList, listAndCutElements.getCutList(), "Cut list should be the same as the input list");
        assertSame(cutElements, listAndCutElements.getCutElements(), "Cut elements should be the same as the input list");
    }

    /**
     * Test case for an empty cut list.
     */
    @Test
    public void testEmptyCutList() {
        // Create person data arrays
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};

        // Create Person objects
        Person person1 = new Person(data1);

        // Create an empty list for the cut elements
        List<Pair> cutElements = Arrays.asList(
                new Pair(person1, person1)
        );

        // Create a ListAndCutElements object with an empty cut list
        ListAndCutElements listAndCutElements = new ListAndCutElements(new ArrayList<>(), cutElements);

        // Verify the cut lists
        assertEquals(new ArrayList<>(), listAndCutElements.getCutList(), "Cut list should be an empty list");
        assertSame(cutElements, listAndCutElements.getCutElements(), "Cut elements should be the same as the input list");
    }
}
