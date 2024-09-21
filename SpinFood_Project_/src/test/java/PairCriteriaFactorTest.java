import controller.PairCriteriaFactor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The PairCriteriaFactorTest class contains JUnit tests for the PairCriteriaFactor class.
 */
public class PairCriteriaFactorTest {

    private PairCriteriaFactor pairCriteriaFactor;

    /**
     * Sets up the PairCriteriaFactor object before each test.
     */
    @BeforeEach
    public void setup() {
        pairCriteriaFactor = new PairCriteriaFactor();
    }

    /**
     * Tests that the default criteria values are set correctly.
     */
    @Test
    public void testDefaultCriteriaValues() {
        assertEquals(1, pairCriteriaFactor.getFoodPreferenceCriteria());
        assertEquals(1, pairCriteriaFactor.getAgeDifferenceCriteria());
        assertEquals(1, pairCriteriaFactor.getGenderDifferenceCriteria());
    }

    /**
     * Tests the setAgeDifferenceCriteria method with valid and invalid values.
     */
    @Test
    public void testSetAgeDifferenceCriteria() {
        pairCriteriaFactor.setAgeDifferenceCriteria(5);
        assertEquals(5, pairCriteriaFactor.getAgeDifferenceCriteria());

        pairCriteriaFactor.setAgeDifferenceCriteria(0);
        assertEquals(5, pairCriteriaFactor.getAgeDifferenceCriteria());

        pairCriteriaFactor.setAgeDifferenceCriteria(-5);
        assertEquals(5, pairCriteriaFactor.getAgeDifferenceCriteria());
    }

    /**
     * Tests the setGenderDifferenceCriteria method with valid and invalid values.
     */
    @Test
    public void testSetGenderDifferenceCriteria() {
        pairCriteriaFactor.setGenderDifferenceCriteria(3);
        assertEquals(3, pairCriteriaFactor.getGenderDifferenceCriteria());

        pairCriteriaFactor.setGenderDifferenceCriteria(0);
        assertEquals(3, pairCriteriaFactor.getGenderDifferenceCriteria());

        pairCriteriaFactor.setGenderDifferenceCriteria(-3);
        assertEquals(3, pairCriteriaFactor.getGenderDifferenceCriteria());
    }

    /**
     * Tests the setFoodPreferenceCriteria method with valid and invalid values.
     */
    @Test
    public void testSetFoodPreferenceCriteria() {
        pairCriteriaFactor.setFoodPreferenceCriteria(2);
        assertEquals(2, pairCriteriaFactor.getFoodPreferenceCriteria());

        pairCriteriaFactor.setFoodPreferenceCriteria(0);
        assertEquals(2, pairCriteriaFactor.getFoodPreferenceCriteria());

        pairCriteriaFactor.setFoodPreferenceCriteria(-2);
        assertEquals(2, pairCriteriaFactor.getFoodPreferenceCriteria());
    }
}
