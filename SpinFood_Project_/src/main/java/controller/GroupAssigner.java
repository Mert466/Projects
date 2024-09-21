package controller;
import controller.GroupCriteriaFactor;
import controller.PairAssigner;
import controller.PairCriteriaFactor;
import data.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The GroupAssigner class assigns pairs to groups for meals based on specified criteria.
 */
public class GroupAssigner {

    private GroupCriteriaFactor criteria;
    private List<Pair> moveUpPairs = new ArrayList<>();
    private List<Person> moveUpPersons = new ArrayList<>();
    public List<Group> starter = new ArrayList<>();
    public List<Group> main = new ArrayList<>();
    public List<Group> dessert = new ArrayList<>();
    public Pair testPair;
    public String testGroupID;
    private int maxPairSize;
    List<Group> allGroups;
    private Coordinate afterParty;

    /**
     * This method returns all the groups that have been created.
     *
     * @return A list of all created groups. Each group is represented by an instance of the Group class.
     */
    public List<Group> getAllCreatedGroups() {
        return List.of(this.starter, this.main, this.dessert).stream().flatMap(x -> x.stream()).collect(Collectors.toList());
    }


    /**
     * Creates groups by assigning pairs based on specified criteria and constraints.
     *
     * @param pairs               the list of pairs to assign to groups
     * @param maximalPersonAmount the maximum number of people in a group
     * @param assignGroupsToDishes a flag indicating whether to assign groups to dishes (starter, main, dessert)
     */
    public void createGroups(List<Pair> pairs, int maximalPersonAmount, boolean assignGroupsToDishes, List<Person> moveUpPersons, Coordinate afterPartyCoordinates) {
        moveUpPairs = new ArrayList<>();
        this.moveUpPersons = moveUpPersons;
        afterParty = afterPartyCoordinates;
        List<Pair> multipleKitchen = checkForMultipleKitchen(pairs);

        for (int i = pairs.size() - 1; i >= 0; i--) {

            for (int j = 0; j < multipleKitchen.size(); j++) {

                if (pairs.get(i).getPairID().equals(multipleKitchen.get(j).getPairID())) {
                    pairs.remove(i);
                }
            }
        }

        moveUpPairs.addAll(multipleKitchen);

        List<Pair> pairsMaxSize = new ArrayList<>();
        int counter = 0;

        if (assignGroupsToDishes) {

            maxPairSize = (int) Math.floor(maximalPersonAmount / 2);

            for (int i = 0; i < pairs.size(); i++) {
                if (i > maxPairSize - 1) {
                    break;
                }
                counter++;
                pairsMaxSize.add(pairs.get(i));

            }

            moveUpPairs.addAll(pairs.subList(counter, pairs.size()));
        } else {
            pairsMaxSize.addAll(pairs);
        }

        List<Pair> none = new ArrayList<>();
        List<Pair> meat = new ArrayList<>();
        List<Pair> vegan = new ArrayList<>();
        List<Pair> veggie = new ArrayList<>();


        for (int i = 0; i < pairsMaxSize.size(); i++) {

            if (pairsMaxSize.get(i).getFoodPreference() == EFoodPreference.NONE) {

                none.add(pairsMaxSize.get(i));
            } else if (pairsMaxSize.get(i).getFoodPreference() == EFoodPreference.MEAT) {

                meat.add(pairsMaxSize.get(i));
            } else if (pairsMaxSize.get(i).getFoodPreference() == EFoodPreference.VEGAN) {

                vegan.add(pairsMaxSize.get(i));
            } else if (pairsMaxSize.get(i).getFoodPreference() == EFoodPreference.VEGGIE) {

                veggie.add(pairsMaxSize.get(i));
            }

        }


        List<List<Pair>> allPreferencePairs = new ArrayList<>(List.of(none, meat, vegan, veggie));
        allGroups = new ArrayList<>();

        for (int i = 0; i < allPreferencePairs.size(); i++) {

            ListAndCutElements cutListMultiple = cutListIfNotMultipleOfNine(allPreferencePairs.get(i), 9);
            allGroups.addAll(getGroups(cutListMultiple.getCutList()));
            moveUpPairs.addAll(cutListMultiple.getCutElements());
        }


        if (assignGroupsToDishes) {

            assignToMeal(allGroups);
        }

/*
        for(int i = 0; i < main.size(); i++) {
            System.out.println("--------------------");

                for(int j = 0; j < main.get(i).getPairs().size(); j++) {
                    System.out.println(main.get(i).getPairs().get(j).getPairID());
                }

            System.out.println("--------------------");
        }
*/


/*
        for(int i = 0; i < main.size(); i++) {
            for(int j = 0; j < main.get(i).getPairs().size(); j++) {

                main.get(i).getPairs().get(j).getPerson1().getKitchen().kitchen = data.EHasKitchen.NO;
                main.get(i).getPairs().get(j).getPerson2().getKitchen().kitchen = data.EHasKitchen.NO;
            }
        }
*/


    }

