import controller.Group;
import controller.MealType;
import data.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Group class.
 */
public class GroupTest {

    /**
     * Test case for the constructor and getter methods of the Group class.
     */
    @Test
    public void testConstructorAndGetterMethods() {
        // Create person data arrays
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};
        String[] data3 = {"0", "3", "Alice", "meat", "28", "female", "yes", "1.5", "1.5", "1.5"};

        // Create Person objects
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);
        Person person3 = new Person(data3);

        // Create Pair objects
        Pair pair1 = new Pair(person1, person2);
        Pair pair2 = new Pair(person2, person3);
        Pair pair3 = new Pair(person3, person1);

        // Create a Group object
        Group group = new Group(pair1, pair2, pair3, MealType.DESSERT);

        // Verify the getter methods
        assertEquals(pair1, group.getPair1());
        assertEquals(pair2, group.getPair2());
        assertEquals(pair3, group.getPair3());
        assertEquals(MealType.DESSERT, group.getMealType());
        assertEquals("1/2/2/3/3/1", group.getGroupID());
        assertNotNull(group.getFoodPreference());
    }

    /**
     * Test case for the setPair1, setPair2, and setPair3 methods of the Group class.
     */
    @Test
    public void testSetPairMethods() {
        // Create person data arrays
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};
        String[] data3 = {"0", "3", "Alice", "meat", "28", "female", "yes", "1.5", "1.5", "1.5"};

        // Create Person objects
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);
        Person person3 = new Person(data3);

        // Create Pair objects
        Pair pair1 = new Pair(person1, person2);
        Pair pair2 = new Pair(person2, person3);
        Pair pair3 = new Pair(person3, person1);

        // Create a Group object
        Group group = new Group(pair1, pair2, pair3, MealType.DESSERT);

        // Create new Pair objects
        Pair newPair1 = new Pair(person2, person3);
        Pair newPair2 = new Pair(person3, person1);
        Pair newPair3 = new Pair(person1, person2);

        // Update the pairs in the group
        group.setPair1(newPair1);
        group.setPair2(newPair2);
        group.setPair3(newPair3);

        // Verify the updated pairs
        assertEquals(newPair1, group.getPair1());
        assertEquals(newPair2, group.getPair2());
        assertEquals(newPair3, group.getPair3());
    }

    /**
     * Test case for the setCookID and getCookID methods of the Group class.
     */
    @Test
    public void testSetAndGetCookID() {
        // Create person data arrays
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};
        String[] data3 = {"0", "3", "Alice", "meat", "28", "female", "yes", "1.5", "1.5", "1.5"};

        // Create Person objects
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);
        Person person3 = new Person(data3);

        // Create Pair objects
        Pair pair1 = new Pair(person1, person2);
        Pair pair2 = new Pair(person2, person3);
        Pair pair3 = new Pair(person3, person1);

        // Create a Group object
        Group group = new Group(pair1, pair2, pair3, MealType.DESSERT);

        // Set and get the cook ID
        group.setCookID("C001");
        assertEquals("C001", group.getCookID());
    }

    /**
     * Test case for the setGroupKitchen and getKitchen methods of the Group class.
     */
    @Test
    public void testSetAndGetGroupKitchen() {
        // Create person data arrays
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};
        String[] data3 = {"0", "3", "Alice", "meat", "28", "female", "yes", "1.5", "1.5", "1.5"};

        // Create Person objects
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);
        Person person3 = new Person(data3);

        // Create Pair objects
        Pair pair1 = new Pair(person1, person2);
        Pair pair2 = new Pair(person2, person3);
        Pair pair3 = new Pair(person3, person1);

        // Create a Group object
        Group group = new Group(pair1, pair2, pair3, MealType.DESSERT);

        // Create a Kitchen object
        Kitchen kitchen = new Kitchen(EHasKitchen.YES, 1.0, 2.0, 3.0);

        // Set and get the group's kitchen
        group.setGroupKitchen(kitchen);
        assertEquals(kitchen, group.getKitchen());
    }

    /**
     * Test case for the setFoodPreference and getFoodPreference methods of the Group class.
     */
    @Test
    public void testSetAndGetFoodPreference() {
        // Create person data arrays
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};
        String[] data3 = {"0", "3", "Alice", "meat", "28", "female", "yes", "1.5", "1.5", "1.5"};

        // Create Person objects
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);
        Person person3 = new Person(data3);

        // Create Pair objects
        Pair pair1 = new Pair(person1, person2);
        Pair pair2 = new Pair(person2, person3);
        Pair pair3 = new Pair(person3, person1);

        // Create a Group object
        Group group = new Group(pair1, pair2, pair3, MealType.DESSERT);

        // Set and get the food preference
        group.setFoodPreference(pair1, pair2, pair3);
        assertEquals(EFoodPreference.VEGAN, group.getFoodPreference());
    }

    /**
     * Test case for the createNewGroupID method of the Group class.
     */
    @Test
    public void testCreateNewGroupID() {
        // Create person data arrays
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};
        String[] data3 = {"0", "3", "Alice", "meat", "28", "female", "yes", "1.5", "1.5", "1.5"};

        // Create Person objects
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);
        Person person3 = new Person(data3);

        // Create Pair objects
        Pair pair1 = new Pair(person1, person2);
        Pair pair2 = new Pair(person2, person3);
        Pair pair3 = new Pair(person3, person1);

        // Create a Group object
        Group group = new Group(pair1, pair2, pair3, MealType.DESSERT);

        // Create new Pair objects
        Pair newPair1 = new Pair(person2, person3);
        Pair newPair2 = new Pair(person3, person1);
        Pair newPair3 = new Pair(person1, person2);

        // Create a new group ID
        group.createNewGroupID(newPair1, newPair2, newPair3);

        // Verify the updated group ID
        assertEquals("2/3/3/1/1/2", group.getGroupID());
    }

    /**
     * Test case for the getPairs method of the Group class when all pairs are non-null.
     */
    @Test
    public void testGetPairsWhenAllPairsAreNonNull() {
        // Create person data arrays
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};
        String[] data3 = {"0", "3", "Alice", "meat", "28", "female", "yes", "1.5", "1.5", "1.5"};

        // Create additional person data arrays
        String[] data4 = {"0", "4", "Mark", "none", "20", "male", "yes", "2.5", "2.5", "2.5"};
        String[] data5 = {"0", "5", "Emily", "vegan", "35", "female", "no", "3.0", "3.0", "3.0"};
        String[] data6 = {"0", "6", "Michael", "meat", "32", "male", "yes", "1.8", "1.8", "1.8"};


        // Create additional person data arrays
        String[] data7 = {"0", "7", "Sarah", "veggie", "27", "female", "yes", "2.2", "2.2", "2.2"};
        String[] data8 = {"0", "8", "Andrew", "vegan", "29", "male", "no", "2.7", "2.7", "2.7"};
        String[] data9 = {"0", "9", "Olivia", "meat", "33", "female", "yes", "1.9", "1.9", "1.9"};

        String[] data10 = {"0", "10", "David", "veggie", "31", "male", "yes", "1.6", "1.6", "1.6"};
        String[] data11 = {"0", "11", "Sophie", "vegan", "27", "female", "no", "2.3", "2.3", "2.3"};
        String[] data12 = {"0", "12", "Jacob", "meat", "26", "male", "yes", "1.7", "1.7", "1.7"};

        // Create Person objects
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);
        Person person3 = new Person(data3);
        Person person4 = new Person(data4);
        Person person5 = new Person(data5);
        Person person6 = new Person(data6);
        Person person7 = new Person(data7);
        Person person8 = new Person(data8);
        Person person9 = new Person(data9);
        Person person10 = new Person(data10);
        Person person11 = new Person(data11);
        Person person12 = new Person(data12);

        // Create three pairs with distinct Pair objects
        // Create three new pairs with distinct Pair objects
        Pair newPair1 = new Pair(person7, person8);
        Pair newPair2 = new Pair(person9, person10);
        Pair newPair3 = new Pair(person11, person12);

        // Create a Group object with these pairs and a meal type
        Group group = new Group(newPair1, newPair2, newPair3, MealType.MAIN);

        // Call the getPairs method
        var pairs = group.getPairs();

        // Assert that the returned list contains the three pairs in the same order
        assertArrayEquals(new Pair[]{newPair1, newPair2, newPair3}, pairs.toArray());
    }

    /**
     * Test case for the setPair1, setPair2, and setPair3 methods of the Group class
     * when called with new Pair objects.
     */
    @Test
    public void testSetPair1Pair2Pair3Methods() {

        // Create person data arrays
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};
        String[] data3 = {"0", "3", "Alice", "meat", "28", "female", "yes", "1.5", "1.5", "1.5"};

        // Create additional person data arrays
        String[] data4 = {"0", "4", "Mark", "none", "20", "male", "yes", "2.5", "2.5", "2.5"};
        String[] data5 = {"0", "5", "Emily", "vegan", "35", "female", "no", "3.0", "3.0", "3.0"};
        String[] data6 = {"0", "6", "Michael", "meat", "32", "male", "yes", "1.8", "1.8", "1.8"};


        // Create additional person data arrays
        String[] data7 = {"0", "7", "Sarah", "veggie", "27", "female", "yes", "2.2", "2.2", "2.2"};
        String[] data8 = {"0", "8", "Andrew", "vegan", "29", "male", "no", "2.7", "2.7", "2.7"};
        String[] data9 = {"0", "9", "Olivia", "meat", "33", "female", "yes", "1.9", "1.9", "1.9"};

        String[] data10 = {"0", "10", "David", "veggie", "31", "male", "yes", "1.6", "1.6", "1.6"};
        String[] data11 = {"0", "11", "Sophie", "vegan", "27", "female", "no", "2.3", "2.3", "2.3"};
        String[] data12 = {"0", "12", "Jacob", "meat", "26", "male", "yes", "1.7", "1.7", "1.7"};

        // Create Person objects
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);
        Person person3 = new Person(data3);
        Person person4 = new Person(data4);
        Person person5 = new Person(data5);
        Person person6 = new Person(data6);
        Person person7 = new Person(data7);
        Person person8 = new Person(data8);
        Person person9 = new Person(data9);
        Person person10 = new Person(data10);
        Person person11 = new Person(data11);
        Person person12 = new Person(data12);

        // Create a Group object with three initial pairs and a meal type
        Group group = new Group(
                new Pair(person1, person2),
                new Pair(person3, person4),
                new Pair(person5, person6),
                MealType.MAIN
        );

        // Create three new pairs with distinct Pair objects
        Pair newPair1 = new Pair(person7, person8);
        Pair newPair2 = new Pair(person9, person10);
        Pair newPair3 = new Pair(person11, person12);

        // Call the setPair1 method with a new pair
        group.setPair1(newPair1);

        // Call the setPair2 method with another new pair
        group.setPair2(newPair2);

        // Call the setPair3 method with the third new pair
        group.setPair3(newPair3);

        // Assert that the pair1, pair2, and pair3 fields in the Group object are updated with the new pairs
        assertSame(newPair1, group.getPair1(), "pair1 should be the same as newPair1");
        assertSame(newPair2, group.getPair2(), "pair2 should be the same as newPair2");
        assertSame(newPair3, group.getPair3(), "pair3 should be the same as newPair3");
    }

    /**
     * Test case for the getCookID and setCookID methods of the Group class.
     */
    @Test
    public void testGetCookIDAndSetCookIDMethods() {
        // Create person data arrays
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};
        String[] data3 = {"0", "3", "Alice", "meat", "28", "female", "yes", "1.5", "1.5", "1.5"};

        // Create additional person data arrays
        String[] data4 = {"0", "4", "Mark", "none", "20", "male", "yes", "2.5", "2.5", "2.5"};
        String[] data5 = {"0", "5", "Emily", "vegan", "35", "female", "no", "3.0", "3.0", "3.0"};
        String[] data6 = {"0", "6", "Michael", "meat", "32", "male", "yes", "1.8", "1.8", "1.8"};

        // Create Person objects
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);
        Person person3 = new Person(data3);
        Person person4 = new Person(data4);
        Person person5 = new Person(data5);
        Person person6 = new Person(data6);

        // Create a Group object with three pairs and a meal type
        Group group = new Group(
                new Pair(person1, person2),
                new Pair(person3, person4),
                new Pair(person5, person6),
                MealType.MAIN
        );

        // Set a cook ID using the setCookID method
        group.setCookID("C001");

        // Call the getCookID method
        var cookID = group.getCookID();

        // Assert that the returned cook ID matches the one set
        assertEquals("C001", cookID, "Cook ID should be 'C001'");
    }

    /**
     * Test case for the setGroupKitchen and getKitchen methods of the Group class.
     */
    @Test
    public void testSetGroupKitchenAndGetKitchenMethods() {
        // Create person data arrays
        String[] data1 = {"0", "1", "John", "vegan", "25", "male", "yes", "1.0", "1.0", "1.0"};
        String[] data2 = {"0", "2", "Jane", "veggie", "30", "female", "no", "2.0", "2.0", "2.0"};
        String[] data3 = {"0", "3", "Alice", "meat", "28", "female", "yes", "1.5", "1.5", "1.5"};

        // Create additional person data arrays
        String[] data4 = {"0", "4", "Mark", "none", "20", "male", "yes", "2.5", "2.5", "2.5"};
        String[] data5 = {"0", "5", "Emily", "vegan", "35", "female", "no", "3.0", "3.0", "3.0"};
        String[] data6 = {"0", "6", "Michael", "meat", "32", "male", "yes", "1.8", "1.8", "1.8"};

        // Create Person objects
        Person person1 = new Person(data1);
        Person person2 = new Person(data2);
        Person person3 = new Person(data3);
        Person person4 = new Person(data4);
        Person person5 = new Person(data5);
        Person person6 = new Person(data6);

        // Create a Group object with three pairs and a meal type
        Group group = new Group(
                new Pair(person1, person2),
                new Pair(person3, person4),
                new Pair(person5, person6),
                MealType.MAIN
        );

        // Create a Kitchen object
        Kitchen kitchen = new Kitchen(EHasKitchen.YES, 1.0, 2.0, 3.0);

        // Call the setGroupKitchen method with the Kitchen object
        group.setGroupKitchen(kitchen);

        // Call the getKitchen method
        var kitchenObj = group.getKitchen();

        // Assert that the returned Kitchen object is the same as the one set
        assertSame(kitchen, kitchenObj, "Kitchen object should be the same as the one set");
    }
}
