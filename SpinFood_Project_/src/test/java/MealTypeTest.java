import controller.MealType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the MealType enum.
 */
public class MealTypeTest {

    /**
     * Tests the values of the MealType enum.
     */
    @Test
    public void testMealTypeValues() {
        MealType starter = MealType.STARTER;
        MealType main = MealType.MAIN;
        MealType dessert = MealType.DESSERT;

        assertNotNull(starter, "Starter should not be null");
        assertNotNull(main, "Main should not be null");
        assertNotNull(dessert, "Dessert should not be null");
    }

    /**
     * Tests the toString() method of the MealType enum.
     */
    @Test
    public void testMealTypeToString() {
        MealType starter = MealType.STARTER;
        MealType main = MealType.MAIN;
        MealType dessert = MealType.DESSERT;

        assertEquals("STARTER", starter.toString(), "Starter toString() should return 'STARTER'");
        assertEquals("MAIN", main.toString(), "Main toString() should return 'MAIN'");
        assertEquals("DESSERT", dessert.toString(), "Dessert toString() should return 'DESSERT'");
    }
}
