
package data;
import data.Coordinate;
import data.Person;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Kitchen class represents a kitchen.
 */
public class Kitchen {

    @Expose
    @SerializedName("emergencyKitchen")
    public EHasKitchen kitchen;
    @Expose
    @SerializedName("story")
    private Double kitchenStory;
    @Expose
    @SerializedName("longitude")
    private Double kitchenLongitude;
    @Expose
    @SerializedName("latitude")
    private Double kitchenLatitude;

    /**
     * Constructs a Kitchen object with the specified parameters.
     *
     * @param kitchen          the type of kitchen (EHasKitchen)
     * @param kitchenStory     the story level of the kitchen
     * @param kitchenLongitude the longitude coordinate of the kitchen
     * @param kitchenLatitude  the latitude coordinate of the kitchen
     */
    public Kitchen(EHasKitchen kitchen, Double kitchenStory, Double kitchenLongitude, Double kitchenLatitude) {

        this.kitchen = kitchen;
        this.kitchenStory = kitchenStory;
        this.kitchenLongitude = kitchenLongitude;
        this.kitchenLatitude = kitchenLatitude;
    }

    /**
     * Retrieves the type of kitchen.
     *
     * @return the type of kitchen (EHasKitchen)
     */
    public EHasKitchen getHasKitchen() {
        return kitchen;
    }

    /**
     * Retrieves the story level of the kitchen.
     *
     * @return the story level of the kitchen
     */
    public Double getKitchenStory() {

        return kitchenStory;
    }

    /**
     * Retrieves the longitude coordinate of the kitchen.
     *
     * @return the longitude coordinate of the kitchen
     */
    public Double getKitchenLongitude() {

        return kitchenLongitude;
    }

    /**
     * Retrieves the latitude coordinate of the kitchen.
     *
     * @return the latitude coordinate of the kitchen
     */
    public Double getkitchenLatitude() {

        return kitchenLatitude;
    }


}
