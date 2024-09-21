package controller.json;

import com.google.gson.*;
import controller.Group;
import controller.MealType;
import controller.PathCalculator;
import data.Coordinate;
import data.EHasKitchen;
import data.Pair;
import data.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading JSON data.
 */
public class JsonReader {

    /**
     * Constructs a new JsonReader object.
     */
    public JsonReader() {
        // Default constructor
    }

    /**
     * Reads pairs from a JSON string.
     *
     * @param json        The JSON string containing the pairs.
     * @param memberName  The name of the JSON array member that contains the pairs.
     * @return            A list of Pair objects read from the JSON string.
     */
    public static List<Pair> readPairs(String json, String memberName) {

        JsonElement jsonElement = JsonParser.parseString(json);
        return buildPairs(jsonElement.getAsJsonObject().getAsJsonArray(memberName));
    }

    /**
     * Builds pairs from a JSON array.
     *
     * @param jsonArray The JSON array containing the pair objects.
     * @return A list of Pair objects built from the JSON array.
     */
    public static List<Pair> buildPairs(JsonArray jsonArray) {

        List<Pair> allPairs = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {

            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

            Person person1 = buildPerson(jsonObject.get("firstParticipant"));
            Person person2 = buildPerson(jsonObject.get("secondParticipant"));

            Pair newPair = new Pair(person1, person2);

            if(person1.getKitchen().getHasKitchen() == EHasKitchen.YES) {
                newPair.setKitchen(person1.getKitchen());
            }
            else if(person2.getKitchen().getHasKitchen() == EHasKitchen.YES) {
                newPair.setKitchen(person2.getKitchen());
            }

            allPairs.add(newPair);
        }

        return allPairs;
    }

    /**
     * Reads successor participants from a JSON string.
     *
     * @param json  The JSON string containing the successor participants.
     * @return      A list of Person objects representing the successor participants.
     */
    public static List<Person> readSuccessorParticipants(String json) {

        JsonElement jsonElement = JsonParser.parseString(json);
        JsonArray jsonArray = jsonElement.getAsJsonObject().getAsJsonArray("successorParticipants");

        List<Person> allPersons = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            Person person = buildPerson(jsonObject);
            allPersons.add(person);
        }

