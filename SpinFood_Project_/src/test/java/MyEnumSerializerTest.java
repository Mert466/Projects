import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import controller.MealType;
import controller.json.MyEnumSerializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * MyEnumSerializerTest is a test class for MyEnumSerializer.
 */
public class MyEnumSerializerTest {

    /**
     * Tests the serialization of the STARTER enum value.
     */
    @Test
    public void testSerializeStarter() {
        MyEnumSerializer serializer = new MyEnumSerializer();
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
        MyEnumSerializer serializer = new MyEnumSerializer();
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
        MyEnumSerializer serializer = new MyEnumSerializer();
        MealType dessert = MealType.DESSERT;
        JsonElement serialized = serializer.serialize(dessert, null, null);
        String expected = "dessert";
        assertEquals(expected, serialized.getAsString().toLowerCase(), "Serialization of DESSERT failed");
    }

    /**
     * Tests the full serialization of MealTypes object using Gson and MyEnumSerializer.
     */
    @Test
    public void testFullSerialization() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(MealType.class, new MyEnumSerializer())
                .create();

        MealType starter = MealType.STARTER;
        MealType main = MealType.MAIN;
        MealType dessert = MealType.DESSERT;

        String expectedJson = "{\"starter\":\"first\",\"main\":\"main\",\"dessert\":\"dessert\"}";
        String actualJson = gson.toJson(new MealTypes(starter, main, dessert));

        assertEquals(expectedJson, actualJson, "Full serialization failed");
    }

    /**
     * Helper class for full serialization test.
     */
    private static class MealTypes {
        private final MealType starter;
        private final MealType main;
        private final MealType dessert;

        public MealTypes(MealType starter, MealType main, MealType dessert) {
            this.starter = starter;
            this.main = main;
            this.dessert = dessert;
        }
    }
}
