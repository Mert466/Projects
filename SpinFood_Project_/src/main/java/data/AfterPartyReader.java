package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The AfterPartyReader class reads party location data from a CSV file.
 */
public class AfterPartyReader {

    /**
     * Retrieves the party location coordinate from the specified CSV file.
     *
     * @param path the path to the CSV file
     * @return the party location coordinate
     */
    public Coordinate getPartyLocation(String path) {

        String line;
        String csvSplitBy = ",";
        Coordinate coordinate = null;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {

                String[] locationData = line.split(csvSplitBy);
                coordinate = new Coordinate(Double.parseDouble(locationData[0]), Double.parseDouble(locationData[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return coordinate;
    }

}
