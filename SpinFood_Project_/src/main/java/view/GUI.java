package view;
import controller.*;
import controller.json.JsonCreator;
import data.*;
import view.buttons.CancelEventButton;
import view.buttons.JsonOutputButton;
import view.buttons.LanguageButtons;
import view.buttons.SaveJsonButton;
import view.tables.CreatedGroupsTable;
import view.tables.CreatedPairTable;
import view.tables.PairReplacementTable;
import view.tables.PersonReplacementTable;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  The GUI class represents the graphical user interface for the party management application.
 *  It provides a window that displays tables of pairs, groups, and replacement persons.
 */
public class GUI extends JFrame {

    List<Pair> allPairs;
    List<Person> replacementListPerson;
    List<Pair> replacementListPair;
    List<Group> allGroups;

    GroupAssigner groupAssigner;

    PersonReplacementTable personReplacementTable;
    PairReplacementTable pairReplacementTable;
    CreatedPairTable createdPairTable;
    CreatedGroupsTable createdGroupsTable;

    JPanel navigationBarPanel;
    JPanel bodyPanel;
    JPanel bodyTopPanel;
    JPanel bodyBottomPanel;

    boolean isSwitchPairPanelVisible = false;
    boolean isSwitchPersonPanelVisible = false;


    TableStates tableStates;

    CreateEventPanel createEventPanel;
    JPanel appPanel;
    ResourceBundle bundle;

    LanguageButtons languageButtons;

    /**
     * Constructs a GUI object that displays the party management application.
     */
    public GUI() {

        Locale.setDefault(Locale.GERMAN);
        bundle = ResourceBundle.getBundle("trans");

        AfterPartyReader afterPartyCoordinates = new AfterPartyReader();

        setLayout(new BorderLayout());
        languageButtons = new LanguageButtons(this);
        createEventPanel = new CreateEventPanel(this, languageButtons, afterPartyCoordinates.getPartyLocation("partylocation.csv"));
        add(createEventPanel, BorderLayout.CENTER);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Retrieves the list of replacement persons from the person replacement table.
     *
     * @return the list of replacement persons
     */

    public void createEvent(CreateEventData data) {

        Container parent = createEventPanel.getParent();
        parent.remove(createEventPanel);
        parent.revalidate();
        parent.repaint();

        // After Party coordinates
        AfterPartyReader afterPartyCoordinates = new AfterPartyReader();
        afterPartyCoordinates.getPartyLocation("partylocation.csv");

        // Criteria

        PairCriteriaFactor pairCriteria = new PairCriteriaFactor();
        pairCriteria.setFoodPreferenceCriteria(data.getFoodPreferenceFactor());
        pairCriteria.setAgeDifferenceCriteria(data.getAgeDifferenceFactor());
        pairCriteria.setGenderDifferenceCriteria(data.getGenderDiversityFactor());
        GroupCriteriaFactor groupCriteriaFactor = new GroupCriteriaFactor();
        Criteria criteria = new Criteria(pairCriteria, groupCriteriaFactor);

        // Pair Assigner

        CSVReader reader = new CSVReader();
        PairAssigner pairAssigner = new PairAssigner(reader.getPersons(data.getSelectedFile()), criteria.getPairFactor());
        replacementListPerson = new ArrayList<>(pairAssigner.getReplacementList());
        allPairs = reader.pairs;

        allPairs.addAll(pairAssigner.getCreatedPairs());

        List<Pair> allPairsClone = new ArrayList<>();
        allPairsClone.addAll(allPairs);

        groupAssigner = new GroupAssigner();
        groupAssigner.createGroups(allPairs, data.getMaxParticipants(), true, pairAssigner.getReplacementList(), afterPartyCoordinates.getPartyLocation("partylocation.csv"));

        allGroups = List.of(groupAssigner.starter, groupAssigner.main, groupAssigner.dessert).stream().flatMap(x -> x.stream()).collect(Collectors.toList());
        replacementListPair = groupAssigner.getMoveUpPairs();

        JButton undoButton = new JButton("Zurück");
        JButton redoButton = new JButton("Wiederholen");

        ActionListener undoListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tableStates.undo();
                fillGroupAssignerWithHistory();
                updateTables();
            }
        };

        undoButton.addActionListener(undoListener);