    /**
     * Converts a list of pairs into groups.
     * Each group consists of three pairs, with a designated cook for each group.
     *
     * @param pairs the list of pairs to convert into groups
     * @return the list of groups created from the pairs
     */
    private List<Group> getGroups(List<Pair> pairs) {

        List<Group> groups = new ArrayList<>();

        for (int c = 0; c < pairs.size(); c += 9) {
            groups.addAll(getGroupsSub(pairs.subList(c, c + 9)));
        }

        return groups;
    }

    /**
     * Converts a sublist of pairs into groups.
     * Each group consists of three pairs, with a designated cook for each group.
     *
     * @param pairs the sublist of pairs to convert into groups
     * @return the list of groups created from the pairs
     */
    private List<Group> getGroupsSub(List<Pair> pairs) {

        List<Group> groups = new ArrayList<>();

        // 0 cook
        Group groupOne = new Group(pairs.get(0), pairs.get(1), pairs.get(2), MealType.STARTER);
        groupOne.setCookID(pairs.get(0).getPairID());
        groupOne.setGroupKitchen(pairs.get(0).getKitchen());
        pairs.get(0).setHasCooked(true);

        // 5 cook
        Group groupTwo = new Group(pairs.get(5), pairs.get(3), pairs.get(4), MealType.STARTER);
        groupTwo.setCookID(pairs.get(5).getPairID());
        groupTwo.setGroupKitchen(pairs.get(5).getKitchen());
        pairs.get(5).setHasCooked(true);

        // 8 cook
        Group groupThree = new Group(pairs.get(8), pairs.get(7), pairs.get(6), MealType.STARTER);
        groupThree.setCookID(pairs.get(8).getPairID());
        groupThree.setGroupKitchen(pairs.get(8).getKitchen());
        pairs.get(8).setHasCooked(true);

        groups.add(groupOne);
        groups.add(groupTwo);
        groups.add(groupThree);

        // 3 cook
        Group groupFour = new Group(pairs.get(3), pairs.get(0), pairs.get(6), MealType.MAIN);
        groupFour.setCookID(pairs.get(3).getPairID());
        groupFour.setGroupKitchen(pairs.get(3).getKitchen());
        pairs.get(3).setHasCooked(true);

        // 1 cook
        Group groupFive = new Group(pairs.get(1), pairs.get(4), pairs.get(7), MealType.MAIN);
        groupFive.setCookID(pairs.get(1).getPairID());
        groupFive.setGroupKitchen(pairs.get(1).getKitchen());
        pairs.get(1).setHasCooked(true);

        // 2 cook

        Group groupSix = new Group(pairs.get(2), pairs.get(5), pairs.get(8), MealType.MAIN);
        groupSix.setCookID(pairs.get(2).getPairID());
        groupSix.setGroupKitchen(pairs.get(2).getKitchen());
        pairs.get(2).setHasCooked(true);

        groups.add(groupFour);
        groups.add(groupFive);
        groups.add(groupSix);

        // 4 cook
        Group groupSeven = new Group(pairs.get(4), pairs.get(0), pairs.get(8), MealType.DESSERT);
        groupSeven.setCookID(pairs.get(4).getPairID());
        groupSeven.setGroupKitchen(pairs.get(4).getKitchen());
        pairs.get(4).setHasCooked(true);

        // 6 cook

        Group groupEight = new Group(pairs.get(6), pairs.get(5), pairs.get(1), MealType.DESSERT);
        groupEight.setCookID(pairs.get(6).getPairID());
        groupEight.setGroupKitchen(pairs.get(6).getKitchen());
        pairs.get(6).setHasCooked(true);

        // 7 cook

        Group groupNine = new Group(pairs.get(7), pairs.get(3), pairs.get(2), MealType.DESSERT);
        groupNine.setCookID(pairs.get(7).getPairID());
        groupNine.setGroupKitchen(pairs.get(7).getKitchen());
        pairs.get(7).setHasCooked(true);

        groups.add(groupSeven);
        groups.add(groupEight);
        groups.add(groupNine);

        PathCalculator.calculatePairPathLength(pairs, groups, afterParty);

        return groups;
    }

