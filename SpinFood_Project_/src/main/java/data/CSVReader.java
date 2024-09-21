package data;

import data.Coordinate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The data.CSVReader class provides functionality to read data from a CSV file
 * and create a list of data.Person objects.
 */
public class CSVReader {

    public List<Pair> pairs = new ArrayList<>();

    /**
     * Reads a CSV file and returns a list of data.Person objects.
     *
     * @param path The path to the CSV file
     * @return A list of Person objects read from the CSV file
     */
    public List<Person> getPersons(String path) {

        String line;
        String csvSplitBy = ",";
        List<Person> people = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {

                String[] personData = line.split(csvSplitBy);

                if(personData.length > 10 && !personData[10].isEmpty()) {

                    String[] person1Data = Arrays.copyOfRange(personData, 0, 10);
                    String[] person2MainData = Arrays.copyOfRange(personData, 10, 14);

                    String[] person2Data = new String[7];

                    person2Data[0] = personData[0];
                    person2Data[1] = person2MainData[0];
                    person2Data[2] = person2MainData[1];
                    person2Data[3] = person1Data[3];
                    person2Data[4] = person2MainData[2];
                    person2Data[5] = person2MainData[3];
                    person2Data[6] = "no";

                    Person person1 = new Person(person1Data);
                    Person person2 = new Person(person2Data);

                    Pair newPair = new Pair(person1, person2, person1.getFoodPreference());
                    newPair.setSignedInTogether(true);
                    newPair.setKitchen(person1.getKitchen());

                    this.pairs.add(newPair);
                }
                else {
                    people.add(new Person(personData));
                }
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        return people;
    }
}