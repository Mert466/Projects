package data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The data.Person class represents a person with their associated data.
 */
public class Person {
    @Expose
    @SerializedName("id")
    private String uuid;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("foodPreference")
    private EFoodPreference foodPreference;
    @Expose
    @SerializedName("age")
    private int age;
    @Expose
    @SerializedName("gender")
    private EGender sex;
    @Expose
    @SerializedName("kitchen")
    private Kitchen kitchen;
    private Boolean available;
    private boolean hasCanceledEvent;

    /**
     * Constructs a data.Person object using an array of string data.
     *
     * @param data An array of string data containing information about a person
     */
    public Person(String[] data) {
        this.uuid = data[1];
        this.name = data[2];
        this.foodPreference = setFoodPreference(data[3]);
        this.age = convertToInt(data[4]);
        this.sex = setGender(data[5]);
        this.available = true;
        this.kitchen = new Kitchen(setHasKitchen(data[6]), data.length > 7 && !data[7].isEmpty() ? Double.parseDouble(data[7]) : null,
                data.length > 8 && !data[8].isEmpty() ? Double.parseDouble(data[8]) : null, data.length > 9 && !data[9].isEmpty() ? Double.parseDouble(data[9]) : null);
    }

    /**
     * Checks if the person is available.
     *
     * @return true if the person is available, false otherwise.
     */
    public boolean isAvailable(){
        return available;
    }

    /**
     * Sets the availability status of the person.
     *
     * @param available the availability status of the person, true if available, false otherwise
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * @return the person's UUID
     */
    public String getUUID() {
        return uuid;
    }

    /**
     * @return the person's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the person's food preference
     */
    public EFoodPreference getFoodPreference() {
        return foodPreference;
    }

    /**
     * @return the person's age
     */
    public int getAge() {
        return age;
    }

    /**
     * @return the person's sex
     */
    public EGender getSex() {
        return sex;
    }

    /**
     * @return the person's kitchen information
     */
    public Kitchen getKitchen() {
        return kitchen;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    /**
     * Gets float or int as a string and transforms it always into an integer
     *
     * @param str float or int as a String
     * @return transformed value from type int
     */
    public static int convertToInt(String str) {

        try {

            return Integer.parseInt(str);

        } catch (NumberFormatException e) {

            try {

                return (int) Float.parseFloat(str);
            } catch (NumberFormatException ex) {

                return -1;
            }
        }
    }

    /**
     * Gets foodpreference as a string and transforms it into an enum
     *
     * @param foodPreference foodPreference as a string
     * @return foodPreference as type data.EFoodPreference
     */
    public static EFoodPreference setFoodPreference(String foodPreference) {

        EFoodPreference preference = EFoodPreference.NONE;

        switch(foodPreference) {
            case "meat":
                preference = EFoodPreference.MEAT;
                break;
            case "veggie":
                preference = EFoodPreference.VEGGIE;
                break;
            case "vegan":
                preference = EFoodPreference.VEGAN;
                break;
        }

        return preference;
    }

    /**
     * Gets hasKitchen as a string and transforms it into an enum
     *
     * @param hasKitchen as a string
     * @return hasKitchen as type data.EHasKitchen
     */
    public EHasKitchen setHasKitchen(String hasKitchen) {

        EHasKitchen hasKitchenType = EHasKitchen.NO;

        switch(hasKitchen) {
            case "yes":
                hasKitchenType = EHasKitchen.YES;
                break;
            case "maybe":
                hasKitchenType = EHasKitchen.MAYBE;
                break;
        }

        return hasKitchenType;
    }

    /**
     * Gets Gender as a string and transforms it into an enum
     *
     * @param gender as a string
     * @return gender as type data.EGender
     */
    public EGender setGender(String gender) {

        EGender genderType = EGender.OTHER;

        switch(gender) {
            case "male":
                genderType = EGender.MALE;
                break;
            case "female":
                genderType = EGender.FEMALE;
                break;
        }

        return genderType;
    }

    /**
     * Retrieves the age range of the person based on their age.
     *
     * @return the age range of the person
     */
    public EAgeRange.AgeRange getAgeRange() {
        int age = getAge();
        if (age <= 17) {
            return EAgeRange.AgeRange.RANGE_0_17;
        } else if (age <= 23) {
            return EAgeRange.AgeRange.RANGE_18_23;
        } else if (age <= 27) {
            return EAgeRange.AgeRange.RANGE_24_27;
        } else if (age <= 30) {
            return EAgeRange.AgeRange.RANGE_28_30;
        } else if (age <= 35) {
            return EAgeRange.AgeRange.RANGE_31_35;
        } else if (age <= 41) {
            return EAgeRange.AgeRange.RANGE_36_41;
        } else if (age <= 46) {
            return EAgeRange.AgeRange.RANGE_42_46;
        } else if (age <= 56) {
            return EAgeRange.AgeRange.RANGE_47_56;
        } else {
            return EAgeRange.AgeRange.RANGE_57_OLDER;
        }
    }

    /**
     * Checks if the person has canceled the event.
     *
     * @return true if the person has canceled the event, false otherwise
     */
    public boolean isHasCanceledEvent() {
        return hasCanceledEvent;
    }

    /**
     * Sets the cancellation status of the person for the event.
     *
     * @param hasCanceledEvent the cancellation status of the person, true if canceled, false otherwise
     */
    public void setHasCanceledEvent(boolean hasCanceledEvent) {

        this.hasCanceledEvent = hasCanceledEvent;
    }

    public String toString() {

        String foodPreference = "";
        String hasKitchen = "";

        switch(this.foodPreference) {
            case MEAT -> foodPreference = "Fleisch";
            case VEGAN -> foodPreference = "Vegan";
            case NONE -> foodPreference = "Keine";
            case VEGGIE -> foodPreference = "Vegetarisch";
        }

        switch(this.getKitchen().getHasKitchen()) {
            case YES -> hasKitchen = "Ja";
            case NO -> hasKitchen = "Nein";
            case MAYBE -> hasKitchen = "Vielleicht";
        }

        return this.name + " | Essensvorliebe: " + foodPreference + " | Küche: " + hasKitchen;
    }
}