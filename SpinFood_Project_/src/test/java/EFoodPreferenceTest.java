import data.EFoodPreference;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The EFoodPreferenceTest class contains tests for the EFoodPreference enum.
 */
public class EFoodPreferenceTest {

    /**
     * Tests the valueOf() method for the MEAT preference.
     */
    @Test
    public void testValueOfMeat() {
        EFoodPreference preference = EFoodPreference.valueOf("MEAT");
        assertEquals(EFoodPreference.MEAT, preference);
        assertEquals(0, preference.getValue());
    }

    /**
     * Tests the valueOf() method for the NONE preference.
     */
    @Test
    public void testValueOfNone() {
        EFoodPreference preference = EFoodPreference.valueOf("NONE");
        assertEquals(EFoodPreference.NONE, preference);
        assertEquals(1, preference.getValue());
    }

    /**
     * Tests the valueOf() method for the VEGAN preference.
     */
    @Test
    public void testValueOfVegan() {
        EFoodPreference preference = EFoodPreference.valueOf("VEGAN");
        assertEquals(EFoodPreference.VEGAN, preference);
        assertEquals(2, preference.getValue());
    }

    /**
     * Tests the valueOf() method for the VEGGIE preference.
     */
    @Test
    public void testValueOfVeggie() {
        EFoodPreference preference = EFoodPreference.valueOf("VEGGIE");
        assertEquals(EFoodPreference.VEGGIE, preference);
        assertEquals(2, preference.getValue());
    }

    /**
     * Tests the valueOf() method for an unknown preference.
     */
    @Test
    public void testInvalidValueOf() {
        assertThrows(IllegalArgumentException.class, () -> EFoodPreference.valueOf("UNKNOWN"));
    }

    /**
     * Tests the order and count of the enum values.
     */
    @Test
    public void testValuesOrderAndCount() {
        EFoodPreference[] values = EFoodPreference.values();
        assertEquals(4, values.length);
        assertEquals(EFoodPreference.MEAT, values[0]);
        assertEquals(EFoodPreference.NONE, values[1]);
        assertEquals(EFoodPreference.VEGAN, values[2]);
        assertEquals(EFoodPreference.VEGGIE, values[3]);
    }

    /**
     * Tests the getValue() method of each preference.
     */
    @Test
    public void testGetValue() {
        assertEquals(0, EFoodPreference.MEAT.getValue());
        assertEquals(1, EFoodPreference.NONE.getValue());
        assertEquals(2, EFoodPreference.VEGAN.getValue());
        assertEquals(2, EFoodPreference.VEGGIE.getValue());
    }
}