    /**
     * Retrieves the groups that are assigned the specified pair.
     *
     * @param pair The pair to find the assigned groups for.
     * @return A list of groups assigned to the pair.
     */
    public List<Group> getPairAssignedGroups(Pair pair) {

        List<List<Group>> allGroups = new ArrayList<>(List.of(this.starter, this.main, this.dessert));
        List<Group> pairAssignedGroups = new ArrayList<>();

        allGroups.stream()
                .flatMap(innerList -> innerList.stream())
                .forEach(el -> {

                    List<Pair> pairs = new ArrayList<>(el.getPairs());

                    pairs.forEach(x -> {

                        if (x.getPairID().equals(pair.getPairID())) {
                            pairAssignedGroups.add(el);
                        }
                    });
                });

        return pairAssignedGroups;
    }

    /**
     * Performs event cancellation for a specific pair and handles replacement logic.
     *
     * @param pair                  the pair to cancel the event for
     * @param replacePersonList     the replacement person list
     * @param replacePairList       the replacement pair list
     * @param pairCriteria          the pair criteria factor
     */
    public void peopleEventCancel(Pair pair, List<Person> replacePersonList, List<Pair> replacePairList, PairCriteriaFactor pairCriteria){

        List<Group> pairAssignedGroups = getPairAssignedGroups(pair);

        if (!pair.isSignedInTogether()) {

            if (pair.getPerson1().isHasCanceledEvent()) {
                singlePersonEventCancel(pair, pair.getPerson2(), replacePersonList, pairAssignedGroups);
            } else if (pair.getPerson2().isHasCanceledEvent()) {

                singlePersonEventCancel(pair, pair.getPerson1(), replacePersonList, pairAssignedGroups);
            }
        }

        if (pair.isSignedInTogether()) {

            if (pair.getPerson1().isHasCanceledEvent() && pair.getPerson2().isHasCanceledEvent()) {
                pairEventCancel(pair, replacePersonList, replacePairList, pairAssignedGroups, pairCriteria);

            } else if (pair.getPerson1().isHasCanceledEvent()) {

                singlePersonEventCancel(pair, pair.getPerson2(), replacePersonList, pairAssignedGroups);
            } else if (pair.getPerson2().isHasCanceledEvent()) {

                singlePersonEventCancel(pair, pair.getPerson1(), replacePersonList, pairAssignedGroups);
            }
        }

        int count = extractPairs(this.starter).size();

        if (moveUpPairs.size() > 9 && count < maxPairSize) {

            createGroups(moveUpPairs, moveUpPairs.size(), false, moveUpPersons, afterParty);

            int subtract = maxPairSize - count;
            int result = (int) Math.floor(subtract / 9);

            List<Group> groups = new ArrayList<>();

            while (this.allGroups.size() < (9 * result)) {
                result--;
            }

            for (int i = 0; i < result * 9; i++) {
                groups.add(this.allGroups.get(i));
            }

            moveUpPairs.addAll(extractPairs(this.allGroups.subList(result * 9, this.allGroups.size())));
            assignToMeal(groups);
        }
    }

    /**
     * Extracts unique pairs from a list of groups.
     *
     * @param allGroupsAsList the list of groups
     * @return the list of extracted unique pairs
     */
    public List<Pair> extractPairs(List<Group> allGroupsAsList) {

        List<Pair> extractedPairs = new ArrayList<>();

        for (int i = 0; i < allGroupsAsList.size(); i++) {
            for (int j = 0; j < allGroupsAsList.get(i).getPairs().size(); j++) {
                boolean found = false;

                for (int k = 0; k < extractedPairs.size(); k++) {
                    if (allGroupsAsList.get(i).getPairs().get(j).getPairID().equals(extractedPairs.get(k).getPairID())) {
                        found = true;
                        break; // Beende die Schleife, da eine Übereinstimmung gefunden wurde
                    }
                }

                if (!found) {
                    extractedPairs.add(allGroupsAsList.get(i).getPairs().get(j));
                }
            }
        }

        return extractedPairs;
    }

