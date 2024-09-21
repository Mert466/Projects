package data;

/**
 * The Coordinate class represents a geographic coordinate with longitude and latitude values.
 */
public class Coordinate {

    private double longitude;

    private double latitude;

    /**
     * Constructs a Coordinate object with the specified longitude and latitude values.
     *
     * @param longitude the longitude value of the coordinate
     * @param latitude  the latitude value of the coordinate
     */
    public Coordinate(double longitude, double latitude) {

        this.longitude = longitude;

        this.latitude = latitude;
    }

    /**
     * Retrieves the longitude value of the coordinate.
     *
     * @return the longitude value of the coordinate
     */
    public double getLongtitude() {

        return longitude;
    }

    /**
     * Retrieves the latitude value of the coordinate.
     *
     * @return the latitude value of the coordinate
     */
    public double getLatitude() {

        return latitude;
    }
}
