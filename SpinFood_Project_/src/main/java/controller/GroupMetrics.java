package controller;

import controller.Group;
import data.EGender;
import data.Pair;

import java.util.List;

/**
 * The GroupMetrics class calculates metrics for a list of groups and replacement pairs.
 */
public class GroupMetrics {

    private int createdGroupsCount;

    private int replacementGroupCount;

    private double avgGenderDiversity;

    private double avgAgeDifference;

    private double avgFoodPreferenceDeviation;

    private double pathLength;

    /**
     * Constructs a GroupMetrics object and generates the metrics.
     *
     * @param groups             the list of groups
     * @param replacementListPairs    the list of replacement pairs
     */
    public GroupMetrics(List<Group> groups, List<Pair> replacementListPairs) {

        generateMetrics(groups, replacementListPairs);
    }

    /**
     * Generates the metrics based on the provided groups and replacement pairs.
     *
     * @param groups             the list of groups
     * @param replacementList    the list of replacement pairs
     */
    public void generateMetrics(List<Group> groups, List<Pair> replacementList) {

        this.createdGroupsCount = groups.size();
        this.replacementGroupCount = replacementList.size();

        double peopleSum = createdGroupsCount * 3;
        double womenSum = 0;
        int ageDifferenceSum = 0;
        int preferenceDeviationSum = 0;
        int pathSum = 0;

        for(int i = 0; i < groups.size(); i++) {
            for(int j = 0; j < groups.get(i).getPairs().size(); j++) {

                if(groups.get(i).getPairs().get(j).getPerson1().getSex() == EGender.FEMALE && groups.get(i).getPairs().get(j).getPerson2().getSex() == EGender.FEMALE ||
                        groups.get(i).getPairs().get(j).getPerson1().getSex() == EGender.MALE && groups.get(i).getPairs().get(j).getPerson2().getSex() == EGender.MALE
                ) {
                    womenSum += 0.5;
                }

                ageDifferenceSum += Math.abs(groups.get(i).getPairs().get(j).getPerson1().getAgeRange().getValue() - groups.get(i).getPairs().get(j).getPerson2().getAgeRange().getValue());
                preferenceDeviationSum += Math.abs(groups.get(i).getPairs().get(j).getPerson1().getFoodPreference().getValue() - groups.get(i).getPairs().get(j).getPerson2().getFoodPreference().getValue());
                pathSum += groups.get(i).getPairs().get(j).getPathLength();
            }
        }


        this.avgGenderDiversity = womenSum / peopleSum;
        this.avgAgeDifference = ageDifferenceSum / peopleSum;
        this.avgFoodPreferenceDeviation = preferenceDeviationSum / peopleSum;
        this.pathLength = pathSum;
    }

    /**
     * Prints the calculated metrics.
     */
    public void printMetrics() {
        System.out.println("Anzahl Gruppen: " + this.createdGroupsCount + "\n Anzahl Nachrueckerpaerchen: " + this.replacementGroupCount + "\n Geschlechtsdiversitaet: " + this.avgGenderDiversity +
                "\n Altersdifferenz: " + this.avgAgeDifference + "\n Vorliebenabweichung: " + this.avgFoodPreferenceDeviation + "\n Weglänge: " + this.pathLength);
    }

    /**
     * Returns the count of created groups.
     *
     * @return the count of created groups
     */
    public int getCreatedGroupsCount() {
        return createdGroupsCount;
    }

    /**
     * Returns the count of replacement groups.
     *
     * @return the count of replacement groups
     */

    public int getReplacementGroupCount() {
        return replacementGroupCount;
    }

    /**
     * Returns the average gender diversity.
     *
     * @return the average gender diversity
     */
    public double getAvgGenderDiversity() {
        return roundValue(avgGenderDiversity);
    }

    /**
     * Returns the average age difference.
     *
     * @return the average age difference
     */
    public double getAvgAgeDifference() {
        return roundValue(avgAgeDifference);
    }

    /**
     * Returns the average food preference deviation.
     *
     * @return the average food preference deviation
     */
    public double getAvgFoodPreferenceDeviation() {
        return roundValue(avgFoodPreferenceDeviation);
    }

    /**
     * Returns the path length.
     *
     * @return the path length
     */
    public double getPathLength() {
        return pathLength;
    }

    /**
     * Rounds the given value to two decimal places.
     *
     * @param val the value to round
     * @return the rounded value
     */
    public double roundValue(double val) {
        return (Math.round(val * 100.0) / 100.0);
    }
}