package view;
/**
 * Represents the data for creating an event.
 * The class holds various factors and settings related to the event creation process.
 */
public class CreateEventData {

    private int foodPreferenceFactor;

    private int ageDifferenceFactor;

    private int genderDiversityFactor;

    private int maxParticipants;

    private String selectedFile;

    /**
     * Constructs a new CreateEventData object with the specified factors and settings.
     *
     * @param foodPreferenceFactor    the factor for food preference
     * @param ageDifferenceFactor     the factor for age difference
     * @param genderDiversityFactor   the factor for gender diversity
     * @param maxParticipants         the maximum number of participants allowed
     * @param selectedFile            the selected file associated with the event
     */

    public CreateEventData(int foodPreferenceFactor, int ageDifferenceFactor, int genderDiversityFactor, int maxParticipants, String selectedFile) {
        this.foodPreferenceFactor = foodPreferenceFactor;
        this.ageDifferenceFactor = ageDifferenceFactor;
        this.genderDiversityFactor = genderDiversityFactor;
        this.maxParticipants = maxParticipants;
        this.selectedFile = selectedFile;
    }

    public int getFoodPreferenceFactor() {
        return foodPreferenceFactor;
    }

    public int getAgeDifferenceFactor() {
        return ageDifferenceFactor;
    }

    public int getGenderDiversityFactor() {
        return genderDiversityFactor;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public String getSelectedFile() {
        return selectedFile;
    }
}
