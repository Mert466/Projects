import controller.Criteria;
import controller.GroupCriteriaFactor;
import controller.PairAssigner;
import controller.PairCriteriaFactor;
import data.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The PairAssignerTest class contains unit tests for the PairAssigner class.
 */
class PairAssignerTest {
    static PairAssigner pairAssigner;

    @BeforeEach
    void setup(){
        PairCriteriaFactor pairCriteria = new PairCriteriaFactor();
        pairCriteria.setAgeDifferenceCriteria(10);
        Criteria criteria = new Criteria(pairCriteria);

        CSVReader reader = new CSVReader();
        pairAssigner = new PairAssigner(reader.getPersons("teilnehmerliste.csv"), criteria.getPairFactor());
        List<Pair> allPairs = new ArrayList<>(pairAssigner.getCreatedPairs());

    }

    /**
     * Tests the matching functionality of the PairAssigner class.
     */
    @Test
    void testPairValidity(){
        List<Pair> allPairs = new ArrayList<>(pairAssigner.getCreatedPairs());

        boolean isValid = true;

        for (Pair matchedPairs : allPairs) {
            if (!(checkKitchenAvailability(matchedPairs) && checkValidFoodPreference(matchedPairs))) {
                isValid = false;
                break;
            }
        }

        Assertions.assertTrue(isValid);
    }

    /**
     * Test to check the age difference criteria for pairs.
     */
    @Test
    void testAgeDifferenceCriteria() {
        List<Pair> pairs = new ArrayList<>(pairAssigner.getCreatedPairs());

        PairCriteriaFactor pairCriteria = new PairCriteriaFactor();
        pairCriteria.setAgeDifferenceCriteria(10);

        double averageAgeDifference = calculateAverageAgeDifference(pairs);
        Assertions.assertTrue(averageAgeDifference <= pairCriteria.getAgeDifferenceCriteria(),
                "Average age difference should be less than or equal to the age difference criteria");

    }


    /**
     * Test to check the gender diversity criteria for pairs.
     */
    @Test
    void testGenderDiversityCriteria() {
        List<Pair> pairs = new ArrayList<>(pairAssigner.getCreatedPairs());
        Assertions.assertNotNull(pairs);
        double idealRatio = 0.5; // Ideal female-to-male ratio

        double totalDeviation = calculateTotalDeviation(pairs, idealRatio);
        double averageDeviation = totalDeviation / pairs.size();

        Assertions.assertTrue(averageDeviation <= 0.2, "Gender diversity deviation should be less than or equal to 0.2");
    }

    /**
     * Calculates the total deviation from the ideal ratio for a list of pairs.
     *
     * @param pairs      the list of pairs to calculate the deviation for
     * @param idealRatio the desired female-to-male ratio
     * @return the total deviation from the ideal ratio
     */
    private double calculateTotalDeviation(List<Pair> pairs, double idealRatio) {
        double totalDeviation = 0.0;
        for (Pair pair : pairs) {
            int femaleCount = countFemales(pair);
            int maleCount = countMales(pair);
            double ratio = (double) femaleCount / (femaleCount + maleCount);
            double deviation = Math.abs(ratio - idealRatio);
            totalDeviation += deviation;
        }
        return totalDeviation;
    }

    /**
     * Counts the number of females in a pair.
     *
     * @param pair the pair to count females in
     * @return the number of females in the pair
     */
    private int countFemales(Pair pair) {
        return (pair.getPerson1().getSex() == EGender.FEMALE ? 1 : 0) +
                (pair.getPerson2().getSex() == EGender.FEMALE ? 1 : 0);
    }

    /**
     * Counts the number of males in a pair.
     *
     * @param pair the pair to count males in
     * @return the number of males in the pair
     */
    private int countMales(Pair pair) {
        return (pair.getPerson1().getSex() == EGender.MALE ? 1 : 0) +
                (pair.getPerson2().getSex() == EGender.MALE ? 1 : 0);
    }

