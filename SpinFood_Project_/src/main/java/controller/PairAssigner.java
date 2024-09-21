package controller;

import data.*;
import data.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The PairAssigner class assigns pairs of people based on given criteria.
 */

public class PairAssigner {

    private static List<Person> replacementList;
    private List<Pair> pairs = new ArrayList<>();

    /**
     * Constructs a PairAssigner object with the given list of people and pair criteria factor.
     *
     * @param people   the list of people to assign pairs from
     * @param criteria the pair criteria factor to consider during pairing
     */
    public PairAssigner(List<Person> people, PairCriteriaFactor criteria) {
        replacementList = new ArrayList<>();
        this.assignPairs(people, criteria);
    }

    /**
     * Assigns pairs of people based on the given list of people and pair criteria factor.
     *
     * @param people   the list of people to assign pairs from
     * @param criteria the pair criteria factor to consider during pairing
     */
    public void assignPairs(List<Person> people, PairCriteriaFactor criteria) {

        for(int i = 0; i < people.size(); i++) {

            List<Person> validCandidates = new ArrayList<>();
            List<PairCandidate> ratedCandidates = new ArrayList<>();
            Person currentPerson = people.get(i);

            if(currentPerson.isAvailable()) {

                for(int j = 0; j < people.size(); j++) {

                    Person comparingPerson = people.get(j);

                    if(comparingPerson.isAvailable()) {

                        if (!Objects.equals(currentPerson.getUUID(), comparingPerson.getUUID())) {

                            if (this.checkFoodPreferenceValidation(currentPerson, comparingPerson) &&
                                    this.checkKitchenAvailability(currentPerson, comparingPerson)) {

                                validCandidates.add(comparingPerson);
                            }
                        }
                    }
                }

                if(validCandidates.size() > 0) {
                    for(int k = 0; k < validCandidates.size(); k++) {

                        PairCandidate candidate = new PairCandidate(validCandidates.get(k));
                        rateCandidate(currentPerson, candidate, criteria);
                        ratedCandidates.add(candidate);
                    }

                    PairCandidate lowestRatingCandidate = ratedCandidates.get(0);

                    for (int p = 0; p < ratedCandidates.size(); p++) {
                        int rating = ratedCandidates.get(p).getRating();

                        if (rating < lowestRatingCandidate.getRating()) {
                            lowestRatingCandidate = ratedCandidates.get(p);
                        }
                    }

                    if(lowestRatingCandidate != null) {
                        Pair pair = new Pair(currentPerson, lowestRatingCandidate.getCandidate());
                        pair.setSignedInTogether(false);

                        if(currentPerson.getKitchen().getHasKitchen() == EHasKitchen.YES) {
                            pair.setKitchen(currentPerson.getKitchen());
                        }

                        if(lowestRatingCandidate.getCandidate().getKitchen().getHasKitchen() == EHasKitchen.YES) {
                            pair.setKitchen(lowestRatingCandidate.getCandidate().getKitchen());
                        }

                        pair.setFoodPreferenceDifference(lowestRatingCandidate.getFoodPreferenceDifference());
                        pairs.add(pair);
                        currentPerson.setAvailable(false);
                        lowestRatingCandidate.getCandidate().setAvailable(false);
                    }
                }
            }
        }

        for(int i = 0; i < people.size(); i++) {

            if(!checkIfPersonAssignedToPair(people.get(i))) {
                this.replacementList.add(people.get(i));
            }
        }
    }

    /**
     * Checks the food preference validation between two persons.
     *
     * @param person1 the first person
     * @param person2 the second person
     * @return true if the food preferences are valid, false otherwise
     */
    public static boolean checkFoodPreferenceValidation(Person person1, Person person2) {

        boolean isValid = true;

        if(person1.getFoodPreference() == EFoodPreference.MEAT && person2.getFoodPreference() == EFoodPreference.VEGAN ||
                person1.getFoodPreference() == EFoodPreference.MEAT && person2.getFoodPreference() == EFoodPreference.VEGGIE ||
                person1.getFoodPreference() == EFoodPreference.VEGAN && person2.getFoodPreference() == EFoodPreference.MEAT ||
                person1.getFoodPreference() == EFoodPreference.VEGGIE && person2.getFoodPreference() == EFoodPreference.MEAT
        ) {
            isValid = false;
        }

        return isValid;
    }

