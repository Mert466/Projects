package data;

/**
 * The EFoodPreference enum represents the food preferences.
 */
public enum EFoodPreference {
    MEAT(0),
    NONE(1),
    VEGAN(2),
    VEGGIE(2);

    private int value;

    /**
     * Constructs an EFoodPreference enum constant with the specified value.
     *
     * @param value the value associated with the food preference
     */
    private EFoodPreference(int value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the food preference.
     *
     * @return the value associated with the food preference
     */
    public int getValue() {
        return value;
    }
}

