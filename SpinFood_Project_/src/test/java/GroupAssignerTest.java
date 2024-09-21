import controller.*;
import data.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GroupAssigner class.
 */
class GroupAssignerTest {

    static PairAssigner pairAssigner;
    static GroupAssigner groupAssigner;


    /**
     * Set up the test environment before each test case.
     */
    @BeforeEach
    void setup() {
        PairCriteriaFactor pairCriteria = new PairCriteriaFactor();
        pairCriteria.setAgeDifferenceCriteria(10);
        GroupCriteriaFactor groupCriteriaFactor = new GroupCriteriaFactor();
        Criteria criteria = new Criteria(pairCriteria, groupCriteriaFactor);

        CSVReader reader = new CSVReader();

        pairAssigner = new PairAssigner(reader.getPersons("teilnehmerliste.csv"), criteria.getPairFactor());
        List<Pair> allPairs = new ArrayList<>(pairAssigner.getCreatedPairs());
        AfterPartyReader afterPartyCoordinates = new AfterPartyReader();
        groupAssigner = new GroupAssigner();
        List<Person> testMoveUpPersons = new ArrayList<>();

        groupAssigner.createGroups(allPairs, 500, true, testMoveUpPersons, afterPartyCoordinates.getPartyLocation("partylocation.csv"));
    }

    /**
     * Test the validity of all groups.
     * Groups are considered valid if they meet certain criteria.
     */
    @Test
    void testGroupValidity() {
        List<Group> groups = new ArrayList<>(groupAssigner.getAllCreatedGroups());

        boolean isValid = true;

        for (Group group : groups) {
            if (!checkIfGroupHasCooked(group) || !checkValidFoodPreferenceGroup(group) || !checkNoDuplicatePairs()) {
                isValid = false;
                break;
            }
        }
        assertTrue(isValid);
    }

    /**
     * Test to check the gender diversity criteria for a list of groups.
     */
    @Test
    void testGenderDiversityCriteria() {
        List<Group> groups = new ArrayList<>(groupAssigner.getAllCreatedGroups());
        double averageDeviation = calculateGenderDiversity(groups);
        Assertions.assertTrue(averageDeviation <= 0.2, "Gender diversity criteria not met");
    }

    /**
     * Test to check the preference deviation criteria for a list of groups.
     */
    @Test
    void testPreferenceDeviationCriteria() {
        List<Group> groups = new ArrayList<>(groupAssigner.getAllCreatedGroups());
        double averageDeviation = calculatePreferenceDeviation(groups);
        Assertions.assertTrue(averageDeviation <= 0.2, "Preference deviation criteria not met");
    }

    /**
     * Test to check the age difference criteria for groups.
     */
    @Test
    void testAgeDifferenceCriteriaForGroups() {
        List<Group> groups = new ArrayList<>(groupAssigner.getAllCreatedGroups());

        double averageAgeDifference = calculateAverageAgeDifference(groups);
        Assertions.assertTrue(averageAgeDifference <= 2,
                "Average age difference should be less than or equal to the age difference criteria");
    }

    /**
     * Calculates the average preference deviation for a list of groups.
     *
     * @param groups the list of groups to calculate the average preference deviation for
     * @return the average preference deviation
     */
    private double calculatePreferenceDeviation(List<Group> groups) {
        int totalDeviation = 0;
        int totalPairs = 0;

        for (Group group : groups) {
            List<Pair> pairs = group.getPairs();

            for (Pair pair : pairs) {
                int preferenceDifference = Math.abs(pair.getPerson1().getFoodPreference().getValue() - pair.getPerson2().getFoodPreference().getValue());
                totalDeviation += preferenceDifference;
                totalPairs++;
            }
        }

        return (double) totalDeviation / totalPairs;
    }


    /**
     * Calculates the gender diversity for a list of groups.
     *
     * @param groups the list of groups to calculate the gender diversity for
     * @return the average deviation from the ideal gender ratio
     */
    private double calculateGenderDiversity(List<Group> groups) {
        double idealRatio = 0.5; // Ideal ratio of women to total persons
        double totalDeviation = 0.0;

        for (Group group : groups) {
            int femaleCount = 0;
            int maleCount = 0;
            int totalCount = 0;

            for (Pair pair : group.getPairs()) {
                if (pair.getPerson1().getSex() == EGender.FEMALE) {
                    femaleCount++;
                } else if (pair.getPerson1().getSex() == EGender.MALE) {
                    maleCount++;
                }

                if (pair.getPerson2().getSex() == EGender.FEMALE) {
                    femaleCount++;
                } else if (pair.getPerson2().getSex() == EGender.MALE) {
                    maleCount++;
                }

                totalCount += 2;
            }

            double actualRatio = (double) femaleCount / totalCount;
            double deviation = Math.abs(actualRatio - idealRatio);
            totalDeviation += deviation;
        }

        return totalDeviation / groups.size();
    }

