package controller;

import data.EGender;
import data.Pair;
import data.Person;

import java.text.DecimalFormat;
import java.util.List;

/**
 * PairMetrics class calculates and stores metrics for a list of pairs.
 */
public class PairMetrics {

    private int createdPairsCount;

    private int replacementPairCount;

    private double avgGenderDiversity;

    private double avgAgeDifference;

    private double avgFoodPreferenceDeviation;

    DecimalFormat df = new DecimalFormat("#.00");

    /**
     * Constructs a PairMetrics object and generates metrics based on the provided pairs and replacement list.
     *
     * @param pairs                    the list of pairs
     * @param replacementListPerson    the list of replacement persons
     */
    public PairMetrics(List<Pair> pairs, List<Person> replacementListPerson) {

        generateMetrics(pairs, replacementListPerson);
    }

    /**
     * Generates metrics based on the provided pairs and replacement list.
     *
     * @param pairs              the list of pairs
     * @param replacementList    the list of replacement persons
     */
    public void generateMetrics(List<Pair> pairs, List<Person> replacementList) {

        this.createdPairsCount = pairs.size();

        this.replacementPairCount = replacementList.size();

        double womenSum = 0;
        int ageDifferenceSum = 0;
        int preferenceDeviation = 0;

        for(int i = 0; i < pairs.size(); i++) {

            if(pairs.get(i).getPerson1().getSex() == EGender.FEMALE && pairs.get(i).getPerson2().getSex() == EGender.FEMALE ||
                    pairs.get(i).getPerson1().getSex() == EGender.MALE && pairs.get(i).getPerson2().getSex() == EGender.MALE
            ) {
                womenSum += 0.5;
            }

            ageDifferenceSum += Math.abs(pairs.get(i).getPerson1().getAgeRange().getValue() - pairs.get(i).getPerson2().getAgeRange().getValue());
            preferenceDeviation += Math.abs(pairs.get(i).getPerson1().getFoodPreference().getValue() - pairs.get(i).getPerson2().getFoodPreference().getValue());
        }

        this.avgGenderDiversity = womenSum / createdPairsCount;
        this.avgAgeDifference = (double) ageDifferenceSum / createdPairsCount;
        this.avgFoodPreferenceDeviation = (double) preferenceDeviation / createdPairsCount;
    }

    /**
     * Prints the calculated metrics.
     */
    public void printMetrics() {
        System.out.println("Anzahl Paerchen: " + this.createdPairsCount + "\n Anzahl Nachrückerpersonen: " + this.replacementPairCount + "\n Geschlechtsdiversitaet: " + this.avgGenderDiversity +
                "\n Altersdifferenz: " + this.avgAgeDifference + "\n Vorliebenabweichung: " + this.avgFoodPreferenceDeviation);
    }

    /**
     * Returns the count of created pairs.
     *
     * @return the count of created pairs
     */
    public int getCreatedPairsCount() {
        return createdPairsCount;
    }

    /**
     * Returns the count of replacement pairs.
     *
     * @return the count of replacement pairs
     */
    public int getReplacementPairCount() {
        return replacementPairCount;
    }

    /**
     * Returns the average gender diversity.
     *
     * @return the average gender diversity
     */
    public double getAvgGenderDiversity() {

        return roundValue(this.avgGenderDiversity);
    }

    /**
     * Returns the average age difference.
     *
     * @return the average age difference
     */
    public double getAvgAgeDifference() {

        return roundValue(this.avgAgeDifference);
    }

    /**
     * Returns the average food preference deviation.
     *
     * @return the average food preference deviation
     */
    public double getAvgFoodPreferenceDeviation() {

        return roundValue(this.avgFoodPreferenceDeviation);
    }

    /**
     * Rounds the given value to two decimal places.
     *
     * @param val    the value to round
     * @return the rounded value
     */
    public double roundValue(double val) {
        return (Math.round(val * 100.0) / 100.0);
    }
}