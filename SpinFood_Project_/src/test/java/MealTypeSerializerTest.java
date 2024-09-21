import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import controller.MealType;
import controller.json.MealTypeSerializer;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * MealTypeSerializerTest is a test class for MealTypeSerializer.
 */
public class MealTypeSerializerTest {

    /**
     * Tests the serialization of the STARTER enum value.
     */
    @Test
    public void testSerializeStarter() {
        MealTypeSerializer serializer = new MealTypeSerializer();
        MealType starter = MealType.STARTER;
        JsonElement serialized = serializer.serialize(starter, null, null);
        String expected = "first";
        assertEquals(expected, serialized.getAsString().toLowerCase(), "Serialization of STARTER failed");
    }

    /**
     * Tests the serialization of the MAIN enum value.
     */
    @Test
    public void testSerializeMain() {
        MealTypeSerializer serializer = new MealTypeSerializer();
        MealType main = MealType.MAIN;
        JsonElement serialized = serializer.serialize(main, null, null);
        String expected = "main";
        assertEquals(expected, serialized.getAsString().toLowerCase(), "Serialization of MAIN failed");
    }

    /**
     * Tests the serialization of the DESSERT enum value.
     */
    @Test
    public void testSerializeDessert() {
        MealTypeSerializer serializer = new MealTypeSerializer();
        MealType dessert = MealType.DESSERT;
        JsonElement serialized = serializer.serialize(dessert, null, null);
        String expected = "dessert";
        assertEquals(expected, serialized.getAsString().toLowerCase(), "Serialization of DESSERT failed");
    }

}