    /**
     * Handles event cancellation when only one person from a pair cancels the event.
     *
     * @param pair               the pair involved in the cancellation
     * @param person             the person who cancels the event
     * @param replacePersonList  the replacement person list
     * @param pairAssignedGroups the list of groups assigned to the pair
     */
    private void singlePersonEventCancel(Pair pair, Person person, List<Person> replacePersonList, List<Group> pairAssignedGroups){

        Pair newPair = null;

        for (int i = 0; i < replacePersonList.size(); i++) {

            if (replacePersonList.get(i).getFoodPreference() == pair.getFoodPreference()) {

                if (PairAssigner.checkKitchenAvailability(person, replacePersonList.get(i))) {

                    newPair = new Pair(person, replacePersonList.get(i));
                    break;
                }
            }
        }

        if (newPair != null) {

            for(int i = moveUpPersons.size() - 1; i >= 0; i--) {

                if(moveUpPersons.get(i).getUUID().equals(newPair.getPerson2().getUUID())) {
                    moveUpPersons.remove(i);
                }
            }

            for (int i = 0; i < pairAssignedGroups.size(); i++) {
                if (pairAssignedGroups.get(i).getCookID().equals(pair.getPairID())) {
                    pairAssignedGroups.get(i).setCookID(newPair.getPairID());
                    pairAssignedGroups.get(i).setGroupKitchen(newPair.getKitchen());
                }
            }

            newPair.setHasCooked(true);
            newPair.setSignedInTogether(false);

            if (newPair.getPerson1().getKitchen().getHasKitchen() == EHasKitchen.YES) {
                newPair.setKitchen(newPair.getPerson1().getKitchen());
            } else if (newPair.getPerson2().getKitchen().getHasKitchen() == EHasKitchen.YES) {
                newPair.setKitchen(newPair.getPerson2().getKitchen());
            }

            PathCalculator.calculateNewPathLength(replaceOldPairWithNewPair(pairAssignedGroups, pair, newPair), List.of(this.starter, this.main, this.dessert).stream().flatMap(x -> x.stream()).collect(Collectors.toList()), afterParty);
        } else {

            removeGroupsLinkedToPair(pairAssignedGroups);

            for(int i = moveUpPairs.size() - 1; i >= 0; i--) {
                if(moveUpPairs.get(i).getPairID().equals(pair.getPairID())) {
                    moveUpPairs.remove(i);
                }
            }

            Person moveUpPerson = null;

            if(person.getUUID().equals(pair.getPerson1().getUUID())) {
                moveUpPerson = pair.getPerson1();
            }
            else if(person.getUUID().equals(pair.getPerson2().getUUID())) {
                moveUpPerson = pair.getPerson2();
            }

            this.moveUpPersons.add(moveUpPerson);
        }
    }

    /**
     * Handles event cancellation when both persons from a pair cancel the event.
     *
     * @param pair                  the pair involved in the cancellation
     * @param personReplacementList the replacement person list
     * @param pairsReplacementList  the replacement pair list
     * @param pairAssignedGroups    the list of groups assigned to the pair
     * @param pairCriteria          the pair criteria factor
     */
    private void pairEventCancel(Pair pair, List<Person> personReplacementList, List<Pair> pairsReplacementList, List<Group> pairAssignedGroups, PairCriteriaFactor pairCriteria){

        Pair newPair = null;

        for (int i = 0; i < pairsReplacementList.size(); i++) {

            if (pairsReplacementList.get(i).getFoodPreference() == pair.getFoodPreference()) {

                newPair = pairsReplacementList.get(i);

                removePairFromMoveUpList(newPair);
                break;
            }
        }

        if (newPair == null) {

            PairAssigner pairs = new PairAssigner(personReplacementList, pairCriteria);

            List<Pair> createdPairs = pairs.getCreatedPairs();

            if (pairs.getCreatedPairs().size() > 0) {

                for (int i = 0; i < createdPairs.size(); i++) {

                    if (createdPairs.get(i).getFoodPreference() == pair.getFoodPreference()) {
                        newPair = createdPairs.get(i);
                        newPair.setSignedInTogether(false);

                        removePersonsFromMoveUpList(List.of(newPair.getPerson1(), newPair.getPerson2()));
                        break;
                    }
                }
            }
        }

        if (newPair != null) {

            for (int i = 0; i < pairAssignedGroups.size(); i++) {
                if (pairAssignedGroups.get(i).getCookID().equals(pair.getPairID())) {
                    pairAssignedGroups.get(i).setCookID(newPair.getPairID());
                    pairAssignedGroups.get(i).setGroupKitchen(newPair.getKitchen());
                }
            }

            newPair.setHasCooked(true);
            newPair.setSignedInTogether(false);

            PathCalculator.calculateNewPathLength(replaceOldPairWithNewPair(pairAssignedGroups, pair, newPair), List.of(this.starter, this.main, this.dessert).stream().flatMap(x -> x.stream()).collect(Collectors.toList()), afterParty);

            for (int i = 0; i < pairAssignedGroups.size(); i++) {
                pairAssignedGroups.get(i).createNewGroupID(pairAssignedGroups.get(i).getPair1(), pairAssignedGroups.get(i).getPair2(), pairAssignedGroups.get(i).getPair3());
            }
        } else {

            removeGroupsLinkedToPair(pairAssignedGroups);
            removePairFromMoveUpList(pair);
        }
    }