        ActionListener redoListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tableStates.redo();
                fillGroupAssignerWithHistory();
                updateTables();
            }
        };

        redoButton.addActionListener(redoListener);

        navigationBarPanel = new JPanel(new BorderLayout());
        JLabel spinfoodLogo = new JLabel("Spinfood");
        spinfoodLogo.setForeground(Color.WHITE);
        Font currentFont = spinfoodLogo.getFont();
        int newSize = currentFont.getSize() + 13;
        Font newFont = currentFont.deriveFont((float)newSize);
        spinfoodLogo.setFont(newFont);
        int paddingLeft = 12;
        Border emptyBorder = BorderFactory.createEmptyBorder(0, paddingLeft, 0, 0);
        spinfoodLogo.setBorder(emptyBorder);

        navigationBarPanel.setBackground(Color.ORANGE);
        navigationBarPanel.add(spinfoodLogo);
        navigationBarPanel.setPreferredSize(new Dimension(navigationBarPanel.getPreferredSize().width, 50));

        JPanel navigationBarButtons = new JPanel(new GridLayout(1, 3));
        navigationBarButtons.add(new CancelEventButton(this));
        navigationBarButtons.add(new JsonOutputButton(this));
        navigationBarButtons.add(undoButton);
        navigationBarButtons.add(redoButton);
        navigationBarPanel.add(navigationBarButtons, BorderLayout.EAST);

        bodyTopPanel = new JPanel(new GridLayout(1, 2));

        personReplacementTable = new PersonReplacementTable(replacementListPerson, this);
        createdPairTable = new CreatedPairTable(allPairsClone, this);

        bodyTopPanel.add(personReplacementTable);
        bodyTopPanel.add(createdPairTable);
        Coordinate afterParty = afterPartyCoordinates.getPartyLocation("partylocation.csv");
        bodyBottomPanel = new JPanel(new GridLayout(1, 2));
        pairReplacementTable = new PairReplacementTable(replacementListPair, this, afterParty);
        createdGroupsTable = new CreatedGroupsTable(allGroups, "Gruppen", afterParty, this);
        bodyBottomPanel.add(pairReplacementTable);
        bodyBottomPanel.add(createdGroupsTable);
        bodyPanel = new JPanel(new GridLayout(2, 2));
        int padding = 20;
        EmptyBorder border = new EmptyBorder(padding, padding, padding, padding);
        bodyPanel.setBorder(border);
        bodyPanel.add(bodyTopPanel);
        bodyPanel.add(bodyBottomPanel);

        appPanel = new JPanel(new BorderLayout());
        appPanel.add(navigationBarPanel, BorderLayout.NORTH);
        appPanel.add(bodyPanel, BorderLayout.CENTER);
        add(appPanel);
/*
        add(bodyPanel, BorderLayout.CENTER);
*/


/*        PairMetrics pairMetrics = new PairMetrics(groupAssigner.extractPairs(groupAssigner.starter), replacementListPerson);
        GroupMetrics groupMetrics = new GroupMetrics(List.of(groupAssigner.starter, groupAssigner.main, groupAssigner.dessert).stream().flatMap(x -> x.stream()).collect(Collectors.toList()), groupAssigner.getMoveUpPairs());
        pairMetrics.printMetrics();
        groupMetrics.printMetrics();*/

        tableStates = new TableStates();
        tableStates.saveState(groupAssigner.getMoveUpPersons(), groupAssigner.getMoveUpPairs(), groupAssigner.getAllCreatedGroups());


        // Pair cancels event

        // Start the timer
