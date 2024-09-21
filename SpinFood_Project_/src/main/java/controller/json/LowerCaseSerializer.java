package controller.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * LowerCaseSerializer is a generic JSON serializer for enums that converts the enum values to lowercase strings.
 *
 * @param <T> the enum type
 */
public class LowerCaseSerializer<T extends Enum<T>> implements JsonSerializer<T> {

    /**
     * Serializes the enum value to JSON as a lowercase string.
     *
     * @param enumValue                 the enum value to serialize
     * @param type                      the type of the object to serialize
     * @param jsonSerializationContext the JSON serialization context
     * @return the serialized JSON element
     */
        @Override
        public JsonElement serialize(T enumValue, Type type, JsonSerializationContext jsonSerializationContext) {
        String enumAsString = enumValue.name().toLowerCase();
        return new JsonPrimitive(enumAsString);
    }

}
