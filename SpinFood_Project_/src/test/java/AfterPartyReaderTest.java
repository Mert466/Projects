import data.AfterPartyReader;
import data.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The AfterPartyReaderTest class contains tests for the AfterPartyReader class.
 */
class AfterPartyReaderTest {

    private AfterPartyReader afterPartyReader;

    /**
     * Sets up the test fixture by creating a new AfterPartyReader object.
     */
    @BeforeEach
    public void setUp() {
        afterPartyReader = new AfterPartyReader();
    }

    /**
     * Tests the retrieval of the party location from a valid file path.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testGetPartyLocation_ValidPath() throws IOException {
        File tempFile = createTempFileWithContent("Longitude,Latitude\n8.6746166676233,50.5909317660173\n");

        Coordinate coordinate = afterPartyReader.getPartyLocation(tempFile.getPath());

        assertNotNull(coordinate, "Coordinate should not be null");
        assertEquals(8.6746166676233, coordinate.getLongtitude(), "Longitude should match the one in the file");
        assertEquals(50.5909317660173, coordinate.getLatitude(), "Latitude should match the one in the file");
    }

    /**
     * Tests the retrieval of the party location from an invalid file path.
     */
    @Test
    public void testGetPartyLocation_InvalidPath() {
        Coordinate coordinate = afterPartyReader.getPartyLocation("invalid_path");
        assertNull(coordinate, "Coordinate should be null for an invalid path");
    }

    /**
     * Tests the retrieval of the party location from a file with incorrect format.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testGetPartyLocation_WrongFormat() throws IOException {
        File tempFile = createTempFileWithContent("Longitude,Latitude\nnot,a,coordinate\n");

        Exception exception = assertThrows(NumberFormatException.class, () -> afterPartyReader.getPartyLocation(tempFile.getPath()));
        assertTrue(exception.getMessage().contains("For input string: \"not\""));
    }

    /**
     * Tests the retrieval of the party location from an empty file.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testGetPartyLocation_EmptyFile() throws IOException {
        File tempFile = createTempFileWithContent("");

        Coordinate coordinate = afterPartyReader.getPartyLocation(tempFile.getPath());

        assertNull(coordinate, "Coordinate should be null for an empty file");
    }

    /**
     * Creates a temporary file with the specified content.
     *
     * @param content the content of the file
     * @return the created temporary file
     * @throws IOException if an I/O error occurs
     */
    private File createTempFileWithContent(String content) throws IOException {
        File tempFile = File.createTempFile("prefix", null);
        tempFile.deleteOnExit();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(content);
        }
        return tempFile;
    }
}