    /**
     * Replaces an old pair with a new pair in the assigned groups.
     *
     * @param pairAssignedGroups the list of groups assigned to pairs
     * @param oldPair            the old pair to be replaced
     * @param newPair            the new pair to replace the old pair
     */
    private List<Group> replaceOldPairWithNewPair(List<Group> pairAssignedGroups, Pair oldPair, Pair newPair){

        for (int i = 0; i < pairAssignedGroups.size(); i++) {

            for (int j = 0; j < pairAssignedGroups.get(i).getPairs().size(); j++) {

                if (pairAssignedGroups.get(i).getPairs().get(j).getPairID().equals(oldPair.getPairID())) {

                    if (j == 0) {
                        pairAssignedGroups.get(i).setPair1(newPair);
                    } else if (j == 1) {
                        pairAssignedGroups.get(i).setPair2(newPair);
                    } else if (j == 2) {
                        pairAssignedGroups.get(i).setPair3(newPair);
                    }
                }
            }
        }

        return pairAssignedGroups;
    }

    /**
     * Removes groups that are linked to a specific pair from the assigned groups.
     *
     * @param pairAssignedGroups the list of groups assigned to pairs
     */
    public void removeGroupsLinkedToPair(List<Group> pairAssignedGroups) {

        List<Pair> pairIDToRemove = new ArrayList<>();
        List<Group> moveUpGroups = new ArrayList<>();
        List<Pair> pairsToMoveUp = new ArrayList<>();

        for (int i = 0; i < pairAssignedGroups.size(); i++) {
            pairIDToRemove.addAll(pairAssignedGroups.get(i).getPairs());
        }

        moveUpGroups.addAll(removeGroups(starter, pairIDToRemove));
        moveUpGroups.addAll(removeGroups(main, pairIDToRemove));
        moveUpGroups.addAll(removeGroups(dessert, pairIDToRemove));


        for (int i = 0; i < moveUpGroups.size(); i++) {
            pairsToMoveUp.addAll(moveUpGroups.get(i).getPairs());
        }

        moveUpPairs.addAll(pairsToMoveUp.stream()
                .distinct().toList());
    }

    /**
     * Removes groups from a list based on the pair IDs to remove.
     *
     * @param list            the list of groups
     * @param pairIDToRemove  the pair IDs to remove
     * @return the list of removed groups
     */
    private List<Group> removeGroups(List<Group> list, List<Pair> pairIDToRemove){

        List<Group> pMoveUp = new ArrayList<>();

        for (int i = list.size() - 1; i >= 0; i--) {
            for (int j = list.get(i).getPairs().size() - 1; j >= 0; j--) {
                for (int h = pairIDToRemove.size() - 1; h >= 0; h--) {
                    if (list.get(i).getPairs().get(j).getPairID().equals(pairIDToRemove.get(h).getPairID())) {
                        pMoveUp.add(list.get(i));
                        break;
                    }
                }
            }
        }

        list.removeAll(pMoveUp);

        return pMoveUp;
    }

