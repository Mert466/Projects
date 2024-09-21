package controller;

import data.Coordinate;
import data.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PathCalculator class calculates the path length for pairs and groups based on coordinates.
 */
public class PathCalculator {

    /**
     * Calculates the new path length for pairs based on assigned groups, all groups, and after-party coordinates.
     *
     * @param pairAssignedGroups     the list of assigned groups for pairs
     * @param allGroups              the list of all groups
     * @param afterPartyCoordinates  the coordinates of the after-party location
     */
    public static void calculateNewPathLength(List<Group> pairAssignedGroups, List<Group> allGroups, Coordinate afterPartyCoordinates) {

        List<Pair> pairIDToRemove = new ArrayList<>();
        List<Pair> pairsToMoveUp = new ArrayList<>();

        for(int i = 0; i < pairAssignedGroups.size(); i++) {

            pairIDToRemove.addAll(pairAssignedGroups.get(i).getPairs());
        }

        List<Group> moveUpGroups = new ArrayList<>(getGroups(allGroups, pairIDToRemove).stream().distinct().toList());

        for(int i = 0; i < moveUpGroups.size(); i++) {
            pairsToMoveUp.addAll(moveUpGroups.get(i).getPairs());
        }

        calculatePairPathLength(pairsToMoveUp.stream()
                .distinct().toList(), moveUpGroups, afterPartyCoordinates);
    }

    /**
     * Retrieves the groups containing the pairs to be removed.
     *
     * @param list              the list of all groups
     * @param pairIDToRemove    the list of pairs to be removed
     * @return the groups containing the pairs to be removed
     */
    public static List<Group> getGroups(List<Group> list, List<Pair> pairIDToRemove) {

        List<Group> pMoveUp = new ArrayList<>();

        for (int i = list.size() - 1; i >= 0; i--) {
            for (int j = list.get(i).getPairs().size() - 1; j >= 0; j--) {
                for (int h = 0; h < pairIDToRemove.size(); h++) {
                    if (list.get(i).getPairs().get(j).getPairID().equals(pairIDToRemove.get(h).getPairID())) {
                        pMoveUp.add(list.get(i));
                        break;
                    }
                }
            }
        }

        return pMoveUp;
    }

    /**
     * Calculates the path length for pairs based on groups and after-party coordinates.
     *
     * @param pairs          the list of pairs
     * @param groups         the list of groups
     * @param afterParty     the coordinates of the after-party location
     */
    public static void calculatePairPathLength(List<Pair> pairs, List<Group> groups, Coordinate afterParty){

        for (int i = 0; i < pairs.size(); i++) {

            Pair currentPair = pairs.get(i);

            List<Group> pairRoute = new ArrayList<>();
            List<String> cookIDs = new ArrayList<>();
            List<Pair> cooks = new ArrayList<>();

            for (int j = 0; j < groups.size(); j++) {

                for (int k = 0; k < groups.get(j).getPairs().size(); k++) {

                    if (groups.get(j).getPairs().get(k).getPairID().equals(currentPair.getPairID())) {
                        pairRoute.add(groups.get(j));
                    }
                }
            }

            for (int j = 0; j < pairRoute.size(); j++) {
                cookIDs.add(pairRoute.get(j).getCookID());
            }

            for (int j = 0; j < cookIDs.size(); j++) {

                for (int k = 0; k < pairs.size(); k++) {

                    if (cookIDs.get(j).equals(pairs.get(k).getPairID())) {
                        cooks.add(pairs.get(k));
                    }
                }
            }


            currentPair.setPathLength(calculateDistance(cooks.get(0).getKitchen().getkitchenLatitude(), cooks.get(0).getKitchen().getKitchenLongitude(),
                    cooks.get(1).getKitchen().getkitchenLatitude(), cooks.get(1).getKitchen().getKitchenLongitude()) + calculateDistance(cooks.get(1).getKitchen().getkitchenLatitude(), cooks.get(1).getKitchen().getKitchenLongitude(),
                    cooks.get(2).getKitchen().getkitchenLatitude(), cooks.get(2).getKitchen().getKitchenLongitude()) + calculateDistance(cooks.get(2).getKitchen().getkitchenLatitude(), cooks.get(2).getKitchen().getKitchenLongitude(),
                    afterParty.getLatitude(), afterParty.getLongtitude()));
        }
    }

    /**
     * Calculates the distance between two sets of coordinates using the Haversine formula.
     *
     * @param lat1  the latitude of the first coordinate
     * @param lon1  the longitude of the first coordinate
     * @param lat2  the latitude of the second coordinate
     * @param lon2  the longitude of the second coordinate
     * @return the distance between the two coordinates in kilometers
     */
    public static double calculateDistance(double lat1, double lon1, double lat2,
                                           double lon2) {
        double earthRadius = 6371; // Radius der Erde in Kilometern

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = earthRadius * c;

        return distance;
    }
}
