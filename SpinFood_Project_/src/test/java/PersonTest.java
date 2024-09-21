import data.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Person class.
 */
public class PersonTest {

    private Person person;

    @BeforeEach
    public void setUp() {
        // Sample data for Person initialization
        String[] data = {"0","1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        person = new Person(data);
    }

    /**
     * Test for the isAvailable() method of the Person class.
     */
    @Test
    public void testIsAvailable() {
        assertTrue(person.isAvailable(), "Person should be available");
        person.setAvailable(false);
        assertFalse(person.isAvailable(), "Person should not be available");
    }

    /**
     * Test for the getUUID() method of the Person class.
     */
    @Test
    public void testGetUUID() {
        assertEquals("1", person.getUUID(), "UUID should be '1'");
    }

    /**
     * Test for the getName() method of the Person class.
     */
    @Test
    public void testGetName() {
        assertEquals("John", person.getName(), "Name should be 'John'");
    }

    /**
     * Test for the getFoodPreference() method of the Person class.
     */
    @Test
    public void testGetFoodPreference() {
        assertEquals(EFoodPreference.VEGAN, person.getFoodPreference(), "Food preference should be 'VEGAN'");
    }

    /**
     * Test for the getAge() method of the Person class.
     */
    @Test
    public void testGetAge() {
        assertEquals(25, person.getAge(), "Age should be '25'");
    }

    /**
     * Test for the getSex() method of the Person class.
     */
    @Test
    public void testGetSex() {
        assertEquals(EGender.MALE, person.getSex(), "Sex should be 'MALE'");
    }

    /**
     * Test for the getKitchen() method of the Person class.
     */
    @Test
    public void testGetKitchen() {
        assertNotNull(person.getKitchen(), "Kitchen should not be null");
    }

    /**
     * Test for the convertToInt() method of the Person class.
     */
    @Test
    public void testConvertToInt() {
        assertEquals(5, Person.convertToInt("5"), "Conversion from string '5' should be 5");
        assertEquals(-1, Person.convertToInt("notAnInteger"), "Conversion from invalid string should return -1");
        assertEquals(5, Person.convertToInt("5.0"), "Conversion from float string '5.0' should be 5");
    }

    /**
     * Test for the setFoodPreference() method of the Person class.
     */
    @Test
    public void testSetFoodPreference() {
        assertEquals(EFoodPreference.MEAT, Person.setFoodPreference("meat"), "Should be 'MEAT'");
        assertEquals(EFoodPreference.VEGAN, Person.setFoodPreference("vegan"), "Should be 'VEGAN'");
        assertEquals(EFoodPreference.VEGGIE, Person.setFoodPreference("veggie"), "Should be 'VEGGIE'");
        assertEquals(EFoodPreference.NONE, Person.setFoodPreference("unknown"), "Should be 'NONE'");
    }

    /**
     * Test for the setHasKitchen() method of the Person class.
     */
    @Test
    public void testSetHasKitchen() {
        assertEquals(EHasKitchen.YES, person.setHasKitchen("yes"), "Should be 'YES'");
        assertEquals(EHasKitchen.MAYBE, person.setHasKitchen("maybe"), "Should be 'MAYBE'");
        assertEquals(EHasKitchen.NO, person.setHasKitchen("no"), "Should be 'NO'");
    }

    /**
     * Test for the setGender() method of the Person class.
     */
    @Test
    public void testSetGender() {
        assertEquals(EGender.MALE, person.setGender("male"), "Should be 'MALE'");
        assertEquals(EGender.FEMALE, person.setGender("female"), "Should be 'FEMALE'");
        assertEquals(EGender.OTHER, person.setGender("other"), "Should be 'OTHER'");
    }

    /**
     * Test for the getAgeRange() method of the Person class.
     */
    @Test
    public void testGetAgeRange() {
        assertEquals(EAgeRange.AgeRange.RANGE_24_27, person.getAgeRange(), "Should be 'RANGE_24_27'");
    }

    /**
     * Test for the isHasCanceledEvent() method of the Person class.
     */
    @Test
    public void testIsHasCanceledEvent() {
        assertFalse(person.isHasCanceledEvent(), "Should not have cancelled event");
        person.setHasCanceledEvent(true);
        assertTrue(person.isHasCanceledEvent(), "Should have cancelled event");
    }

    /**
     * Test for the getUUID() method of the Person class.
     */
    @Test
    public void testGetUUIDNull() {
        assertNotNull(person.getUUID(), "UUID should not be null");
    }

    /**
     * Test for the getName() method of the Person class.
     */
    @Test
    public void testGetNameNull() {
        assertNotNull(person.getName(), "Name should not be null");
    }

    /**
     * Test for the getFoodPreference() method of the Person class.
     */
    @Test
    public void testGetFoodPreferenceNull() {
        assertNotNull(person.getFoodPreference(), "Food preference should not be null");
    }

    /**
     * Test for the getAge() method of the Person class.
     */
    @Test
    public void testGetAgeNull() {
        assertNotNull(person.getAge(), "Age should not be null");
    }

    /**
     * Test for the getSex() method of the Person class.
     */
    @Test
    public void testGetSexNull() {
        assertNotNull(person.getSex(), "Sex should not be null");
    }

    /**
     * Test for the getKitchen() method of the Person class.
     */
    @Test
    public void testGetKitchenNull() {
        assertNotNull(person.getKitchen(), "Kitchen should not be null");
    }

    /**
     * Test for the convertToInt() method of the Person class.
     */
    @Test
    public void testConvertToIntNull() {
        assertNotNull(Person.convertToInt("5"), "Conversion from string '5' should not be null");
    }

    /**
     * Test for the setFoodPreference() method of the Person class.
     */
    @Test
    public void testSetFoodPreferenceNull() {
        assertNotNull(Person.setFoodPreference("meat"), "Food preference 'meat' should not be null");
    }

    /**
     * Test for the setHasKitchen() method of the Person class.
     */
    @Test
    public void testSetHasKitchenNull() {
        assertNotNull(person.setHasKitchen("yes"), "Has kitchen 'yes' should not be null");
    }

    /**
     * Test for the setGender() method of the Person class.
     */
    @Test
    public void testSetGenderNull() {
        assertNotNull(person.setGender("male"), "Gender 'male' should not be null");
    }

    /**
     * Test for the getAgeRange() method of the Person class.
     */
    @Test
    public void testGetAgeRangeNull() {
        assertNotNull(person.getAgeRange(), "Age range should not be null");
    }

    /**
     * Test for the isHasCanceledEvent() method of the Person class.
     */
    @Test
    public void testIsHasCanceledEventNull() {
        assertNotNull(person.isHasCanceledEvent(), "Has canceled event should not be null");
    }
}