/*        int delayInSeconds = 10;
        final Timer timer = new Timer(delayInSeconds * 1000, null);

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Pair pair = groupAssigner.testPair;

                pair.getPerson1().setHasCanceledEvent(true);
                pair.getPerson2().setHasCanceledEvent(true);
                System.out.println("Paar: " + pair + " will sich abmelden");


                groupAssigner.peopleEventCancel(pair, getPersonReplacementList(), getPairReplacementList(), pairCriteria);
                updateTables();
                // Stop the timer after the event occurs
                timer.stop();
            }
        });

        // Start the timer
        timer.start();*/

    }



    public void updateTables() {

        createdGroupsTable.updateTable(groupAssigner.getAllCreatedGroups());
        createdPairTable.updateTable(List.of(List.of(groupAssigner.extractPairs(groupAssigner.starter), groupAssigner.extractPairs(groupAssigner.main), groupAssigner.extractPairs(groupAssigner.dessert)).stream().flatMap(x -> x.stream()).distinct().toList(), groupAssigner.getMoveUpPairs()).stream().flatMap(x -> x.stream()).distinct().toList());
        pairReplacementTable.updateTable(groupAssigner.getMoveUpPairs());
        personReplacementTable.updateTable(groupAssigner.getMoveUpPersons());
    }

    public void dissolvePair(Pair pair) {

        groupAssigner.removeGroupsLinkedToPair(groupAssigner.getPairAssignedGroups(pair));

        for(int i = groupAssigner.getMoveUpPairs().size() - 1; i >= 0; i--) {
            if(groupAssigner.getMoveUpPairs().get(i).getPairID().equals(pair.getPairID())) {
                groupAssigner.getMoveUpPairs().remove(i);
            }
        }

        groupAssigner.getMoveUpPersons().add(pair.getPerson1());
        groupAssigner.getMoveUpPersons().add(pair.getPerson2());

        updateTables();
        tableStates.saveState(groupAssigner.getMoveUpPersons(), groupAssigner.getMoveUpPairs(), groupAssigner.getAllCreatedGroups());

        for (int i = 0; i < groupAssigner.main.size(); i++) {
            for (int j = 0; j < groupAssigner.main.get(i).getPairs().size(); j++) {

                if (groupAssigner.main.get(i).getPairs().get(j).isSignedInTogether()) {
                    groupAssigner.testPair = groupAssigner.main.get(i).getPairs().get(j);
                    groupAssigner.testGroupID = groupAssigner.main.get(i).getGroupID();
                }
            }
        }
    }

    public void dissolveGroup(Pair pair) {

        groupAssigner.removeGroupsLinkedToPair(groupAssigner.getPairAssignedGroups(pair));
        updateTables();
        tableStates.saveState(groupAssigner.getMoveUpPersons(), groupAssigner.getMoveUpPairs(), groupAssigner.getAllCreatedGroups());
    }

    public void onNewPearCreated(Pair newPair) {

        List<Person> personsRemove = new ArrayList<>(List.of(newPair.getPerson1(), newPair.getPerson2()));

        for(int i = groupAssigner.getMoveUpPersons().size() - 1; i >= 0; i--) {

            for(int j = personsRemove.size() - 1; j >= 0; j--) {

                if(groupAssigner.getMoveUpPersons().get(i).getUUID().equals(personsRemove.get(j).getUUID())) {
                    groupAssigner.getMoveUpPersons().remove(i);
                    break;
                }
            }
        }

        newPair.setSignedInTogether(false);

        if(newPair.getPerson1().getKitchen().getHasKitchen() == EHasKitchen.YES) {
            newPair.setKitchen(newPair.getPerson1().getKitchen());
        }
        else if(newPair.getPerson2().getKitchen().getHasKitchen() == EHasKitchen.YES) {
            newPair.setKitchen(newPair.getPerson2().getKitchen());
        }

        groupAssigner.getMoveUpPairs().add(newPair);
        updateTables();

        tableStates.saveState(groupAssigner.getMoveUpPersons(), groupAssigner.getMoveUpPairs(), groupAssigner.getAllCreatedGroups());
    }

    public void showJsonDialog() {

        String defaultText = new JsonCreator().createJSON(groupAssigner.extractPairs(groupAssigner.starter), getPersonReplacementList(), groupAssigner.getMoveUpPairs(), groupAssigner.getAllCreatedGroups());

        JTextArea textArea = new JTextArea(defaultText);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window window = SwingUtilities.windowForComponent(cancelButton);
                window.dispose();
            }
        });

        buttonPanel.add(cancelButton);

        SaveJsonButton customButton = new SaveJsonButton(defaultText);
        buttonPanel.add(customButton);

        List<Object> options = Arrays.asList(new Object[] { buttonPanel });

        JOptionPane.showOptionDialog(null, scrollPane, "Json Output",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options.toArray(), null);
    }

    public void cancelEvent() {
        tableStates.reset();
        Container parent = this.appPanel.getParent();
        parent.remove(appPanel);
        revalidate();
        repaint();
        add(createEventPanel);
    }

    public void changeLanguage(Locale lang) {
        ResourceBundle.clearCache();
        Locale.setDefault(lang);
        bundle = ResourceBundle.getBundle("trans");
        setTranslationTexts();
    }

    public void setTranslationTexts() {
        createEventPanel.updateTexts();
        if(createdPairTable != null && personReplacementTable != null) {
            createdPairTable.updateTexts();
            personReplacementTable.updateTexts();
        }
    }

    public String getTranslatedString(String key) {
        return bundle.getString(key);
    }

    public void fillGroupAssignerWithHistory() {

        List<Person> currentMoveUpPersonTable = new ArrayList<>(tableStates.getCurrentMoveUpPersonTable());
        List<Pair> currentMoveUpPairTable = new ArrayList<>(tableStates.getCurrentMoveUpPairTable());
        List<Group> currentGroups = new ArrayList<>(tableStates.getCurrentGroups());

        groupAssigner.starter = new ArrayList<>();
        groupAssigner.main = new ArrayList<>();
        groupAssigner.dessert = new ArrayList<>();


        groupAssigner.setMoveUpPersons(currentMoveUpPersonTable);
        groupAssigner.setMoveUpPairs(currentMoveUpPairTable);

        for(int i = 0; i < currentGroups.size(); i++) {
            if(currentGroups.get(i).getMealType() == MealType.STARTER) {
                groupAssigner.starter.add(currentGroups.get(i));
            }
        }

        for(int i = 0; i < currentGroups.size(); i++) {
            if(currentGroups.get(i).getMealType() == MealType.MAIN) {
                groupAssigner.main.add(currentGroups.get(i));
            }
        }

        for(int i = 0; i < currentGroups.size(); i++) {
            if(currentGroups.get(i).getMealType() == MealType.DESSERT) {
                groupAssigner.dessert.add(currentGroups.get(i));
            }
        }
    }

    public void onNewGroupCreate(Group group) {

        if(group.getMealType() == MealType.STARTER) {
            groupAssigner.starter.add(group);
        }
        else if(group.getMealType() == MealType.MAIN) {
            groupAssigner.main.add(group);
        }
        else if(group.getMealType() == MealType.DESSERT) {
            groupAssigner.dessert.add(group);
        }

        List<Pair> pairsRemove = new ArrayList<>();

        for(int i = groupAssigner.getMoveUpPairs().size() - 1; i >= 0; i--) {

            for(int j = group.getPairs().size() - 1; j >= 0; j--) {

                if(groupAssigner.getMoveUpPairs().get(i).getPairID().equals(group.getPairs().get(j).getPairID())) {
                    pairsRemove.add(groupAssigner.getMoveUpPairs().get(i));
                    break;
                }
            }
        }
        groupAssigner.getMoveUpPairs().removeAll(pairsRemove);
        pairReplacementTable.hideCreateGroupsPanel();
        updateTables();
        tableStates.saveState(groupAssigner.getMoveUpPersons(), groupAssigner.getMoveUpPairs(), groupAssigner.getAllCreatedGroups());

    }

    public List<Person> getPersonReplacementList() {

        return groupAssigner.getMoveUpPersons();
    }

    public List<Pair> getPairReplacementList() {

        return groupAssigner.getMoveUpPairs();
    }

    public List<Pair> getPairs() {

        return List.of(List.of(groupAssigner.extractPairs(groupAssigner.starter), groupAssigner.extractPairs(groupAssigner.main), groupAssigner.extractPairs(groupAssigner.dessert)).stream().flatMap(x -> x.stream()).distinct().toList(), groupAssigner.getMoveUpPairs()).stream().flatMap(x -> x.stream()).distinct().toList();
    }

    public void loadSwitchPairPanel(Pair selectedPair, List<Pair> candidates) {

        if(!isSwitchPairPanelVisible) {
            bodyBottomPanel.add(new SwitchPairPanel(selectedPair, candidates,this));
            revalidate();
            repaint();
            isSwitchPairPanelVisible = true;
        }
    }

    public void removeSwitchPairPanel() {
        bodyBottomPanel.remove(2);
        revalidate();
        repaint();
        isSwitchPairPanelVisible = false;
        enableGroupTableElements();
    }

    public void addEntriesToPairReplacementTable(List<Pair> pairs) {
        this.pairReplacementTable.addEntriesToTable(pairs);
    }

    public void removeEntriesFromPairReplacementTable(List<Pair> pairs) {
        this.pairReplacementTable.removeEntriesFromTable(pairs);
    }

    public void updatePairInGroupTable(Pair oldPair, Pair newPair) {

        this.createdGroupsTable.updateTablePair(oldPair, newPair);
    }

    public void updatePairReplacementInGroupTable() {

        this.createdGroupsTable.getReplacementPairs();
    }

    public void switchPairInCreatedPairTable(Pair switchPair, Pair selectedPair) {
        this.createdPairTable.switchPair(switchPair, selectedPair);
    }

    public void loadSwitchPersonPanel(Pair pair, List<Person> candidatesPersonOne, List<Person> candidatesPersonTwo) {
        if(!isSwitchPersonPanelVisible) {
            bodyBottomPanel.add(new SwitchPersonPanel(this, pair, candidatesPersonOne, candidatesPersonTwo));
            revalidate();
            repaint();
            isSwitchPersonPanelVisible = true;
        }
    }

    public void removeSwitchPersonPanel() {
        bodyBottomPanel.remove(2);
        revalidate();
        repaint();
        isSwitchPersonPanelVisible = false;
        enableGroupTableElements();
    }

    public void enableGroupTableElements() {
        createdGroupsTable.enableElements();
    }

    public void updatePersonSwitch(Pair oldPair, Pair newPair, Person newPerson, Person oldPerson) {

        updatePairInGroupTable(oldPair, newPair);
        switchPairInCreatedPairTable(oldPair, newPair);
/*
        personReplacementTable.updateTable(newPerson, oldPerson);
*/
        removeSwitchPersonPanel();
    }
}
