import controller.*;
import controller.json.JsonCreator;
import data.AfterPartyReader;
import data.CSVReader;
import data.Pair;
import data.Person;
import view.CreateEventData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateSolutions {

    public static void main(String[] args) {

        // Weglänge haben wir zeitlich nicht geschafft zu optimieren

        // For a)

        CreateEventData event1 = new CreateEventData(15, 10, 5, Integer.MAX_VALUE, "teilnehmerliste.csv");
        createSolution(event1, "LoesungA");
        // For b)

        CreateEventData event2 = new CreateEventData(15, 5, 10, Integer.MAX_VALUE, "teilnehmerliste.csv");
        createSolution(event2, "LoesungB");

        // For c)

        CreateEventData event3 = new CreateEventData(5, 10, 15, Integer.MAX_VALUE, "teilnehmerliste.csv");
        createSolution(event3, "LoesungC");

    }

    public static void createSolution(CreateEventData data, String fileName) {

        AfterPartyReader afterPartyCoordinates = new AfterPartyReader();
        afterPartyCoordinates.getPartyLocation("partylocation.csv");

        // Wir haben es zeitlich nicht mehr geschafft Gruppenbildung auch zu optimieren.

        PairCriteriaFactor pairCriteria = new PairCriteriaFactor();
        pairCriteria.setFoodPreferenceCriteria(data.getFoodPreferenceFactor());
        pairCriteria.setAgeDifferenceCriteria(data.getAgeDifferenceFactor());
        pairCriteria.setGenderDifferenceCriteria(data.getGenderDiversityFactor());
        GroupCriteriaFactor groupCriteriaFactor = new GroupCriteriaFactor();
        Criteria criteria = new Criteria(pairCriteria, groupCriteriaFactor);

        // Pair Assigner

        CSVReader reader = new CSVReader();
        PairAssigner pairAssigner = new PairAssigner(reader.getPersons(data.getSelectedFile()), criteria.getPairFactor());
        List<Pair> allPairs = reader.pairs;

        allPairs.addAll(pairAssigner.getCreatedPairs());

        GroupAssigner groupAssigner = new GroupAssigner();
        groupAssigner.createGroups(allPairs, data.getMaxParticipants(), true, pairAssigner.getReplacementList(), afterPartyCoordinates.getPartyLocation("partylocation.csv"));

        writeJSONFile(groupAssigner, fileName);
    }

    public static void writeJSONFile(GroupAssigner groupAssigner, String destFileName) {

        String jsonAsText = new JsonCreator().createJSON(groupAssigner.extractPairs(groupAssigner.starter), groupAssigner.getMoveUpPersons(), groupAssigner.getMoveUpPairs(), groupAssigner.getAllCreatedGroups());

        String folderPath = "Loesungen/";
        String filePath = folderPath + destFileName + ".json";

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonAsText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