    /**
     * Checks the kitchen availability between two persons.
     *
     * @param person1 the first person
     * @param person2 the second person
     * @return true if the kitchen availability is valid, false otherwise
     */
    public static boolean checkKitchenAvailability(Person person1, Person person2) {

        boolean isValid = true;

        if((person1.getKitchen().getHasKitchen() == EHasKitchen.YES && person2.getKitchen().getHasKitchen() == EHasKitchen.YES) ||
                (person1.getKitchen().getHasKitchen() == EHasKitchen.NO && person2.getKitchen().getHasKitchen() == EHasKitchen.NO) ||
                (person1.getKitchen().getHasKitchen() == EHasKitchen.NO && person2.getKitchen().getHasKitchen() == EHasKitchen.MAYBE) ||
                (person1.getKitchen().getHasKitchen() == EHasKitchen.MAYBE && person2.getKitchen().getHasKitchen() == EHasKitchen.NO) ||
                (person1.getKitchen().getHasKitchen() == EHasKitchen.MAYBE && person2.getKitchen().getHasKitchen() == EHasKitchen.MAYBE)
        ) {
            isValid = false;
        }

        return isValid;
    }

    /**
     * Rates a candidate based on the given person and pair criteria factor.
     *
     * @param person    the person to rate the candidate against
     * @param candidate the candidate to be rated
     * @param criteria  the pair criteria factor to consider during rating
     */
    private void rateCandidate(Person person, PairCandidate candidate, PairCriteriaFactor criteria) {

        // age difference

        int ageRangeDifference = person.getAgeRange().getValue() - candidate.getCandidate().getAgeRange().getValue();
        candidate.increaseRating(Math.abs(ageRangeDifference) * criteria.getAgeDifferenceCriteria());

        // gender difference

        if(person.getSex() == candidate.getCandidate().getSex()) {
            candidate.increaseRating(8 * criteria.getGenderDifferenceCriteria());
        }

        // food preference

        int foodPreference = Math.abs(person.getFoodPreference().getValue() - candidate.getCandidate().getFoodPreference().getValue());

        candidate.setFoodPreferenceDifference(foodPreference);

        if(foodPreference == 1) {

            candidate.increaseRating(4 * criteria.getFoodPreferenceCriteria());
        }
        else if(foodPreference == 2) {
            candidate.increaseRating(8 * criteria.getFoodPreferenceCriteria());
        }
    }

    /**
     * Checks if a person is already assigned to a pair.
     *
     * @param person the person to check
     * @return true if the person is already assigned to a pair, false otherwise
     */
    private boolean checkIfPersonAssignedToPair(Person person) {

        boolean exists = false;

        for(int j = 0; j < pairs.size(); j++) {

            if(person.getUUID().equals(pairs.get(j).getPerson1().getUUID()) || person.getUUID().equals(pairs.get(j).getPerson2().getUUID())) {
                exists = true;
            }
        }

        return exists;
    }

    /**
     * Retrieves the list of people who were not assigned to a pair.
     *
     * @return the list of people who were not assigned to a pair
     */
    public List<Person> getReplacementList() {

        return this.replacementList;
    }

    /**
     * Retrieves the list of created pairs.
     *
     * @return the list of created pairs
     */
    public List<Pair> getCreatedPairs() {

        return this.pairs;
    }

    /**
     * Removes a person from the replacement list.
     *
     * @param person the person to remove
     */
    public static void removePersonFromReplacementList(Person person) {

        for(int i = replacementList.size() - 1; i >= 0; i--) {

            if(replacementList.get(i).getUUID().equals(person.getUUID())) {
                replacementList.remove(i);
            }
        }
    }
}

