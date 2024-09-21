import data.EGender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the EGender enum.
 */
public class EGenderTest {

    /**
     * Tests the values of the EGender enum.
     */
    @Test
    public void testEGenderValues() {
        EGender male = EGender.MALE;
        EGender female = EGender.FEMALE;
        EGender other = EGender.OTHER;

        assertNotNull(male, "MALE should not be null");
        assertNotNull(female, "FEMALE should not be null");
        assertNotNull(other, "OTHER should not be null");
    }

    /**
     * Tests the toString() method of the EGender enum.
     */
    @Test
    public void testEGenderToString() {
        EGender male = EGender.MALE;
        EGender female = EGender.FEMALE;
        EGender other = EGender.OTHER;

        assertEquals("MALE", male.toString(), "MALE toString() should return 'MALE'");
        assertEquals("FEMALE", female.toString(), "FEMALE toString() should return 'FEMALE'");
        assertEquals("OTHER", other.toString(), "OTHER toString() should return 'OTHER'");
    }
}
