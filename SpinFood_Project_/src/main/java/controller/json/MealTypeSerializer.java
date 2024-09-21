package controller.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import controller.MealType;

import java.lang.reflect.Type;

/**
 * MealTypeSerializer is a custom JSON serializer for the MealType enum.
 */
public class MealTypeSerializer implements JsonSerializer<MealType> {

    /**
     * Serializes the MealType enum to JSON.
     *
     * @param myEnum                   the MealType enum value to serialize
     * @param type                     the type of the object to serialize
     * @param jsonSerializationContext the JSON serialization context
     * @return the serialized JSON element
     */
    @Override
    public JsonElement serialize(MealType myEnum, Type type, JsonSerializationContext jsonSerializationContext) {

        String enumName = myEnum.name();
        String enumAsString = "";

        if(enumName.equals("STARTER")) {

            enumAsString = "first";
        }
        else if(enumName.equals("MAIN")) {

            enumAsString = "main";
        }
        else if(enumName.equals("DESSERT")) {

            enumAsString = "dessert";
        }

        return new JsonPrimitive(enumAsString.toLowerCase());

    }
}
