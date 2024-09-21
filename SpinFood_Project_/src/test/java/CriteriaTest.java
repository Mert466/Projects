import controller.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Criteria class.
 */
class CriteriaTest {

    private PairCriteriaFactor pairCriteriaFactor;
    private GroupCriteriaFactor groupCriteriaFactor;

    @BeforeEach
    void setUp() {
        pairCriteriaFactor = new PairCriteriaFactor();  // Assuming default constructor
        groupCriteriaFactor = new GroupCriteriaFactor(); // Assuming default constructor
    }

    /**
     * Tests the construction of a Criteria object with only a PairCriteriaFactor.
     */
    @Test
    void testPairFactorOnlyConstruction() {
        Criteria criteria = new Criteria(pairCriteriaFactor);
        assertEquals(pairCriteriaFactor, criteria.getPairFactor());
        assertNull(criteria.getGroupFactor());
    }

    /**
     * Tests the construction of a Criteria object with a PairCriteriaFactor and GroupCriteriaFactor.
     */
    @Test
    void testPairAndGroupFactorConstruction() {
        Criteria criteria = new Criteria(pairCriteriaFactor, groupCriteriaFactor);
        assertEquals(pairCriteriaFactor, criteria.getPairFactor());
        assertEquals(groupCriteriaFactor, criteria.getGroupFactor());
    }

    /**
     * Tests the construction of a Criteria object with null GroupCriteriaFactor.
     */
    @Test
    void testNullGroupFactorConstruction() {
        Criteria criteria = new Criteria(pairCriteriaFactor, null);
        assertEquals(pairCriteriaFactor, criteria.getPairFactor());
        assertNull(criteria.getGroupFactor());
    }

    /**
     * Test to ensure that constructing a Criteria object with a null pair factor doesn't throw an exception.
     */
    @Test
    void testNullPairFactorConstruction() {
        assertDoesNotThrow(() -> new Criteria(null));
    }

    /**
     * Test to ensure that constructing a Criteria object with both factors as null doesn't throw an exception.
     */
    @Test
    void testNullBothFactorsConstruction() {
        assertDoesNotThrow(() -> new Criteria(null, null));
    }

    /**
     * Test to ensure that getPairFactor() returns null when the pair factor was constructed as null.
     */
    @Test
    void testGetPairFactorWhenNull() {
        Criteria criteria = new Criteria(null);
        assertNull(criteria.getPairFactor());
    }

    /**
     * Test to ensure that getGroupFactor() returns null when the group factor was constructed as null.
     */
    @Test
    void testGetGroupFactorWhenNull() {
        Criteria criteria = new Criteria(pairCriteriaFactor, null);
        assertNull(criteria.getGroupFactor());
    }

    /**
     * Test for the pair factor constructor of the Criteria class.
     */
    @Test
    void testPairFactorConstructor() {
        Criteria criteria = new Criteria(pairCriteriaFactor);
        assertEquals(pairCriteriaFactor, criteria.getPairFactor());
        assertNull(criteria.getGroupFactor());
    }

    /**
     * Test for the constructor with both pair and group factors of the Criteria class.
     */
    @Test
    void testBothFactorsConstructor() {
        Criteria criteria = new Criteria(pairCriteriaFactor, groupCriteriaFactor);
        assertEquals(pairCriteriaFactor, criteria.getPairFactor());
        assertEquals(groupCriteriaFactor, criteria.getGroupFactor());
    }

    /**
     * Test for constructing Criteria with a null pair factor, expecting no exception to be thrown.
     */
    @Test
    void testNullPairFactorConstructionException() {
        assertDoesNotThrow(() -> new Criteria(null, groupCriteriaFactor));
    }

    /**
     * Test for constructing Criteria with a null group factor, expecting no exception to be thrown.
     */
    @Test
    void testNullGroupFactorConstructionException() {
        assertDoesNotThrow(() -> new Criteria(pairCriteriaFactor, null));
    }

    /**
     * Test for getting a null pair factor after constructing Criteria with a null pair factor.
     */
    @Test
    void testNullPairFactorReturn() {
        Criteria criteria = new Criteria(null, groupCriteriaFactor);
        assertNull(criteria.getPairFactor());
    }

    /**
     * Test for getting a null group factor after constructing Criteria with a null group factor.
     */
    @Test
    void testNullGroupFactorReturn() {
        Criteria criteria = new Criteria(pairCriteriaFactor, null);
        assertNull(criteria.getGroupFactor());
    }

}