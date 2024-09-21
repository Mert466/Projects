import data.EHasKitchen;
import data.Kitchen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The KitchenTest class contains tests for the Kitchen class.
 */
public class KitchenTest {

    private Kitchen kitchen;

    /**
     * Sets up the test fixture by creating a new Kitchen object.
     */
    @BeforeEach
    public void setUp() {
        kitchen = new Kitchen(EHasKitchen.YES, 1.0, 2.0, 3.0);
    }

    /**
     * Tests the construction of a Kitchen object with the specified parameters.
     */
    @Test
    public void testConstructor() {
        assertEquals(EHasKitchen.YES, kitchen.getHasKitchen(), "Kitchen type should be YES");
        assertEquals(1.0, kitchen.getKitchenStory(), 0.01, "Kitchen story should be 1.0");
        assertEquals(2.0, kitchen.getKitchenLongitude(), 0.01, "Kitchen longitude should be 2.0");
        assertEquals(3.0, kitchen.getkitchenLatitude(), 0.01, "Kitchen latitude should be 3.0");
    }

    /**
     * Tests the retrieval of the kitchen type.
     */
    @Test
    public void testGetHasKitchen() {
        assertEquals(EHasKitchen.YES, kitchen.getHasKitchen(), "Kitchen type should be YES");
    }

    /**
     * Tests the retrieval of the kitchen story level.
     */
    @Test
    public void testGetKitchenStory() {
        assertEquals(1.0, kitchen.getKitchenStory(), 0.01, "Kitchen story should be 1.0");
    }

    /**
     * Tests the retrieval of the kitchen longitude.
     */
    @Test
    public void testGetKitchenLongitude() {
        assertEquals(2.0, kitchen.getKitchenLongitude(), 0.01, "Kitchen longitude should be 2.0");
    }

    /**
     * Tests the retrieval of the kitchen latitude.
     */
    @Test
    public void testGetKitchenLatitude() {
        assertEquals(3.0, kitchen.getkitchenLatitude(), 0.01, "Kitchen latitude should be 3.0");
    }

    /**
     * Tests the Kitchen object for not null property values.
     */
    @Test
    public void testNotNullKitchen() {
        Kitchen kitchen = new Kitchen(EHasKitchen.YES, 1.0, 2.0, 3.0);

        assertNotNull(kitchen.getHasKitchen(), "Kitchen type should not be null");
        assertNotNull(kitchen.getKitchenStory(), "Kitchen story should not be null");
        assertNotNull(kitchen.getKitchenLongitude(), "Kitchen longitude should not be null");
        assertNotNull(kitchen.getkitchenLatitude(), "Kitchen latitude should not be null");
    }

    /**
     * Tests the Kitchen object for null property values.
     */
    @Test
    public void testNullKitchen() {
        Kitchen kitchen = new Kitchen(null, null, null, null);

        assertNull(kitchen.getHasKitchen(), "Kitchen type should be null");
        assertNull(kitchen.getKitchenStory(), "Kitchen story should be null");
        assertNull(kitchen.getKitchenLongitude(), "Kitchen longitude should be null");
        assertNull(kitchen.getkitchenLatitude(), "Kitchen latitude should be null");
    }
}