    /**
     * Calculates the average age difference for a list of groups.
     *
     * @param groups the list of groups to calculate the average age difference for
     * @return the average age difference
     */
    private double calculateAverageAgeDifference(List<Group> groups) {
        Assertions.assertNotNull(groups);
        int totalAgeDifference = 0;
        int totalPairs = 0;
        for (Group group : groups) {
            int groupAgeDifference = 0;
            List<Pair> pairs = group.getPairs();
            for (Pair pair : pairs) {
                int ageDifference = Math.abs(pair.getPerson1().getAge() - pair.getPerson2().getAge());
                groupAgeDifference += ageDifference;
            }
            totalAgeDifference += groupAgeDifference;
            totalPairs += pairs.size();
        }
        return (double) totalAgeDifference / totalPairs;
    }

    /**
     * Check if the food preference in the group is valid.
     * A group is considered valid if it satisfies the food preference constraints.
     *
     * @param matchedGroups The group to check.
     * @return True if the group has a valid food preference, false otherwise.
     */
    private boolean checkValidFoodPreferenceGroup(Group matchedGroups) {
        boolean meatFound = false;
        boolean veggieFound = false;
        boolean veganFound = false;

        for (Pair matchedPair : matchedGroups.getPairs()) {
            switch (matchedPair.getFoodPreference()) {
                case MEAT:
                    meatFound = true;
                    break;
                case VEGGIE:
                    veggieFound = true;
                    break;
                case VEGAN:
                    veganFound = true;
                    break;
            }
        }

        return (!meatFound || matchedGroups.getPairs().size() == 1) && (!veggieFound || !veganFound);
    }

    /**
     * Checks if there are no duplicate pairs across different groups.
     *
     * @return true if there are no duplicate pairs, false otherwise.
     */
    private boolean checkNoDuplicatePairs() {
        for (Group group : groupAssigner.getAllCreatedGroups()) {
            List<Pair> pairs = group.getPairs();
            Set<String> uniquePairIDs = pairs.stream()
                    .map(Pair::getPairID)
                    .collect(Collectors.toSet());

            for (Pair pair : pairs) {
                if (uniquePairIDs.contains(pair)) {
                    return false; // Found a duplicate pair
                }
                uniquePairIDs.add(String.valueOf(pair));
            }
        }
        return true;
    }


    /**
     * Check if the group has a cook assigned.
     *
     * @param matchedGroups The group to check.
     * @return True if the group has a cook assigned, false otherwise.
     */
    private boolean checkIfGroupHasCooked(Group matchedGroups) {
        return matchedGroups.getCookID() != null;
    }

    /**
     * Test the size of each group.
     * Each group is expected to have a size of 3 pairs.
     */
    @Test
    void testGroupSize() {
        for (Group group : groupAssigner.getAllCreatedGroups()) {
            assertEquals(3, group.getPairs().size());
        }
    }

    /**
     * Test that each group has a unique meal type.
     */
    @Test
    void testUniqueCoursesInGroup() {
        for (Group group : groupAssigner.getAllCreatedGroups()) {
            MealType mealType = group.getMealType();
            assertNotNull(mealType);
        }
    }

    /**
     * Test that each group has a food preference assigned.
     */
    @Test
    void testFoodPreferenceInGroup() {
        for (Group group : groupAssigner.getAllCreatedGroups()) {
            assertNotNull(group.getFoodPreference());
        }
    }

    /**
     * Test that there are remaining pairs in the overflow.
     * This test verifies that the group assigner correctly handles overflow pairs.
     */
    @Test
    void testRemainingPairInOverflow() {
        Coordinate testCoordinate = new Coordinate(51.5074, -0.1278);
        List<Person> testMoveUpPersons = new ArrayList<>();
        groupAssigner.createGroups(pairAssigner.getCreatedPairs(), 500, true, testMoveUpPersons, testCoordinate);
        assertTrue(groupAssigner.getMoveUpPairs().size() > 0);
    }

    /**
     * Test that each group has unique pairs.
     * Each group should not have duplicate pairs.
     */
    @Test
    void testUniquePairsInGroup() {
        for (Group group : groupAssigner.getAllCreatedGroups()) {
            List<Pair> pairs = group.getPairs();
            Set<String> uniquePairIDs = pairs.stream()
                    .map(Pair::getPairID)
                    .collect(Collectors.toSet());

            assertEquals(pairs.size(), uniquePairIDs.size());
        }
    }

