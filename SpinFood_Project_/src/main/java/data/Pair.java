package data;

import data.Coordinate;
import data.Person;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static java.lang.CharSequence.compare;

/**
 * The data.Pair class represents a data.Pair with their associated data.
 */
public class Pair implements Comparable<Pair> {

    private String pairID;
    @Expose
    @SerializedName("premade")
    private boolean signedInTogether;
    @Expose
    @SerializedName("foodPreference")
    private EFoodPreference foodPreference;

    private Kitchen kitchen;

    @Expose
    @SerializedName("firstParticipant")
    private Person person1;
    @Expose
    @SerializedName("secondParticipant")
    private Person person2;
    private int foodPreferenceDifference;

    private boolean hasCooked;

    private boolean availability = true;

    private double pathLength;

    /**
     * Constructs a data.Pair object using two objects of type person and their food preference.
     *
     * @param person1 A data.Person object containing all information about a person
     * @param person2 A data.Person object containing all information about a person
     */

    public Pair(Person person1, Person person2) {

        this.person1 = person1;

        this.person2 = person2;

        this.checkPairFoodPreference();

        this.createPairID(person1.getUUID(), person2.getUUID());
    }

    /**
     * Constructs a Pair object using two Person objects and a food preference.
     *
     * @param person1         the first Person object
     * @param person2         the second Person object
     * @param foodPreference  the food preference of the pair
     */
    public Pair(Person person1, Person person2, EFoodPreference foodPreference) {

        this.person1 = person1;

        this.person2 = person2;

        this.foodPreference = foodPreference;

        this.createPairID(person1.getUUID(), person2.getUUID());
    }

    /**
     * Checks the food preferences of the two persons in the pair and determines the overall food preference for the pair.
     * If both persons have the same food preference, the pair's food preference will be set accordingly.
     * If one person is a vegetarian and the other is a meat-eater, the pair's food preference will be set as meat.
     * If one person is a vegetarian and the other is a vegan, the pair's food preference will be set as vegan.
     * If one person is a meat-eater and the other is a vegan, the pair's food preference will be set as vegan.
     * If one person does not have any food preference, the pair's food preference will be set based on the other person's preference.
     */
    private void checkPairFoodPreference() {


        if(this.person1.getFoodPreference() == this.person2.getFoodPreference()) {

            this.foodPreference = person1.getFoodPreference();
        }
        else if((this.person1.getFoodPreference() == EFoodPreference.NONE) && (this.person2.getFoodPreference() == EFoodPreference.MEAT) ||
                (this.person1.getFoodPreference() == EFoodPreference.MEAT) && (this.person2.getFoodPreference() == EFoodPreference.NONE)) {
            this.foodPreference = EFoodPreference.MEAT;
        }
        else if((this.person1.getFoodPreference() == EFoodPreference.MEAT) && (this.person2.getFoodPreference() == EFoodPreference.VEGGIE) ||
                (this.person1.getFoodPreference() == EFoodPreference.VEGGIE) && (this.person2.getFoodPreference() == EFoodPreference.MEAT)) {
            this.foodPreference = EFoodPreference.VEGGIE;
        }
        else if((this.person1.getFoodPreference() == EFoodPreference.NONE) && (this.person2.getFoodPreference() == EFoodPreference.VEGGIE) ||
                (this.person1.getFoodPreference() == EFoodPreference.VEGGIE) && (this.person2.getFoodPreference() == EFoodPreference.NONE)) {
            this.foodPreference = EFoodPreference.VEGGIE;
        }
        else if((this.person1.getFoodPreference() == EFoodPreference.MEAT) && (this.person2.getFoodPreference() == EFoodPreference.VEGAN) ||
                (this.person1.getFoodPreference() == EFoodPreference.VEGAN) && (this.person2.getFoodPreference() == EFoodPreference.MEAT)) {
            this.foodPreference = EFoodPreference.VEGAN;
        }
        else if((this.person1.getFoodPreference() == EFoodPreference.NONE) && (this.person2.getFoodPreference() == EFoodPreference.VEGAN) ||
                (this.person1.getFoodPreference() == EFoodPreference.VEGAN) && (this.person2.getFoodPreference() == EFoodPreference.NONE)) {
            this.foodPreference = EFoodPreference.VEGAN;
        }
        else if((this.person1.getFoodPreference() == EFoodPreference.VEGGIE) && (this.person2.getFoodPreference() == EFoodPreference.VEGAN) ||
                (this.person1.getFoodPreference() == EFoodPreference.VEGAN) && (this.person2.getFoodPreference() == EFoodPreference.VEGGIE)) {
            this.foodPreference = EFoodPreference.VEGAN;
        }
    }

    /**
     * Retrieves the food preference of the pair.
     *
     * @return the food preference of the pair
     */
    public EFoodPreference getFoodPreference() {
        return this.foodPreference;
    }

    /**
     * Retrieves the food preference difference of the pair.
     *
     * @return the food preference difference of the pair
     */
    public int getFoodPreferenceDifference() {
        return this.foodPreferenceDifference;
    }

    /**
     * Sets the food preference difference of the pair.
     *
     * @param difference the food preference difference to set
     */
    public void setFoodPreferenceDifference(int difference) {
        this.foodPreferenceDifference = difference;
    }

    /**
     * Creates a pair ID using the UUIDs of the two persons.
     *
     * @param id1 the UUID of the first person
     * @param id2 the UUID of the second person
     */
    public void createPairID(String id1, String id2) {

        this.pairID = id1 + "/" +  id2;
    }

    /**
     * Checks if the pair is signed in together.
     *
     * @return true if the pair is signed in together, false otherwise
     */
    public boolean isSignedInTogether() {

        return signedInTogether;
    }

    /**
     * Sets the signed-in together status of the pair.
     *
     * @param signedInTogether the signed-in together status to set
     */
    public void setSignedInTogether(boolean signedInTogether) {

        this.signedInTogether = signedInTogether;
    }

    /**
     * Retrieves the first person in the pair.
     *
     * @return the first person in the pair
     */
    public Person getPerson1() {

        return this.person1;
    }

    /**
     * Retrieves the second person in the pair.
     *
     * @return the second person in the pair
     */
    public Person getPerson2() {

        return this.person2;
    }

    /**
     * Retrieves the pair ID.
     *
     * @return the pair ID
     */
    public String getPairID() {

        return this.pairID;
    }

    /**
     * Retrieves the food preference of the pair.
     *
     * @return the food preference of the pair
     */
    public EFoodPreference getPairFoodPreference() {

        return this.foodPreference;
    }


    /**
     * Checks if the pair has cooked.
     *
     * @return true if the pair has cooked, false otherwise
     */
    public boolean isHasCooked() {
        return hasCooked;
    }

    /**
     * Sets the cooked status of the pair.
     *
     * @param hasCooked the cooked status to set
     */
    public void setHasCooked(boolean hasCooked) {
        this.hasCooked = hasCooked;
    }

    /**
     * Checks the availability of the pair.
     *
     * @return true if the pair is available, false otherwise
     */
    public boolean getAvailability() {
        return availability;
    }

    /**
     * Sets the availability status of the pair.
     *
     * @param availability the availability status to set
     */
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public double getPathLength() {
        return pathLength;
    }

    public void setPathLength(double pathLength) {
        this.pathLength = pathLength;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public String toString() {
        return this.person1.getName() + " & " + this.person2.getName();
    }

    @Override
    public int compareTo(Pair o) {
        return compare(this.getPerson1().getName(), o.getPerson1().getName());
    }
}