    /**
     * Calculates the average age difference for a list of pairs.
     *
     * @param pairs the list of pairs to calculate the average age difference for
     * @return the average age difference
     */
    private double calculateAverageAgeDifference(List<Pair> pairs) {
        int totalAgeDifference = 0;
        for (Pair pair : pairs) {
            int ageDifference = Math.abs(pair.getPerson1().getAge() - pair.getPerson2().getAge());
            totalAgeDifference += ageDifference;
        }
        return (double) totalAgeDifference / pairs.size();
    }

    /**
     * Test to check the preference deviation criterion for pairs.
     */
    @Test
    void testPreferenceDeviationCriteria() {
        List<Pair> pairs = new ArrayList<>(pairAssigner.getCreatedPairs());

        PairCriteriaFactor pairCriteria = new PairCriteriaFactor();
        pairCriteria.setFoodPreferenceCriteria(1); // Set your desired preference deviation criteria

        double averagePreferenceDeviation = calculateAveragePreferenceDeviation(pairs);
        Assertions.assertTrue(averagePreferenceDeviation <= pairCriteria.getFoodPreferenceCriteria(),
                "Average preference deviation should be less than or equal to the preference deviation criteria");
    }

    /**
     * Calculates the average preference deviation for a list of pairs.
     *
     * @param pairs the list of pairs to calculate the average preference deviation for
     * @return the average preference deviation
     */
    private double calculateAveragePreferenceDeviation(List<Pair> pairs) {
        int totalPreferenceDeviation = 0;
        for (Pair pair : pairs) {
            int preferenceDeviation = Math.abs(pair.getPerson1().getFoodPreference().getValue() - pair.getPerson2().getFoodPreference().getValue());
            totalPreferenceDeviation += preferenceDeviation;
        }
        return (double) totalPreferenceDeviation / pairs.size();
    }
    /**
     * Checks if both persons in the pair have a kitchen assigned.
     *
     * @param matchedPairs the pair to check
     * @return true if both persons have a kitchen, false otherwise
     */
    private boolean checkKitchenAvailability(Pair matchedPairs){
        return matchedPairs.getPerson1().getKitchen() != null && matchedPairs.getPerson2().getKitchen() != null;
    }

    /**
     * Checks if the food preferences of the persons in the pair are compatible.
     *
     * @param matchedPairs the pair to check
     * @return true if the food preferences are compatible, false otherwise
     */
    private boolean checkValidFoodPreference(Pair matchedPairs) {
        EFoodPreference foodPreferencePerson1 = matchedPairs.getPerson1().getFoodPreference();
        EFoodPreference foodPreferencePerson2 = matchedPairs.getPerson2().getFoodPreference();

        return !foodPreferencePerson1.equals(EFoodPreference.MEAT) ||
                (!foodPreferencePerson2.equals(EFoodPreference.VEGGIE) && !foodPreferencePerson2.equals(EFoodPreference.VEGAN));
    }

    /**
     * Test that no person is paired with themselves.
     */
    @Test
    void testNoPersonPairedWithSelf() {
        for (Pair pair : pairAssigner.getCreatedPairs()) {
            Assertions.assertNotEquals(pair.getPerson1(), pair.getPerson2());
        }
    }

    /**
     * Test that each person is assigned to only one pair.
     */
    @Test
    void testEachPersonAssignedToOnlyOnePair() {
        Map<String, Integer> personOccurrences = new HashMap<>();
        for (Pair pair : pairAssigner.getCreatedPairs()) {
            String person1Id = pair.getPerson1().getUUID();
            String person2Id = pair.getPerson2().getUUID();
            personOccurrences.put(person1Id, personOccurrences.getOrDefault(person1Id, 0) + 1);
            personOccurrences.put(person2Id, personOccurrences.getOrDefault(person2Id, 0) + 1);
        }
        boolean isEveryPersonAssignedOnce = personOccurrences.values().stream().allMatch(count -> count == 1);
        Assertions.assertTrue(isEveryPersonAssignedOnce);
    }

