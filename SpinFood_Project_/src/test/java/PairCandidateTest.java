import controller.PairCandidate;
import data.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PairCandidate class.
 */
public class PairCandidateTest {

    /**
     * Tests the initialization of a PairCandidate object.
     */
    @Test
    public void testPairCandidateInitialization() {
        Person person = new Person(new String[]{"", "uuid", "John Doe", "meat", "25", "male", "yes"});
        PairCandidate pairCandidate = new PairCandidate(person);

        assertEquals(person, pairCandidate.getCandidate(), "Candidate should be initialized correctly");
        assertEquals(0, pairCandidate.getRating(), "Rating should be initialized to 0");
        assertEquals(0, pairCandidate.getFoodPreferenceDifference(), "Food preference difference should be initialized to 0");
    }

    /**
     * Tests the increaseRating() method of PairCandidate.
     */
    @Test
    public void testIncreaseRating() {
        Person person = new Person(new String[]{"", "uuid", "John Doe", "meat", "25", "male", "yes"});
        PairCandidate pairCandidate = new PairCandidate(person);

        pairCandidate.increaseRating(5);
        assertEquals(5, pairCandidate.getRating(), "Rating should be increased correctly");

        pairCandidate.increaseRating(3);
        assertEquals(8, pairCandidate.getRating(), "Rating should be increased correctly again");
    }

    /**
     * Tests the setFoodPreferenceDifference() method of PairCandidate.
     */
    @Test
    public void testSetFoodPreferenceDifference() {
        Person person = new Person(new String[]{"", "uuid", "John Doe", "meat", "25", "male", "yes"});
        PairCandidate pairCandidate = new PairCandidate(person);

        pairCandidate.setFoodPreferenceDifference(2);
        assertEquals(2, pairCandidate.getFoodPreferenceDifference(), "Food preference difference should be set correctly");

        pairCandidate.setFoodPreferenceDifference(0);
        assertEquals(0, pairCandidate.getFoodPreferenceDifference(), "Food preference difference should be set correctly again");
    }

    /**
     * Tests the getRating() method of PairCandidate.
     */
    @Test
    public void testGetRating() {
        Person person = new Person(new String[]{"", "uuid", "John Doe", "meat", "25", "male", "yes"});

        PairCandidate pairCandidate = new PairCandidate(person);

        int initialRating = pairCandidate.getRating();
        assertEquals(0, initialRating, "Rating should be initialized to 0");

        pairCandidate.increaseRating(5);
        int updatedRating = pairCandidate.getRating();
        assertEquals(initialRating + 5, updatedRating, "Rating should reflect the increase");
    }

    /**
     * Tests the getCandidate() method of PairCandidate.
     */
    @Test
    public void testGetCandidate() {
        Person person = new Person(new String[]{"", "uuid", "John Doe", "meat", "25", "male", "yes"});

        PairCandidate pairCandidate = new PairCandidate(person);

        Person candidate = pairCandidate.getCandidate();
        assertNotNull(candidate, "Candidate should not be null");
        assertEquals(person, candidate, "Candidate should be the same as the one provided");
    }

    /**
     * Tests the getFoodPreferenceDifference() method of PairCandidate.
     */
    @Test
    public void testGetFoodPreferenceDifference() {
        Person person = new Person(new String[]{"", "uuid", "John Doe", "meat", "25", "male", "yes"});

        PairCandidate pairCandidate = new PairCandidate(person);

        int initialDifference = pairCandidate.getFoodPreferenceDifference();
        assertEquals(0, initialDifference, "Food preference difference should be initialized to 0");

        pairCandidate.setFoodPreferenceDifference(3);
        int updatedDifference = pairCandidate.getFoodPreferenceDifference();
        assertEquals(3, updatedDifference, "Food preference difference should reflect the update");
    }

    /**
     * Tests setting a negative food preference difference.
     */
    @Test
    public void testSetFoodPreferenceDifferenceNegative() {
        Person person = new Person(new String[]{"", "uuid", "John Doe", "meat", "25", "male", "yes"});

        PairCandidate pairCandidate = new PairCandidate(person);

        pairCandidate.setFoodPreferenceDifference(-2);
        assertEquals(-2, pairCandidate.getFoodPreferenceDifference(), "Negative food preference difference should be set correctly");
    }

    /**
     * Tests that the candidate is not null.
     */
    @Test
    public void testCandidateNotNull() {
        Person person = new Person(new String[]{"", "uuid", "John Doe", "meat", "25", "male", "yes"});

        PairCandidate pairCandidate = new PairCandidate(person);

        assertNotNull(pairCandidate.getCandidate(), "Candidate should not be null");
    }

    /**
     * Test case for a null candidate.
     *
     * This test creates a pair candidate with a null person object
     * and asserts that the candidate is null.
     */
    @Test
    public void testCandidateNull() {
        // Create a pair candidate with a null person object
        PairCandidate pairCandidate = new PairCandidate(null);

        // Assert that the candidate is null
        assertNull(pairCandidate.getCandidate(), "Candidate should be null");
    }

    /**
     * Test case for default food preference difference.
     *
     * This test creates a person object with default values,
     * creates a pair candidate with that person object,
     * and asserts that the food preference difference is initialized to 0.
     */
    @Test
    public void testFoodPreferenceDifferenceDefault() {
        Person person = new Person(new String[]{"", "uuid", "John Doe", "meat", "25", "male", "yes"});

        PairCandidate pairCandidate = new PairCandidate(person);

        assertEquals(0, pairCandidate.getFoodPreferenceDifference(), "Food preference difference should be initialized to 0");
    }

}
