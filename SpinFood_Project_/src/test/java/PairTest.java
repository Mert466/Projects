import data.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The PairTest class contains unit tests for the Pair class.
 */
public class PairTest {

    private Person person1;
    private Person person2;
    private Pair pair;

    /**
     * Sets up the test data before each test.
     */
    @BeforeEach
    public void setUp() {
        // Sample data for Person initialization
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};
        person1 = new Person(data1);
        person2 = new Person(data2);
        pair = new Pair(person1, person2);
    }

    /**
     * Tests the constructor of the Pair class.
     */
    @Test
    public void testConstructor() {
        assertNotNull(pair.getPerson1(), "Person 1 should not be null");
        assertNotNull(pair.getPerson2(), "Person 2 should not be null");
        assertNotNull(pair.getFoodPreference(), "Food preference should not be null");
        assertNotNull(pair.getPairID(), "Pair ID should not be null");
    }

    /**
     * Tests the getFoodPreference method of the Pair class.
     */
    @Test
    public void testGetFoodPreference() {
        assertNotNull(pair.getFoodPreference(), "Food preference should not be null");
    }

    /**
     * Tests the getFoodPreferenceDifference method of the Pair class.
     */
    @Test
    public void testGetFoodPreferenceDifference() {
        assertNotNull(pair.getFoodPreferenceDifference(), "Food preference difference should not be null");
    }

    /**
     * Tests the setFoodPreferenceDifference method of the Pair class.
     */
    @Test
    public void testSetFoodPreferenceDifference() {
        pair.setFoodPreferenceDifference(2);
        assertEquals(2, pair.getFoodPreferenceDifference(), "Food preference difference should be set correctly");
    }

    /**
     * Tests the createPairID method of the Pair class.
     */
    @Test
    public void testCreatePairID() {
        assertNotNull(pair.getPairID(), "Pair ID should not be null");
    }

    /**
     * Tests the isSignedInTogether method of the Pair class.
     */
    @Test
    public void testIsSignedInTogether() {
        pair.setSignedInTogether(true);
        assertTrue(pair.isSignedInTogether(), "Pair should be signed in together");
        pair.setSignedInTogether(false);
        assertFalse(pair.isSignedInTogether(), "Pair should not be signed in together");
    }

    /**
     * Tests the getPerson1 method of the Pair class.
     */
    @Test
    public void testGetPerson1() {
        assertNotNull(pair.getPerson1(), "Person 1 should not be null");
    }

    /**
     * Tests the getPerson2 method of the Pair class.
     */
    @Test
    public void testGetPerson2() {
        assertNotNull(pair.getPerson2(), "Person 2 should not be null");
    }

    /**
     * Tests the getPairID method of the Pair class.
     */
    @Test
    public void testGetPairID() {
        assertNotNull(pair.getPairID(), "Pair ID should not be null");
    }

    /**
     * Tests the getPairFoodPreference method of the Pair class.
     */
    @Test
    public void testGetPairFoodPreference() {
        assertNotNull(pair.getPairFoodPreference(), "Pair food preference should not be null");
    }

    /**
     * Tests the isHasCooked method of the Pair class.
     */
    @Test
    public void testIsHasCooked() {
        assertFalse(pair.isHasCooked(), "Pair should not have cooked by default");
        pair.setHasCooked(true);
        assertTrue(pair.isHasCooked(), "Pair should have cooked");
    }

    /**
     * Tests the getAvailability method of the Pair class.
     */
    @Test
    public void testGetAvailability() {
        assertTrue(pair.getAvailability(), "Pair should be available by default");
        pair.setAvailability(false);
        assertFalse(pair.getAvailability(), "Pair should not be available");
    }

    /**
     * Tests the getPathLength method of the Pair class.
     */
    @Test
    public void testGetPathLength() {
        assertNotNull(pair.getPathLength(), "Path length should not be null");
    }

    /**
     * Tests the setPathLength method of the Pair class.
     */
    @Test
    public void testSetPathLength() {
        pair.setPathLength(3.5);
        assertEquals(3.5, pair.getPathLength(), "Path length should be set correctly");
    }

    /**
     * Tests the getKitchen method of the Pair class.
     */
    @Test
    public void testGetKitchen() {
        assertNull(pair.getKitchen(), "Kitchen is null unless it is set");
    }

    /**
     * Tests the setKitchen method of the Pair class.
     */
    @Test
    public void testSetKitchen() {
        Kitchen kitchen = new Kitchen(EHasKitchen.YES, 1.0, 2.0, 3.0);
        pair.setKitchen(kitchen);
        assertEquals(kitchen, pair.getKitchen(), "Kitchen should be set correctly");
    }

    /**
     * Tests the toString method of the Pair class.
     */
    @Test
    public void testToString() {
        assertEquals("John & Jane", pair.toString(), "ToString should return the correct representation");
    }

    /**
     * Tests the compareTo method of the Pair class.
     */
    @Test
    public void testCompareToTrue() {
        Pair otherPair = new Pair(person2, person1);
        assertTrue(pair.compareTo(otherPair) > 0, "Pair should be greater than the other pair based on person name comparison");
    }

    /**
     * Tests the compareTo method of the Pair class.
     */
    @Test
    public void testCompareToFalse() {
        Pair otherPair = new Pair(person2, person1);
        assertFalse(pair.compareTo(otherPair) < 0, "Pair should not be greater than the other pair based on person name comparison");
    }

    /**
     * Tests the food preference of the Pair class.
     */
    @Test
    public void testFoodPreference() {
        // Create pairs with different food preferences
        Pair veganPair = new Pair(person1, person2, EFoodPreference.VEGAN);
        Pair veggiePair = new Pair(person1, person2, EFoodPreference.VEGGIE);
        Pair meatPair = new Pair(person1, person2, EFoodPreference.MEAT);

        // Test the food preferences of each pair
        assertEquals(EFoodPreference.VEGAN, veganPair.getFoodPreference(), "Food preference should be VEGAN");
        assertEquals(EFoodPreference.VEGGIE, veggiePair.getFoodPreference(), "Food preference should be VEGGIE");
        assertEquals(EFoodPreference.MEAT, meatPair.getFoodPreference(), "Food preference should be MEAT");
    }

    /**
     * Tests the food preference of a Pair object with vegan food preferences.
     */
    @Test
    public void testFoodPreference_Vegan() {
        // Create new Person objects
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "vegan", "30", "female", "no", "2.0", "2.0", "2.0"};
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);

        // Create Pair
        Pair pair = new Pair(person1, person2);

        // Test not null assertions
        assertNotNull(pair.getPerson1(), "Person 1 should not be null");
        assertNotNull(pair.getPerson2(), "Person 2 should not be null");
        assertNotNull(pair.getFoodPreference(), "Food preference should not be null");
        assertNotNull(pair.getPairID(), "Pair ID should not be null");

        // Test food preference
        assertEquals(EFoodPreference.VEGAN, pair.getFoodPreference(), "Food preference should be VEGAN");
    }

    /**
     * Tests the food preference of a Pair object with veggie food preferences.
     */
    @Test
    public void testFoodPreference_Veggie() {
        // Create new Person objects
        String[] data1 = {"0", "1", "John", "veggie", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);

        // Create Pair
        Pair pair = new Pair(person1, person2);

        // Test not null assertions
        assertNotNull(pair.getPerson1(), "Person 1 should not be null");
        assertNotNull(pair.getPerson2(), "Person 2 should not be null");
        assertNotNull(pair.getFoodPreference(), "Food preference should not be null");
        assertNotNull(pair.getPairID(), "Pair ID should not be null");

        // Test food preference
        assertEquals(EFoodPreference.VEGGIE, pair.getFoodPreference(), "Food preference should be VEGGIE");
    }

    /**
     * Tests the food preference of a Pair object with veggie and meat food preferences.
     */
    @Test
    public void testFoodPreference_VeggieAndMeat() {
        // Create new Person objects
        String[] data1 = {"0", "1", "John", "veggie", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "meat", "30", "female", "no", "2.0", "2.0", "2.0"};
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);

        // Create Pair
        Pair pair = new Pair(person1, person2);

        // Test not null assertions
        assertNotNull(pair.getPerson1(), "Person 1 should not be null");
        assertNotNull(pair.getPerson2(), "Person 2 should not be null");
        assertNotNull(pair.getFoodPreference(), "Food preference should not be null");
        assertNotNull(pair.getPairID(), "Pair ID should not be null");

        // Test food preference
        assertEquals(EFoodPreference.VEGGIE, pair.getFoodPreference(), "Food preference should be VEGGIE");
    }

    /**
     * Tests the food preference of a Pair object with meat food preferences.
     */
    @Test
    public void testFoodPreference_MeatAndMeat() {
        // Create new Person objects
        String[] data1 = {"0", "1", "John", "meat", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "meat", "30", "female", "no", "2.0", "2.0", "2.0"};
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);

        // Create Pair
        Pair pair = new Pair(person1, person2);

        // Test not null assertions
        assertNotNull(pair.getPerson1(), "Person 1 should not be null");
        assertNotNull(pair.getPerson2(), "Person 2 should not be null");
        assertNotNull(pair.getFoodPreference(), "Food preference should not be null");
        assertNotNull(pair.getPairID(), "Pair ID should not be null");

        // Test food preference
        assertEquals(EFoodPreference.MEAT, pair.getFoodPreference(), "Food preference should be MEAT");
    }

    /**
     * Tests the food preference of a Pair object with vegan and veggie food preferences.
     */
    @Test
    public void testFoodPreference_VeganAndVeggie() {
        // Create new Person objects
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);

        // Create Pair
        Pair pair = new Pair(person1, person2);

        // Test not null assertions
        assertNotNull(pair.getPerson1(), "Person 1 should not be null");
        assertNotNull(pair.getPerson2(), "Person 2 should not be null");
        assertNotNull(pair.getFoodPreference(), "Food preference should not be null");
        assertNotNull(pair.getPairID(), "Pair ID should not be null");

        // Test food preference
        assertEquals(EFoodPreference.VEGAN, pair.getFoodPreference(), "Food preference should be VEGGIE");
    }
}