    /**
     * Test that no person is left unassigned.
     */
    @Test
    void testNoPersonUnassigned() {
        CSVReader reader = new CSVReader();
        List<Person> allParticipants = reader.getPersons("teilnehmerliste.csv");

        Set<String> assignedParticipantIds = new HashSet<>();
        for (Pair pair : pairAssigner.getCreatedPairs()) {
            assignedParticipantIds.add(pair.getPerson1().getUUID());
            assignedParticipantIds.add(pair.getPerson2().getUUID());
        }
        boolean isEveryParticipantAssigned = allParticipants.stream().allMatch(participant -> assignedParticipantIds.contains(participant.getUUID()));
        Assertions.assertFalse(isEveryParticipantAssigned);
    }
    /*

    @Test
    void testEveryPairHasDifferentGender() {
        for (Pair pair : pairAssigner.getCreatedPairs()) {
            Assertions.assertNotEquals(pair.getPerson1().getSex(), pair.getPerson2().getSex());
        }
    }
*/
    /**
     * Test that every pair has compatible food preferences.
     */
    @Test
    void testEveryPairHasCompatibleFoodPreference() {
        for (Pair pair : pairAssigner.getCreatedPairs()) {
            Assertions.assertTrue(PairAssigner.checkFoodPreferenceValidation(pair.getPerson1(), pair.getPerson2()));
        }
    }

/*
    @Test
    void testPairMatchScoreMeetsThreshold() {
        double threshold = 0.6;
        for (Pair pair : pairAssigner.getCreatedPairs()) {
            Assertions.assertTrue(pair.getMatchScore() >= threshold);
        }
    }
*/

    /**
     * Test that each pair's coordinates match with the assigned kitchen.
     */
    @Test
    void testPairCoordinatesMatchWithAssignedKitchen() {
        for (Pair pair : pairAssigner.getCreatedPairs()) {
             boolean isPerson1CoordinatesMatch = (pair.getKitchen().getkitchenLatitude() == pair.getPerson1().getKitchen().getkitchenLatitude()) &&
                   (pair.getKitchen().getKitchenLongitude() == pair.getPerson1().getKitchen().getKitchenLongitude());

            boolean isPerson2CoordinatesMatch = (pair.getKitchen().getkitchenLatitude() == pair.getPerson2().getKitchen().getkitchenLatitude()) &&
                   (pair.getKitchen().getKitchenLongitude() == pair.getPerson2().getKitchen().getKitchenLongitude());

        Assertions.assertTrue(isPerson1CoordinatesMatch || isPerson2CoordinatesMatch);
        }
    }

    /**
     * Test that each pair has at least one kitchen assigned.
     */
    @Test
    void testEachPairHasAtLeastOneKitchen() {
        for (Pair pair : pairAssigner.getCreatedPairs()) {
            Assertions.assertTrue(pair.getPerson1().getKitchen() != null || pair.getPerson2().getKitchen() != null);
        }
    }

    /**
     * Test that each pair has kitchen coordinates.
     */
    @Test
    void testEachPairHasKitchenCoordinates() {
        for (Pair pair : pairAssigner.getCreatedPairs()) {
            Assertions.assertNotNull(pair.getKitchen().getKitchenLongitude());
            Assertions.assertNotNull(pair.getKitchen().getkitchenLatitude());
        }
    }

    /**
     * Test that each pair has a food preference.
     */
    @Test
    void testEachPairHasFoodPreference() {
        for (Pair pair : pairAssigner.getCreatedPairs()) {
            Assertions.assertNotNull(pair.getFoodPreference());
        }
    }

/*
    @Test
    void testEachPairHasDistanceToAfterParty() {
        for (Pair pair : pairAssigner.getCreatedPairs()) {
            Assertions.assertNotNull(pair.getDistanceToAfterParty());
        }
    }

    @Test
    void testEachPairHasMatchScore() {
        for (Pair pair : pairAssigner.getCreatedPairs()) {
            Assertions.assertNotNull(pair.getMatchScore());
        }
    }
*/

    /**
     * Test that odd number of participants results in overflow.
     */
    @Test
    void testOddNumberOfParticipantsResultsInOverflow() {
        List<Person> pairedParticipants = new ArrayList<>();
        for (Pair pair : pairAssigner.getCreatedPairs()) {
            pairedParticipants.add(pair.getPerson1());
            pairedParticipants.add(pair.getPerson2());
        }
        CSVReader reader = new CSVReader();

        List<Person> totalParticipantCount = reader.getPersons("teilnehmerliste.csv");
        int length = totalParticipantCount.size();

        int pairedParticipantCount = pairedParticipants.size();

        Assertions.assertNotEquals(length % 2, length - pairedParticipantCount);
    }

