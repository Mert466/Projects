import data.EAgeRange;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the EAgeRange class.
 */
public class EAgeRangeTest {

    /**
     * Tests the getValue() method of the AgeRange enum.
     */
    @Test
    public void testAgeRangeValues() {
        // Test each age range value
        assertEquals(0, EAgeRange.AgeRange.RANGE_0_17.getValue(), "RANGE_0_17 should have value 0");
        assertEquals(1, EAgeRange.AgeRange.RANGE_18_23.getValue(), "RANGE_18_23 should have value 1");
        assertEquals(2, EAgeRange.AgeRange.RANGE_24_27.getValue(), "RANGE_24_27 should have value 2");
        assertEquals(3, EAgeRange.AgeRange.RANGE_28_30.getValue(), "RANGE_28_30 should have value 3");
        assertEquals(4, EAgeRange.AgeRange.RANGE_31_35.getValue(), "RANGE_31_35 should have value 4");
        assertEquals(5, EAgeRange.AgeRange.RANGE_36_41.getValue(), "RANGE_36_41 should have value 5");
        assertEquals(6, EAgeRange.AgeRange.RANGE_42_46.getValue(), "RANGE_42_46 should have value 6");
        assertEquals(7, EAgeRange.AgeRange.RANGE_47_56.getValue(), "RANGE_47_56 should have value 7");
        assertEquals(8, EAgeRange.AgeRange.RANGE_57_OLDER.getValue(), "RANGE_57_OLDER should have value 8");
    }

    /**
     * Tests the toString() method of the AgeRange enum.
     */
    @Test
    public void testAgeRangeToString() {
        // Test string representation of each age range
        assertEquals("RANGE_0_17", EAgeRange.AgeRange.RANGE_0_17.toString(), "RANGE_0_17 should have the correct string representation");
        assertEquals("RANGE_18_23", EAgeRange.AgeRange.RANGE_18_23.toString(), "RANGE_18_23 should have the correct string representation");
        assertEquals("RANGE_24_27", EAgeRange.AgeRange.RANGE_24_27.toString(), "RANGE_24_27 should have the correct string representation");
        assertEquals("RANGE_28_30", EAgeRange.AgeRange.RANGE_28_30.toString(), "RANGE_28_30 should have the correct string representation");
        assertEquals("RANGE_31_35", EAgeRange.AgeRange.RANGE_31_35.toString(), "RANGE_31_35 should have the correct string representation");
        assertEquals("RANGE_36_41", EAgeRange.AgeRange.RANGE_36_41.toString(), "RANGE_36_41 should have the correct string representation");
        assertEquals("RANGE_42_46", EAgeRange.AgeRange.RANGE_42_46.toString(), "RANGE_42_46 should have the correct string representation");
        assertEquals("RANGE_47_56", EAgeRange.AgeRange.RANGE_47_56.toString(), "RANGE_47_56 should have the correct string representation");
        assertEquals("RANGE_57_OLDER", EAgeRange.AgeRange.RANGE_57_OLDER.toString(), "RANGE_57_OLDER should have the correct string representation");
    }

    /**
     * Tests that instances of each age range are not null.
     */
    @Test
    public void testAgeRangeInstanceNotNull() {
        // Test that each age range instance is not null
        assertNotNull(EAgeRange.AgeRange.RANGE_0_17, "RANGE_0_17 should not be null");
        assertNotNull(EAgeRange.AgeRange.RANGE_18_23, "RANGE_18_23 should not be null");
        assertNotNull(EAgeRange.AgeRange.RANGE_24_27, "RANGE_24_27 should not be null");
        assertNotNull(EAgeRange.AgeRange.RANGE_28_30, "RANGE_28_30 should not be null");
        assertNotNull(EAgeRange.AgeRange.RANGE_31_35, "RANGE_31_35 should not be null");
        assertNotNull(EAgeRange.AgeRange.RANGE_36_41, "RANGE_36_41 should not be null");
        assertNotNull(EAgeRange.AgeRange.RANGE_42_46, "RANGE_42_46 should not be null");
        assertNotNull(EAgeRange.AgeRange.RANGE_47_56, "RANGE_47_56 should not be null");
        assertNotNull(EAgeRange.AgeRange.RANGE_57_OLDER, "RANGE_57_OLDER should not be null");
    }

    /**
     * Tests that the age ranges are ordered correctly.
     */
    @Test
    public void testAgeRangeOrder() {
        // Get the array of age ranges
        EAgeRange.AgeRange[] ageRanges = EAgeRange.AgeRange.values();

        // Check that each age range is greater than the previous one by 1
        for (int i = 0; i < ageRanges.length - 1; i++) {
            int currentValue = ageRanges[i].getValue();
            int nextValue = ageRanges[i + 1].getValue();

            assertEquals(currentValue + 1, nextValue, "The age ranges should be ordered correctly");
        }
    }

    /**
     * Tests the equality of age ranges.
     */
    @Test
    public void testAgeRangeEquality() {
        // Create age range instances for testing
        EAgeRange.AgeRange range1 = EAgeRange.AgeRange.RANGE_24_27;
        EAgeRange.AgeRange range2 = EAgeRange.AgeRange.RANGE_24_27;
        EAgeRange.AgeRange range3 = EAgeRange.AgeRange.RANGE_31_35;

        // Test reference equality
        assertTrue(range1 == range2, "Same age ranges should be equal using reference comparison");

        // Test equality using equals() method
        assertTrue(range1.equals(range2), "Same age ranges should be equal using equals() method");

        // Test inequality
        assertFalse(range1.equals(range3), "Different age ranges should not be equal");
    }
}
