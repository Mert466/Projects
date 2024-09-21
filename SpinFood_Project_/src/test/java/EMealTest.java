import data.EMeal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the EMeal enum.
 */
public class EMealTest {

    /**
     * Tests the values of the EMeal enum.
     */
    @Test
    public void testEMealValues() {
        EMeal starter = EMeal.STARTER;
        EMeal main = EMeal.MAIN;
        EMeal dessert = EMeal.DESSERT;

        assertNotNull(starter, "Starter should not be null");
        assertNotNull(main, "Main should not be null");
        assertNotNull(dessert, "Dessert should not be null");
    }

    /**
     * Tests the toString() method of the EMeal enum.
     */
    @Test
    public void testEMealToString() {
        EMeal starter = EMeal.STARTER;
        EMeal main = EMeal.MAIN;
        EMeal dessert = EMeal.DESSERT;

        assertEquals("STARTER", starter.toString(), "Starter toString() should return 'STARTER'");
        assertEquals("MAIN", main.toString(), "Main toString() should return 'MAIN'");
        assertEquals("DESSERT", dessert.toString(), "Dessert toString() should return 'DESSERT'");
    }
}
