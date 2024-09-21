package controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import controller.MealType;
import data.EFoodPreference;
import data.EMeal;
import data.Kitchen;
import data.Pair;

import java.util.ArrayList;
import java.util.List;

import static java.lang.CharSequence.compare;

/**
 * The Group class represents a group of pairs for a specific meal.
 */
public class Group implements Comparable<Group> {

    @Expose
    @SerializedName("course")
    private MealType mealType;

    @Expose
    @SerializedName("foodType")
    public EFoodPreference foodPreference;

    @Expose
    @SerializedName("kitchen")
    private Kitchen kitchen;

    @Expose
    @SerializedName("cookingPair")
    private Pair pair1;

    @Expose
    @SerializedName("secondPair")
    private Pair pair2;

    @Expose
    @SerializedName("thirdPair")
    private Pair pair3;

    private String cookID;

    private String groupID;

    /**
     * Constructs a Group object with the specified pairs and meal type.
     *
     * @param pair1    the first pair in the group
     * @param pair2    the second pair in the group
     * @param pair3    the third pair in the group
     * @param mealType the meal type of the group
     */
    public Group(Pair pair1, Pair pair2, Pair pair3, MealType mealType) {

        this.pair1 = pair1;

        this.pair2 = pair2;

        this.pair3 = pair3;

        this.groupID = pair1.getPairID() + "/" + pair2.getPairID() + "/" + pair3.getPairID();

        this.mealType = mealType;

        this.foodPreference = setFoodPreference(pair1, pair2, pair3);
    }

    /**
     * Retrieves the pairs in the group.
     *
     * @return a list of pairs in the group
     */
    public List<Pair> getPairs() {

        return List.of(pair1, pair2, pair3);
    }

    /**
     * Retrieves the group ID.
     *
     * @return the group ID
     */
    public String getGroupID() {
        return groupID;
    }

    /**
     * Retrieves the meal type of the group.
     *
     * @return the meal type of the group
     */
    public MealType getMealType() {
        return mealType;
    }

    /**
     * Sets the first pair in the group.
     *
     * @param pair1 the first pair in the group
     */
    public void setPair1(Pair pair1) {

        this.pair1 = pair1;
    }

    /**
     * Sets the second pair in the group.
     *
     * @param pair2 the second pair in the group
     */
    public void setPair2(Pair pair2) {
        this.pair2 = pair2;
    }

    /**
     * Sets the third pair in the group.
     *
     * @param pair3 the third pair in the group
     */
    public void setPair3(Pair pair3) {

        this.pair3 = pair3;
    }

    /**
     * Retrieves the cook ID associated with the group.
     *
     * @return the cook ID associated with the group
     */
    public String getCookID() {
        return cookID;
    }

    /**
     * Sets the cook ID associated with the group.
     *
     * @param cookID the cook ID to be set
     */
    public void setCookID(String cookID) {
        this.cookID = cookID;
    }

    /**
     * Creates a new group ID based on the specified pairs.
     *
     * @param one   the first pair
     * @param two   the second pair
     * @param three the third pair
     */
    public void createNewGroupID(Pair one, Pair two, Pair three) {

        this.groupID = one.getPairID() + "/" + two.getPairID() + "/" + three.getPairID();
    }

    /**
     * Retrieves the first pair in the group.
     *
     * @return the first pair in the group
     */
    public Pair getPair1() {
        return pair1;
    }

    /**
     * Retrieves the second pair in the group.
     *
     * @return the second pair in the group
     */
    public Pair getPair2() {
        return pair2;
    }

    /**
     * Retrieves the third pair in the group.
     *
     * @return the third pair in the group
     */
    public Pair getPair3() {
        return pair3;
    }

    /**
     * Retrieves the food preference of the group.
     *
     * @return the food preference of the group
     */
    public EFoodPreference getFoodPreference() {

        return this.foodPreference;
    }

    public EFoodPreference setFoodPreference(Pair one, Pair two, Pair three) {

        if(one.getFoodPreference() == EFoodPreference.NONE && two.getFoodPreference() == EFoodPreference.NONE && three.getFoodPreference() == EFoodPreference.NONE) {
            return EFoodPreference.MEAT;
        }
        else {
            return one.getFoodPreference();
        }
    }

    public void setGroupKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    @Override
    public int compareTo(Group o) {
        return compare(getPair1().getPerson1().getName(), o.getPair1().getPerson1().getName());
    }
}