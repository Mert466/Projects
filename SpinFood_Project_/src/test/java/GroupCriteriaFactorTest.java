import controller.GroupCriteriaFactor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The GroupCriteriaFactorTest class contains JUnit tests for the GroupCriteriaFactor class.
 */
public class GroupCriteriaFactorTest {

    private GroupCriteriaFactor groupCriteriaFactor;

    /**
     * Sets up the GroupCriteriaFactor object before each test.
     */
    @BeforeEach
    public void setup() {
        groupCriteriaFactor = new GroupCriteriaFactor();
    }

    /**
     * Tests that the default criteria values are set correctly.
     */
    @Test
    public void testDefaultCriteriaValues() {
        assertEquals(1, groupCriteriaFactor.getFoodPreferenceCriteria());
        assertEquals(1, groupCriteriaFactor.getGenderDiversityCriteria());
    }

    /**
     * Tests the setFoodPreferenceCriteria method with valid and invalid values.
     */
    @Test
    public void testSetFoodPreferenceCriteria() {
        groupCriteriaFactor.setFoodPreferenceCriteria(3);
        assertEquals(3, groupCriteriaFactor.getFoodPreferenceCriteria());

        groupCriteriaFactor.setFoodPreferenceCriteria(0);
        assertEquals(3, groupCriteriaFactor.getFoodPreferenceCriteria());

        groupCriteriaFactor.setFoodPreferenceCriteria(-3);
        assertEquals(3, groupCriteriaFactor.getFoodPreferenceCriteria());
    }

    /**
     * Tests the setGenderDiversityCriteria method with valid and invalid values.
     */
    @Test
    public void testSetGenderDiversityCriteria() {
        groupCriteriaFactor.setGenderDiversityCriteria(2);
        assertEquals(2, groupCriteriaFactor.getGenderDiversityCriteria());

        groupCriteriaFactor.setGenderDiversityCriteria(0);
        assertEquals(2, groupCriteriaFactor.getGenderDiversityCriteria());

        groupCriteriaFactor.setGenderDiversityCriteria(-2);
        assertEquals(2, groupCriteriaFactor.getGenderDiversityCriteria());
    }
}