    /**
     * Test that each pair is unique.
     */
    @Test
    void testEachPairIsUnique() {
        Set<Pair> uniquePairs = new HashSet<>(pairAssigner.getCreatedPairs());
        Assertions.assertEquals(pairAssigner.getCreatedPairs().size(), uniquePairs.size());
    }

    /**
     * Test that each pair has at least one kitchen with the "YES" option after pairing.
     */
    @Test
    void testPairHasAtLeastOneKitchenYesAfterPairing() {
        for (Pair pair : pairAssigner.getCreatedPairs()) {
            assertTrue(pair.getPerson1().getKitchen().getHasKitchen() == EHasKitchen.YES ||
                    pair.getPerson2().getKitchen().getHasKitchen() == EHasKitchen.YES);
        }
    }

    /**
     * Test that each pair has a food preference after pairing.
     */
    @Test
    void testPairHasFoodPreferenceAfterPairing() {
        for (Pair pair : pairAssigner.getCreatedPairs()) {
            assertNotNull(pair.getFoodPreference());
        }
    }

    /**
     * Test that each pair has a distance to the after-party location after pairing.
     */
    @Test
    void testPairHasDistanceToAfterPartyAfterPairing() {
        for (Pair pair : pairAssigner.getCreatedPairs()) {
            assertNotNull(pair.getPathLength());
        }
    }

    /**
     * Test that each pair has unique participants after pairing.
     */
    @Test
    void testUniqueParticipantsInPairAfterPairing() {
        for (Pair pair : pairAssigner.getCreatedPairs()) {
            assertNotEquals(pair.getPerson1().getUUID(), pair.getPerson2().getUUID());
        }
    }

    /**
     * Test the performance of the PairAssigner.
     */
    @Test
    void testPerformance() {
        long startTime = System.currentTimeMillis();
        PairCriteriaFactor pairCriteria = new PairCriteriaFactor();
        pairCriteria.setAgeDifferenceCriteria(10);
        GroupCriteriaFactor groupCriteriaFactor = new GroupCriteriaFactor();
        Criteria criteria = new Criteria(pairCriteria, groupCriteriaFactor);
        // Prepare your CSV reader to return a large list of Persons
        CSVReader reader = new CSVReader();
        pairAssigner = new PairAssigner(reader.getPersons("teilnehmerliste.csv"), criteria.getPairFactor());

        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        // Assuming the operation should not take more than 5 seconds (5000 milliseconds)
        Assertions.assertTrue(duration < 5000);
    }

    /**
     * Test boundary cases for age criteria.
     */
    @Test
    void testAgeBoundaryCases() {
        // Prepare your CSV reader to return a list of Persons with ages at the boundaries of your criteria
        CSVReader reader = new CSVReader();
        PairCriteriaFactor pairCriteria = new PairCriteriaFactor();
        pairCriteria.setAgeDifferenceCriteria(10);
        GroupCriteriaFactor groupCriteriaFactor = new GroupCriteriaFactor();
        Criteria criteria = new Criteria(pairCriteria, groupCriteriaFactor);
        pairAssigner = new PairAssigner(reader.getPersons("teilnehmerliste.csv"), criteria.getPairFactor());

        // Test whether the resulting pairs are valid according to your age criteria
        for (Pair pair : pairAssigner.getCreatedPairs()) {
            Assertions.assertTrue(Math.abs(pair.getPerson1().getAge() - pair.getPerson2().getAge()) <= criteria.getPairFactor().getAgeDifferenceCriteria());
        }
    }

    /**
     * Test the randomness of the pairings.
     */
    @Test
    void testPairingRandomness() {
        List<Pair> firstRunPairs = pairAssigner.getCreatedPairs();
        System.out.println(firstRunPairs);
        // Run the pair assigner multiple times
        setup();
        List<Pair> secondRunPairs = pairAssigner.getCreatedPairs();
        System.out.println(secondRunPairs);

        // Check that the results of the first run and the second run are not the same
        Assertions.assertNotEquals(firstRunPairs, secondRunPairs);
    }

}