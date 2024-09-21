import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import controller.json.LowerCaseSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * LowerCaseSerializerTest is a test class for LowerCaseSerializer.
 */
public class LowerCaseSerializerTest {

    private enum TestEnum {
        ENUM_VALUE
    }

    /**
     * Tests the serialization of an enum value to a lowercase string.
     */
    @Test
    public void testSerializeEnumValue() {
        LowerCaseSerializer<TestEnum> serializer = new LowerCaseSerializer<>();
        TestEnum enumValue = TestEnum.ENUM_VALUE;
        JsonElement serialized = serializer.serialize(enumValue, null, null);
        String expected = "enum_value";
        assertEquals(expected, serialized.getAsString(), "Serialization of enum value failed");
    }

    /**
     * Tests the serialization of a null enum value.
     */
    @Test
    public void testSerializeNullValue() {
        LowerCaseSerializer<TestEnum> serializer = new LowerCaseSerializer<>();
        TestEnum enumValue = null;
        Assertions.assertThrows(NullPointerException.class, () -> {
            serializer.serialize(enumValue, null, null);
        });
    }

    /**
     * Tests the serialization of an enum value with lowercase letters to a lowercase string.
     */
    @Test
    public void testSerializeEnumValueWithLowercase() {
        LowerCaseSerializer<TestEnum> serializer = new LowerCaseSerializer<>();
        TestEnum enumValue = TestEnum.ENUM_VALUE;
        JsonElement serialized = serializer.serialize(enumValue, null, null);
        String expected = "enum_value";
        assertEquals(expected, serialized.getAsString(), "Serialization of enum value with lowercase letters failed");
    }

    /**
     * Tests the serialization of an enum value with uppercase letters to a lowercase string.
     */
    @Test
    public void testSerializeEnumValueWithUppercase() {
        LowerCaseSerializer<TestEnum> serializer = new LowerCaseSerializer<>();
        TestEnum enumValue = TestEnum.ENUM_VALUE;
        JsonElement serialized = serializer.serialize(enumValue, null, null);
        String expected = "enum_value";
        assertEquals(expected, serialized.getAsString(), "Serialization of enum value with uppercase letters failed");
    }
}
