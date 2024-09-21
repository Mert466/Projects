import data.EHasKitchen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the EHasKitchen enum.
 */
public class EHasKitchenTest {

    /**
     * Tests the values of the EHasKitchen enum.
     */
    @Test
    public void testEHasKitchenValues() {
        EHasKitchen no = EHasKitchen.NO;
        EHasKitchen yes = EHasKitchen.YES;
        EHasKitchen maybe = EHasKitchen.MAYBE;

        assertNotNull(no, "NO should not be null");
        assertNotNull(yes, "YES should not be null");
        assertNotNull(maybe, "MAYBE should not be null");
    }

    /**
     * Tests the toString() method of the EHasKitchen enum.
     */
    @Test
    public void testEHasKitchenToString() {
        EHasKitchen no = EHasKitchen.NO;
        EHasKitchen yes = EHasKitchen.YES;
        EHasKitchen maybe = EHasKitchen.MAYBE;

        assertEquals("NO", no.toString(), "NO toString() should return 'NO'");
        assertEquals("YES", yes.toString(), "YES toString() should return 'YES'");
        assertEquals("MAYBE", maybe.toString(), "MAYBE toString() should return 'MAYBE'");
    }
}
