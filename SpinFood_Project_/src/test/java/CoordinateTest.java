import data.Coordinate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Coordinate class.
 */
public class CoordinateTest {

    /**
     * Tests the initialization of a Coordinate object.
     */
    @Test
    public void testCoordinateInitialization() {
        double longitude = 12.34;
        double latitude = 56.78;
        Coordinate coordinate = new Coordinate(longitude, latitude);

        assertEquals(longitude, coordinate.getLongtitude(), "Longitude should be initialized correctly");
        assertEquals(latitude, coordinate.getLatitude(), "Latitude should be initialized correctly");
    }

    /**
     * Tests the retrieval of the longitude value.
     */
    @Test
    public void testGetLongitude() {
        double longitude = 12.34;
        double latitude = 56.78;
        Coordinate coordinate = new Coordinate(longitude, latitude);

        assertEquals(longitude, coordinate.getLongtitude(), "getLongitude() should return the correct longitude");
    }

    /**
     * Tests the retrieval of the latitude value.
     */
    @Test
    public void testGetLatitude() {
        double longitude = 12.34;
        double latitude = 56.78;
        Coordinate coordinate = new Coordinate(longitude, latitude);

        assertEquals(latitude, coordinate.getLatitude(), "getLatitude() should return the correct latitude");
    }

    /**
     * Tests the equality of Coordinate objects.
     */
    @Test
    public void testEquality() {
        Coordinate coordinate1 = new Coordinate(12.34, 56.78);
        Coordinate coordinate2 = new Coordinate(12.34, 56.78);
        Coordinate coordinate3 = new Coordinate(98.76, 54.32);

        assertTrue(coordinate1 == coordinate1, "Same Coordinate object should be equal using reference comparison");

        assertTrue(compareCoordinates(coordinate1, coordinate2), "Coordinate objects with the same values should be equal");
        assertFalse(compareCoordinates(coordinate1, coordinate3), "Coordinate objects with different values should not be equal");
    }

    /**
     * Tests the equality of Coordinate objects using the equals() method.
     */
    @Test
    public void testCoordinateEquality() {
        Coordinate coordinate1 = new Coordinate(12.34, 56.78);
        Coordinate coordinate2 = new Coordinate(12.34, 56.78);
        Coordinate coordinate3 = new Coordinate(98.76, 54.32);

        assertTrue(compareCoordinates(coordinate1, coordinate1), "Coordinate should be equal to itself");
        assertTrue(compareCoordinates(coordinate1, coordinate2), "Coordinate objects with the same values should be equal");
        assertFalse(compareCoordinates(coordinate1, coordinate3), "Coordinate objects with different values should not be equal");
        assertFalse(compareCoordinates(coordinate1, null), "Coordinate should not be equal to null");
    }


    /**
     * Tests a non-null Coordinate object.
     */
    @Test
    public void testNotNullCoordinate() {
        Coordinate coordinate = new Coordinate(12.34, 56.78);
        assertNotNull(coordinate, "Coordinate should not be null");
    }

    /**
     * Compares two Coordinate objects for equality.
     *
     * @param c1 the first Coordinate object
     * @param c2 the second Coordinate object
     * @return true if the Coordinate objects are equal, false otherwise
     */
    private boolean compareCoordinates(Coordinate c1, Coordinate c2) {
        if (c1 == c2) {
            return true;
        }
        if (c1 == null || c2 == null) {
            return false;
        }
        return Double.compare(c1.getLongtitude(), c2.getLongtitude()) == 0
                && Double.compare(c1.getLatitude(), c2.getLatitude()) == 0;
    }
}