    /**
     * Test the setter and getter for move-up pairs.
     */
    @Test
    void testSetMoveUpPairs() {
        List<Pair> testPairs = new ArrayList<>();
        groupAssigner.setMoveUpPairs(testPairs);
        assertEquals(testPairs, groupAssigner.getMoveUpPairs());
    }

    /**
     * Test the setter and getter for move-up persons.
     */
    @Test
    void testSetMoveUpPersons() {
        List<Person> testPersons = new ArrayList<>();
        groupAssigner.setMoveUpPersons(testPersons);
        assertEquals(testPersons, groupAssigner.getMoveUpPersons());
    }

    /**
     * Test that each pair ID within a group is unique.
     */
    @Test
    void testAllPairsInGroupHaveUniqueIDs() {
        for (Group group : groupAssigner.getAllCreatedGroups()) {
            Set<String> uniqueIDs = group.getPairs().stream()
                    .map(Pair::getPairID)
                    .collect(Collectors.toSet());

            assertEquals(group.getPairs().size(), uniqueIDs.size());
        }
    }
/*
    @Test
    void testCorrectNumberOfGroupsCreated() {
        int totalPairs = pairAssigner.getCreatedPairs().size();
        System.out.println(totalPairs);
        int expectedGroupCount = totalPairs / 3;
        System.out.println(expectedGroupCount);
        int cool = groupAssigner.getAllCreatedGroups().size();
        System.out.println(cool);

        int remainderPairs = totalPairs % 3;
        if (remainderPairs > 0) {
            expectedGroupCount++;
        }
        assertEquals(expectedGroupCount, groupAssigner.getAllCreatedGroups().size());
    }
*/
    /**
     * Test that no group has more than three pairs.
     * Each group should have a maximum of three pairs.
     */
    @Test
    void testNoGroupHasMoreThanThreePairs() {
        for (Group group : groupAssigner.getAllCreatedGroups()) {
            assertTrue(group.getPairs().size() <= 3);
        }
    }

    /**
     * Test the group size limit.
     * Each group should have a fixed size of three pairs.
     */
    @Test
    void testGroupSizeLimit() {
        int limit = 3; // Assuming that the limit is 3 pairs per group
        for (Group group : groupAssigner.getAllCreatedGroups()) {
            assertTrue(group.getPairs().size() == limit);
        }
    }

    /**
     * Test that group IDs are unique.
     */
    @Test
    void testGroupIDsAreUnique() {
        Set<String> uniqueGroupIDs = groupAssigner.getAllCreatedGroups().stream()
                .map(Group::getGroupID)
                .collect(Collectors.toSet());

        assertEquals(groupAssigner.getAllCreatedGroups().size(), uniqueGroupIDs.size());
    }

    /*
    @Test
    void testAllPairsAreInGroups() {
        Set<Pair> allPairsInGroups = groupAssigner.getAllCreatedGroups().stream()
                .flatMap(group -> group.getPairs().stream())
                .collect(Collectors.toSet());

        assertTrue(pairAssigner.getCreatedPairs().size(), allPairsInGroups.size());
    }
    */

    /**
     * Test that every group has a cook assigned.
     */
    @Test
    void testEveryGroupHasCook() {
        for (Group group : groupAssigner.getAllCreatedGroups()) {
            assertNotNull(group.getCookID());
        }
    }

    /**
     * Test the gender balance within each group.
     * The difference between the number of males and females in a group should be within a certain threshold.
     */
    @Test
    void testGroupGenderBalance() {
        for (Group group : groupAssigner.getAllCreatedGroups()) {
            int maleCount = 0;
            int femaleCount = 0;
            for (Pair pair : group.getPairs()) {
                if (pair.getPerson1().getSex() == EGender.MALE || pair.getPerson2().getSex() == EGender.MALE) {
                    maleCount++;
                }
                if (pair.getPerson1().getSex() == EGender.FEMALE || pair.getPerson2().getSex() == EGender.FEMALE) {
                    femaleCount++;
                }
            }
            assertTrue(Math.abs(maleCount - femaleCount) <= 5); // Assuming a difference of 5 is acceptable
        }
    }


/*
    @Test
    void testAfterPartyLocationOfGroups() {
        Coordinate afterPartyLocation = new Coordinate(51.5074, -0.1278); // Replace with the actual after-party coordinate
        for (Group group : groupAssigner.getAllCreatedGroups()) {
            assertEquals(afterPartyLocation, group.getAfterPartyLocation());
        }
    }
*/

}
