import data.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

/**
 * The CSVReaderTest class contains tests for the CSVReader class.
 */
class CSVReaderTest {

    @TempDir
    File tempDir;
    File tempFile;
    CSVReader reader;
    List<Person> persons;

    @BeforeEach
    void setUp() throws IOException {
        reader = new CSVReader();
        persons = reader.getPersons("teilnehmerliste.csv");

        assertNotNull(persons);
    }

    /**
     * Tests the data of the first person read from the CSV file.
     */
    @Test
    void testPerson1Data() {
        Person person1 = persons.get(0);

        assertNotNull(person1.getUUID());
        assertEquals("004670cb-47f5-40a4-87d8-5276c18616ec", person1.getUUID());

        assertNotNull(person1.getName());
        assertEquals("Person1", person1.getName());

        assertNotNull(person1.getFoodPreference());
        assertEquals(EFoodPreference.VEGGIE, person1.getFoodPreference());

        assertNotNull(person1.getAge());
        assertEquals(21, person1.getAge());

        assertNotNull(person1.getSex());
        assertEquals(EGender.MALE, person1.getSex());

        assertNotNull(person1.getKitchen().getHasKitchen());
        assertEquals(EHasKitchen.MAYBE, person1.getKitchen().getHasKitchen());

        assertNotNull(person1.getKitchen().getKitchenStory());
        assertEquals(3.0, person1.getKitchen().getKitchenStory());

        assertNotNull(person1.getKitchen().getKitchenLongitude());
        assertEquals(8.673368271555807, person1.getKitchen().getKitchenLongitude());

        assertNotNull(person1.getKitchen().getkitchenLatitude());
        assertEquals(50.5941282715558, person1.getKitchen().getkitchenLatitude());
    }


    /**
     * Tests the data of the second person read from the CSV file.
     */
    @Test
    void testPerson2Data() {
        Person person2 = persons.get(1);

        assertNotNull(person2.getUUID());
        assertEquals("01a099db-22e1-4fc3-bbf5-db738bc2c10b", person2.getUUID());

        assertNotNull(person2.getName());
        assertEquals("Person2", person2.getName());

        assertNotNull(person2.getFoodPreference());
        assertEquals(EFoodPreference.NONE, person2.getFoodPreference());

        assertNotNull(person2.getAge());
        assertEquals(26, person2.getAge());

        assertNotNull(person2.getSex());
        assertEquals(EGender.MALE, person2.getSex());

        assertNotNull(person2.getKitchen().getHasKitchen());
        assertEquals(EHasKitchen.YES, person2.getKitchen().getHasKitchen());

        assertNotNull(person2.getKitchen().getKitchenStory());
        assertEquals(1.0, person2.getKitchen().getKitchenStory());

        assertNotNull(person2.getKitchen().getKitchenLongitude());
        assertEquals(8.718914539788807, person2.getKitchen().getKitchenLongitude());

        assertNotNull(person2.getKitchen().getkitchenLatitude());
        assertEquals(50.590899839788804, person2.getKitchen().getkitchenLatitude());
    }

    /**
     * Tests reading from an empty CSV file.
     *
     * @throws IOException if an I/O error occurs while creating the empty file
     */
    @Test
    void testEmptyFile() throws IOException {
        // Create an empty file in the temporary directory
        File emptyFile = new File(tempDir, "empty.csv");
        boolean wasFileCreated = emptyFile.createNewFile();

        assertTrue(wasFileCreated, "Failed to create temporary file");

        List<Person> personsFromEmptyFile = reader.getPersons(emptyFile.getPath());

        assertTrue(personsFromEmptyFile.isEmpty());
    }
}