        return allPersons;
    }

    /**
     * Reads groups from a JSON string.
     *
     * @param json        The JSON string containing the groups.
     * @param afterParty  The coordinate representing the location of the after-party.
     * @return            A list of Group objects representing the groups.
     */
    public static List<Group> readGroups(String json, Coordinate afterParty) {

        JsonElement jsonElement = JsonParser.parseString(json);
        JsonArray jsonArray = jsonElement.getAsJsonObject().getAsJsonArray("groups");
        List<Group> allGroups = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {

            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

            JsonElement pair1 = jsonObject.get("cookingPair");
            JsonElement pair2 = jsonObject.get("secondPair");
            JsonElement pair3 = jsonObject.get("thirdPair");

            Pair newPair1 = new Pair(buildPerson(pair1.getAsJsonObject().get("firstParticipant")), buildPerson(pair1.getAsJsonObject().get("secondParticipant")));
            Pair newPair2 = new Pair(buildPerson(pair2.getAsJsonObject().get("firstParticipant")), buildPerson(pair2.getAsJsonObject().get("secondParticipant")));
            Pair newPair3 = new Pair(buildPerson(pair3.getAsJsonObject().get("firstParticipant")), buildPerson(pair3.getAsJsonObject().get("secondParticipant")));

            List<Pair> groupPairs = new ArrayList<>(List.of(newPair1, newPair2, newPair3));

            for(int j = 0; j < groupPairs.size(); j++) {

                if(groupPairs.get(j).getPerson1().getKitchen().getHasKitchen() == EHasKitchen.YES) {
                    groupPairs.get(j).setKitchen(groupPairs.get(j).getPerson1().getKitchen());
                }
                else {
                    groupPairs.get(j).setKitchen(groupPairs.get(j).getPerson2().getKitchen());
                }
            }

            Group newGroup = new Group(newPair1, newPair2, newPair3, transformMealToEnum(jsonObject.get("course").getAsString()));
            newGroup.setCookID(newPair1.getPairID());
            allGroups.add(newGroup);
        }

        for(int i = 0; i < allGroups.size(); i++) {
            System.out.println(getPairAssignedGroups(allGroups.get(i).getPairs().get(0), allGroups).size());
            PathCalculator.calculateNewPathLength(getPairAssignedGroups(allGroups.get(i).getPairs().get(0), allGroups), allGroups, afterParty);
        }

        return allGroups;
    }

    /**
     * Reads successor pairs from a JSON string.
     *
     * @param json  The JSON string containing the successor pairs.
     * @return      A list of Pair objects representing the successor pairs.
     */

    public static List<Pair> readSuccessorPairs(String json) {

        JsonElement jsonElement = JsonParser.parseString(json);
        JsonArray jsonArray = jsonElement.getAsJsonObject().getAsJsonArray("successorPairs");

        List<Pair> allPairs = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {

            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

            Person person1 = buildPerson(jsonObject.get("firstParticipant"));
            Person person2 = buildPerson(jsonObject.get("secondParticipant"));

            Pair newPair = new Pair(person1, person2);

            if(person1.getKitchen().getHasKitchen() == EHasKitchen.YES) {
                newPair.setKitchen(person1.getKitchen());
            }
            else if(person2.getKitchen().getHasKitchen() == EHasKitchen.YES) {
                newPair.setKitchen(person2.getKitchen());
            }

            allPairs.add(newPair);
        }

        return allPairs;

    }

    /**
     * Builds a Person object from a JsonElement.
     *
     * @param person  The JsonElement representing the person.
     * @return        A Person object built from the JsonElement.
     */
    public static Person buildPerson(JsonElement person) {

        String[] personOneData = new String[10];

        personOneData[1] = person.getAsJsonObject().get("id").getAsString();
        personOneData[2] = person.getAsJsonObject().get("name").getAsString();
        personOneData[3] = person.getAsJsonObject().get("foodPreference").getAsString();
        personOneData[4] = person.getAsJsonObject().get("age").getAsString();
        personOneData[5] = person.getAsJsonObject().get("gender").getAsString();

        if (person.getAsJsonObject().has("kitchen")) {

            JsonElement kitchen = person.getAsJsonObject().get("kitchen");

            if (!kitchen.isJsonNull()) {
                personOneData[6] = "yes";

                JsonObject kitchenObj = kitchen.getAsJsonObject();

                if (kitchenObj.has("story") && !kitchenObj.get("story").isJsonNull()) {
                    personOneData[7] = kitchenObj.get("story").getAsString();
                }
                else {
                    personOneData[7] = "";
                }

                if (kitchenObj.has("longitude") && !kitchenObj.get("longitude").isJsonNull()) {
                    personOneData[8] = kitchenObj.get("longitude").getAsString();
                }

                if (kitchenObj.has("latitude") && !kitchenObj.get("latitude").isJsonNull()) {
                    personOneData[9] = kitchenObj.get("latitude").getAsString();
                }
            } else {
                personOneData[6] = "no";
                personOneData[7] = "";
                personOneData[8] = "";
                personOneData[9] = "";
            }
        }

        return new Person(personOneData);
    }

    /**
     * Transforms a meal string into a MealType enum.
     *
     * @param meal The string representation of the meal.
     * @return The corresponding MealType enum value.
     */
    public static MealType transformMealToEnum(String meal) {

        return switch (meal) {
            case "first" -> MealType.STARTER;
            case "main" -> MealType.MAIN;
            case "dessert" -> MealType.DESSERT;
            default -> null;
        };
    }

    /**
     * Retrieves the groups that a pair is assigned to.
     *
     * @param pair   The pair to find assigned groups for.
     * @param groups The list of groups to search.
     * @return A list of Group objects that the pair is assigned to.
     */
    public static List<Group> getPairAssignedGroups(Pair pair, List<Group> groups) {

        List<Group> allGroups = new ArrayList<>(groups);
        List<Group> pairAssignedGroups = new ArrayList<>();

        allGroups.stream()
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
}