    /**
     * Checks for pairs with the same kitchen coordinates and marks them as unavailable.
     * Pairs with the same kitchen coordinates are considered to have multiple kitchens.
     *
     * @param pairs the list of pairs to check
     * @return the list of pairs with the same kitchen coordinates
     */
    private List<Pair> checkForMultipleKitchen(List<Pair> pairs) {

        List<Pair> pairsWithSameKitchen = new ArrayList<>();

        for (int i = 0; i < pairs.size(); i++) {

            Pair currentPair = pairs.get(i);

            if (currentPair.getAvailability()) {

                for (int j = 0; j < pairs.size(); j++) {

                    Pair secondPair = pairs.get(j);

                    if (secondPair.getAvailability()) {

                        if (currentPair != secondPair) {

                            if (Objects.equals(currentPair.getKitchen().getKitchenLongitude(), secondPair.getKitchen().getKitchenLongitude()) &&
                                    Objects.equals(currentPair.getKitchen().getkitchenLatitude(), secondPair.getKitchen().getkitchenLatitude())) {

                                double currentPairStory = 0;
                                double secondPairStory = 0;

                                if (currentPair.getPerson1().getKitchen().getHasKitchen() == EHasKitchen.YES) {
                                    currentPairStory = currentPair.getPerson1().getKitchen().getKitchenStory();
                                } else if (currentPair.getPerson2().getKitchen().getHasKitchen() == EHasKitchen.YES) {
                                    currentPairStory = currentPair.getPerson2().getKitchen().getKitchenStory();
                                }

                                if (secondPair.getPerson1().getKitchen().getHasKitchen() == EHasKitchen.YES) {
                                    secondPairStory = secondPair.getPerson1().getKitchen().getKitchenStory();
                                } else if (secondPair.getPerson2().getKitchen().getHasKitchen() == EHasKitchen.YES) {
                                    secondPairStory = secondPair.getPerson2().getKitchen().getKitchenStory();
                                }

                                if (currentPairStory == secondPairStory) {
                                    pairsWithSameKitchen.add(secondPair);
                                    secondPair.setAvailability(false);
                                }
                            }
                        }
                    }
                }

                currentPair.setAvailability(false);
            }
        }

        for (int i = 0; i < pairs.size(); i++) {
            pairs.get(i).setAvailability(true);
        }

        return pairsWithSameKitchen;
    }

    /**
     * Cuts a list into a smaller list and returns the remaining elements.
     *
     * @param list      the list to cut
     * @param sizeCut   the size to cut the list
     * @return an object containing the cut list and the remaining elements
     */
    private ListAndCutElements cutListIfNotMultipleOfNine(List<Pair> list,int sizeCut){
        int size = list.size();
        if (size % sizeCut == 0) {
            return new ListAndCutElements(list, new ArrayList<>()); // Kein Schneiden erforderlich
        }
        int newSize = size - (size % sizeCut);
        List<Pair> cutList = list.subList(0, newSize);
        List<Pair> cutElements = list.subList(newSize, size);
        return new ListAndCutElements(cutList, cutElements);
    }

    /**
     * Retrieves the list of pairs that can be moved up to the next round of group assignment.
     *
     * @return the list of pairs to move up
     */
    public List<Pair> getMoveUpPairs() {

        return moveUpPairs;
    }

    /**
     * Removes the specified pair from the list of pairs to move up.
     *
     * @param pair the pair to be removed
     */
    private void removePairFromMoveUpList(Pair pair){

        for (int i = moveUpPairs.size() - 1; i >= 0; i--) {

            if (moveUpPairs.get(i).getPairID().equals(pair.getPairID())) {

                moveUpPairs.remove(i);
            }
        }
    }

    /**
     * Removes the specified persons from the move-up list.
     *
     * @param persons The list of persons to remove.
     */
    private void removePersonsFromMoveUpList(List<Person> persons){

        for(int i = moveUpPersons.size() - 1; i >= 0; i--) {

            for(int j = persons.size(); j >= 0; j--) {
                if(moveUpPersons.get(i).getUUID().equals(persons.get(j).getUUID())) {
                    moveUpPersons.remove(i);
                    break;
                }
            }
        }
    }

    /**
     * Assigns the specified list of groups to the appropriate meal category (starter, main, dessert).
     *
     * @param list the list of groups to assign to meals
     */
    private void assignToMeal(List<Group> list) {
        for (int j = 0; j < list.size(); j++) {

            if (list.get(j).getMealType() == MealType.STARTER) {

                starter.add(list.get(j));
            } else if (list.get(j).getMealType() == MealType.MAIN) {

                main.add(list.get(j));
            } else if (list.get(j).getMealType() == MealType.DESSERT) {

                dessert.add(list.get(j));
            }
        }
    }

    /**
     * Gets the list of move-up persons.
     *
     * @return The list of move-up persons.
     */
    public List<Person> getMoveUpPersons() {
        return this.moveUpPersons;
    }


    /**
     * Sets the move-up pairs.
     *
     * @param pairs The move-up pairs to set.
     */
    public void setMoveUpPairs(List<Pair> pairs) {
        this.moveUpPairs = pairs;
    }

    /**
     * Sets the move-up persons.
     *
     * @param persons The move-up persons to set.
     */
    public void setMoveUpPersons(List<Person> persons) {
        this.moveUpPersons = persons;
    }
}





