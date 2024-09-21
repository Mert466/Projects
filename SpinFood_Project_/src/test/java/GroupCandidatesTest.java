import controller.GroupCandidates;
import data.Pair;
import data.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for the GroupCandidates class.
 */
public class GroupCandidatesTest {

    private Pair pair1;
    private Pair pair2;
    private GroupCandidates groupCandidates;

    /**
     * Sets up the test environment before each test case.
     */
    @BeforeEach
    public void setup() {
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};
        String[] data3 = {"0", "3", "Mike", "meat", "35", "male", "yes", "3.0", "3.0", "3.0"};
        String[] data4 = {"0", "4", "Emily", "vegan", "28", "female", "no", "4.0", "4.0", "4.0"};
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);
        Person person3 = new Person(data3);
        Person person4 = new Person(data4);
        pair1 = new Pair(person1, person2);
        pair2 = new Pair(person3, person4);
        groupCandidates = new GroupCandidates(pair1, pair2);
    }

    /**
     * Test for getting the rating of group candidates.
     */
    @Test
    public void testGetRating() {
        int rating = groupCandidates.getRating();

        // Perform assertions
        assertEquals(0, rating);
    }

    /**
     * Test for increasing the rating of group candidates.
     */
    @Test
    public void testIncreaseRating() {
        groupCandidates.increaseRating(5);

        // Perform assertions
        assertEquals(5, groupCandidates.getRating());
    }

    /**
     * Test for getting the first candidate pair.
     */
    @Test
    public void testGetFirstCandidate() {
        Pair firstCandidate = groupCandidates.getFirstCandidate();

        // Perform assertions
        assertEquals(pair1, firstCandidate);
        assertNotNull(firstCandidate);
    }

    /**
     * Test for getting the second candidate pair.
     */
    @Test
    public void testGetSecondCandidate() {
        Pair secondCandidate = groupCandidates.getSecondCandidate();

        // Perform assertions
        assertEquals(pair2, secondCandidate);
        assertNotNull(secondCandidate);
    }

    /**
     * Test for the initial rating of group candidates.
     */
    @Test
    public void testInitialRating() {
        int rating = groupCandidates.getRating();
        assertEquals(0, rating);
    }
}
