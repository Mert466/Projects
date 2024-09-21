package controller.json;

import com.google.gson.*;
import controller.*;
import data.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.nio.file.*;

/**
 * The controller.json.JsonCreator class is responsible for creating and modifying JSON objects and files.
 * It provides methods to generate JSON representations of pairs, persons, groups, and the overall data structure.
 * Additionally, it includes a method to modify an existing JSON file.
 */
public class JsonCreator {

    private final Gson gson;

    /**
     * Constructs a controller.json.JsonCreator object and initializes the Gson instance with appropriate settings.
     */
    public JsonCreator() {
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapter(MealType.class, new MealTypeSerializer())
                .registerTypeAdapter(EFoodPreference.class, new LowerCaseSerializer<>())
                .registerTypeAdapter(EGender.class, new LowerCaseSerializer<>())
                .create();
    }

    /**
     * Modifies a JSON element based on custom rules.
     *
     * @param element the JSON element to be modified.
     * @return the modified JSON element.
     */
    private static JsonElement modifyJsonElement(JsonElement element) {

        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            JsonObject newObj = new JsonObject();
            for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                newObj.add(entry.getKey(), modifyJsonElement(entry.getValue()));
            }
            return newObj;
        } else if (element.isJsonArray()) {
            JsonArray arr = element.getAsJsonArray();
            JsonArray newArr = new JsonArray();
            for (JsonElement arrElement : arr) {
                newArr.add(modifyJsonElement(arrElement));
            }
            return newArr;
        } else if (element.isJsonPrimitive()) {
            JsonPrimitive value = element.getAsJsonPrimitive();
            if (value.isString()) {
                String strVal = value.getAsString();
                if (strVal.equals("YES")) {
                    return new JsonPrimitive(true);
                } else if (strVal.equals("NO")) {
                    return new JsonPrimitive(false);
                } else if (strVal.equals("MAYBE")) {
                    return JsonNull.INSTANCE;
                } else if (strVal.equals(strVal.toUpperCase())) {
                    return new JsonPrimitive(strVal.toLowerCase());
                }
            }
        }
        return element;
    }

    /**
     * Modifies a JSON file at the specified path by applying custom modifications to its contents.
     *
     * @param filePath the path of the JSON file to be modified.
     */
    public static void modifyJsonFile(String filePath) {
        try {
            String jsonString = Files.readString(Paths.get(filePath));
            JsonElement jsonElement = JsonParser.parseString(jsonString);
            JsonElement modifiedJson = modifyJsonElement(jsonElement);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = gson.toJson(modifiedJson);

            Files.writeString(Paths.get(filePath), prettyJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a JSON object from the provided data and writes it to a file.
     *
     * @param allPairs              The list of pairs.
     * @param replacementListPersons The list of replacement persons.
     * @param replacementListPairs   The list of replacement pairs.
     * @param allGroups             The list of groups.
     * @return                      The JSON string representation of the created JSON object.
     */
    public String createJSON(List<Pair> allPairs, List<Person> replacementListPersons, List<Pair> replacementListPairs, List<Group> allGroups) {

        JsonCreator jsonCreator = new JsonCreator();
        JsonObject jsonObject = jsonCreator.createJsonObject(allPairs, replacementListPersons, replacementListPairs, allGroups);

        // Print the JSON object to console
        System.out.println(jsonObject);
        // Write the JSON object to a file
        try (FileWriter file = new FileWriter("jsons/result.json")) {
            file.write(jsonCreator.getGson().toJson(jsonObject));
/*
            System.out.println("Successfully Copied JSON Object to File...");
*/
/*
            System.out.println("\nJSON Object: " + jsonObject);
*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filePath = "jsons/result.json";
        modifyJsonFile(filePath);

        return gson.toJson(jsonObject);
    }

    /**
     * Creates a JSON representation of a pair.
     *
     * @param pair the pair object to convert to JSON.
     * @return the JSON object representing the pair.
     */
    public JsonObject createPairJson(Pair pair) {
        return gson.toJsonTree(pair).getAsJsonObject();
    }

    /**
     * Creates a JSON array representation of a list of pairs.
     *
     * @param pairs the list of pairs to convert to JSON array.
     * @return the JSON array representing the list of pairs.
     */
    public JsonArray createPairArray(List<Pair> pairs) {
        JsonArray jsonArray = new JsonArray();
        for (Pair pair : pairs) {
            jsonArray.add(createPairJson(pair));
        }
        return jsonArray;
    }

    /**
     * Returns the Gson instance used by the controller.json.JsonCreator.
     *
     * @return the Gson instance.
     */
    public Gson getGson() {
        return gson;
    }

    /**
     * Creates a JSON representation of a person.
     *
     * @param person the person object to convert to JSON.
     * @return the JSON object representing the person.
     */
    public JsonObject createPersonJson(Person person) {
        return gson.toJsonTree(person).getAsJsonObject();
    }

    /**
     * Creates a JSON array representation of a list of persons.
     *
     * @param persons the list of persons to convert to JSON array.
     * @return the JSON array representing the list of persons.
     */
    public JsonArray createPersonArray(List<Person> persons) {
        JsonArray jsonArray = new JsonArray();
        for (Person person : persons) {
            jsonArray.add(createPersonJson(person));
        }
        return jsonArray;
    }

    /**
     * Creates a JSON representation of a group.
     *
     * @param group the group object to convert to JSON.
     * @return the JSON object representing the group.
     */
    public JsonObject createGroupJson(Group group) {
        return gson.toJsonTree(group).getAsJsonObject();
    }

    /**
     * Creates a JSON array representation of a list of groups.
     *
     * @param groups the list of groups to convert to JSON array.
     * @return the JSON array representing the list of groups.
     */
    public JsonArray createGroupArray(List<Group> groups) {
        JsonArray jsonArray = new JsonArray();
        for (Group group : groups) {
            jsonArray.add(createGroupJson(group));
        }
        return jsonArray;
    }

    /**
     * Creates the overall JSON object by combining the JSON representations of pairs, persons, and groups.
     *
     * @param pairs            the list of pairs.
     * @param replacementListPerson          the list of persons.
     * @param replacementListPair  the list of replacement pairs.
     * @param groups           the list of groups.
     * @return the JSON object representing the overall data structure.
     */
    public JsonObject createJsonObject(List<Pair> pairs, List<Person> replacementListPerson, List<Pair> replacementListPair, List<Group> groups) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("groups", createGroupArray(groups));
        jsonObject.add("pairs", createPairArray(pairs));
        jsonObject.add("successorPairs", createPairArray(replacementListPair));
        jsonObject.add("successorParticipants", createPersonArray(replacementListPerson));

        modifyCreatedJSONObject(jsonObject);

        return jsonObject;
    }

    /**
     * Modifies the created JSON object by updating the values of specific properties.
     *
     * @param jsonObject The JSON object to be modified.
     */
    public void modifyCreatedJSONObject(JsonObject jsonObject) {

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            if(key.equals("groups")) {

                for(int i = 0; i < value.getAsJsonArray().size(); i++) {

                    JsonElement element = value.getAsJsonArray().get(i);
                    JsonObject kitchen = getValue(element, "kitchen");
                    if(kitchen != null) {
                        if((Objects.equals(kitchen.get("emergencyKitchen").getAsString(), "MAYBE"))) {
                            updateValue(kitchen, "emergencyKitchen", true);
                        }
                        else {
                            updateValue(kitchen, "emergencyKitchen", false);
                        }
                    }

                    updatePairKitchen(getValue(element, "cookingPair"));
                    updatePairKitchen(getValue(element, "secondPair"));
                    updatePairKitchen(getValue(element, "thirdPair"));
                }
            }

            if(key.equals("pairs") || key.equals("successorPairs")) {

                for(int i = 0; i < value.getAsJsonArray().size(); i++) {

                    updatePairKitchen(value.getAsJsonArray().get(i));
                }
            }

            if(key.equals("successorParticipants")) {
                for(int i = 0; i < value.getAsJsonArray().size(); i++) {

                    JsonElement element = value.getAsJsonArray().get(i);
                    JsonObject kitchen = getValue(element, "kitchen");

                    assert kitchen != null;
                    if ((Objects.equals(kitchen.get("emergencyKitchen").getAsString(), "NO"))) {
                        setValueToNull(element, "kitchen");
                    } else {
                        if ((Objects.equals(kitchen.get("emergencyKitchen").getAsString(), "MAYBE"))) {
                            updateValue(kitchen, "emergencyKitchen", true);
                        } else {
                            updateValue(kitchen, "emergencyKitchen", false);
                        }
                    }
                }
            }
        }
    }

    /**
     * Updates the kitchen values of a pair.
     *
     * @param element The JSON element representing the pair.
     */
    public void updatePairKitchen(JsonElement element) {

        JsonObject firstParticipant = getValue(element, "firstParticipant");
        assert firstParticipant != null;
        JsonObject firstParticipanKitchen = getValue(firstParticipant, "kitchen");


        JsonObject secondParticipant = getValue(element, "secondParticipant");
        assert secondParticipant != null;
        JsonObject secondParticipantKitchen = getValue(secondParticipant, "kitchen");

        assert firstParticipanKitchen != null;
        modifyKitchen(firstParticipant, firstParticipanKitchen);

        assert secondParticipantKitchen != null;
        modifyKitchen(secondParticipant, secondParticipantKitchen);
    }

    /**
     * Modifies the kitchen values based on the emergencyKitchen property.
     *
     * @param participant The JSON object representing the participant.
     * @param kitchen     The JSON object representing the kitchen.
     */
    private void modifyKitchen(JsonObject participant, JsonObject kitchen) {

        if((Objects.equals(kitchen.get("emergencyKitchen").getAsString(), "NO"))) {
            setValueToNull(participant, "kitchen");
        }
        else {
            if((Objects.equals(kitchen.get("emergencyKitchen").getAsString(), "MAYBE"))) {
                updateValue(kitchen, "emergencyKitchen", true);
            }
            else {
                updateValue(kitchen, "emergencyKitchen", false);
            }
        }
    }

    /**
     * Retrieves a specific property value from a JSON element.
     *
     * @param jsonElement   The JSON element containing the property.
     * @param propertyName  The name of the property.
     * @return              The JSON object representing the property value, or null if not found.
     */
    private static JsonObject getValue(JsonElement jsonElement, String propertyName) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement propertyElement = jsonObject.get(propertyName);
        if (propertyElement != null && !propertyElement.isJsonNull()) {
            return propertyElement.getAsJsonObject();
        }
        return null;
    }

    /**
     * Updates a specific property value in a JSON element to a boolean value.
     *
     * @param jsonElement    The JSON element containing the property.
     * @param propertyName   The name of the property to be updated.
     * @param newValue       The new boolean value for the property.
     */
    private static void updateValue(JsonElement jsonElement, String propertyName, boolean newValue) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject.addProperty(propertyName, newValue);
    }

    /**
     * Sets a specific property value in a JSON element to null.
     *
     * @param jsonElement   The JSON element containing the property.
     * @param propertyName  The name of the property to be set to null.
     */
    private static void setValueToNull(JsonElement jsonElement, String propertyName) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject.add(propertyName, JsonNull.INSTANCE);
    }
}
