package view.tables;

import controller.*;
import data.Coordinate;
import data.EFoodPreference;
import data.Pair;
import data.Person;
import view.GUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The CreatedGroupsTable class represents a JPanel that displays a table of created groups.
 * It provides information about the pairs in each group and related metrics.
 */
public class CreatedGroupsTable extends JPanel {

    List<Group> groups;
    JTable groupTable;
    JPanel createGroupContainer;
    JPanel createGroupListPanel;
    GUI gui;
    Pair currentSelectedPair;
    List<Pair> potentialPairs = new ArrayList<>();
    JButton switchPairBtn;
    JButton switchPersonBtn;
    JButton dissolveGroupButton;
    Coordinate afterParty;
    List<Pair> replacementListPair;
    JLabel metrics;


    private int lastSelectedRow;
    private int lastSelectedColumn;

    /**
     * Constructs a CreatedGroupsTable object with the given list of groups, heading, and replacement list pairs.
     *
     * @param groups              the list of groups to display in the table.
     * @param heading             the heading text for the table.
     */
    public CreatedGroupsTable(List<Group> groups, String heading, Coordinate afterParty, GUI gui) {
        Collections.sort(groups);
        this.gui = gui;
        this.groups = groups;
        this.afterParty = afterParty;
        getReplacementPairs();

        DefaultTableModel model = new DefaultTableModel(getTableData(), getTableColumnNames()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Setze alle Zellen als nicht editierbar
            }
        };

        setLayout(new BorderLayout());

        JLabel groupTableHeading = new JLabel(heading);
        GroupMetrics groupMetrics = new GroupMetrics(groups, replacementListPair);
        String metricsText = "Gruppen: " + groupMetrics.getCreatedGroupsCount() + ", Nachrückende Pärchen: " + groupMetrics.getReplacementGroupCount() +
                ", Geschlechterdiversität: " + groupMetrics.getAvgGenderDiversity() +  ", Altersdifferenz: " + groupMetrics.getAvgAgeDifference() + ", Vorliebenabweichung: " +
                groupMetrics.getAvgFoodPreferenceDeviation() + ", Weglänge: " + groupMetrics.getPathLength();
        metrics = new JLabel(metricsText);
        metrics.setToolTipText(metricsText);
        JScrollPane groupTableScroll = new JScrollPane();
        groupTable = new JTable(model);
        createGroupContainer = new JPanel(new BorderLayout());
        createGroupListPanel = new JPanel(new GridLayout(1, 3));



        groupTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupTable.getTableHeader().setReorderingAllowed(false);

        groupTableScroll.setViewportView(groupTable);
        hideIDColumns();

        dissolveGroupButton = new JButton("Auflösen");
        dissolveGroupButton.setEnabled(false);
        ActionListener dissolveGroupListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(currentSelectedPair);
                gui.dissolveGroup(currentSelectedPair);
            }
        };
        dissolveGroupButton.addActionListener(dissolveGroupListener);


        switchPairBtn = new JButton("Pärchen tauschen");
        switchPairBtn.setEnabled(false);
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentSelectedPair != null) {
                    disableTableElements();
                    gui.loadSwitchPairPanel(currentSelectedPair, potentialPairs);
                }
            }
        };
        switchPairBtn.addActionListener(listener);

        switchPersonBtn = new JButton("Person tauschen");
        switchPersonBtn.setEnabled(false);
        ActionListener switchPersonBtnListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentSelectedPair != null) {

                    disableTableElements();
                    List<Person> persons = gui.getPersonReplacementList();


                    Person person1 = currentSelectedPair.getPerson1();
                    Person person2 = currentSelectedPair.getPerson2();

                    List<Person> candidatesPersonOne = getValidPersonCandidates(persons, person1);
                    List<Person> candidatesPersonTwo = getValidPersonCandidates(persons, person2);

                    gui.loadSwitchPersonPanel(currentSelectedPair, candidatesPersonOne, candidatesPersonTwo);
                }
            }
        };

        switchPersonBtn.addActionListener(switchPersonBtnListener);

        groupTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                if (!groupTable.isEnabled()) {
                    return;
                }

                DefaultTableModel tableModel = (DefaultTableModel) groupTable.getModel();
                int selectedRow = groupTable.getSelectedRow();
                lastSelectedRow = groupTable.getSelectedRow();

                if (lastSelectedRow != -1) {

                    lastSelectedRow = groupTable.getSelectedRow();
                    lastSelectedColumn = groupTable.getSelectedColumn();
                    dissolveGroupButton.setEnabled(true);

                    String pairID = (String) tableModel.getValueAt(lastSelectedRow, 0);
                    List<Pair> pairs = getPairs();

                    for(int i = 0; i < pairs.size(); i++) {

                        if(pairs.get(i).getPairID().equals(pairID)) {
                            currentSelectedPair = pairs.get(i);
                        }
                    }
                }
            }
        });

        JPanel groupTableHeader = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(new GridLayout());
        headerPanel.add(groupTableHeading);
        groupTableHeader.add(headerPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(dissolveGroupButton);

        groupTableHeader.add(buttonPanel, BorderLayout.EAST);
        groupTableHeader.add(groupTableHeading, BorderLayout.WEST);
        add(groupTableHeader, BorderLayout.NORTH);
        add(metrics, BorderLayout.SOUTH);
        add(groupTableScroll);
    }

    public void hideIDColumns() {

        for(int i = 0; i < 3; i++) {

            groupTable.getColumnModel().getColumn(i).setMinWidth(0);
            groupTable.getColumnModel().getColumn(i).setMaxWidth(0);
            groupTable.getColumnModel().getColumn(i).setWidth(0);
            groupTable.getColumnModel().getColumn(i).setPreferredWidth(0);
            groupTable.getTableHeader().getColumnModel().getColumn(i).setMaxWidth(0);
            groupTable.getTableHeader().getColumnModel().getColumn(i).setMinWidth(0);
            groupTable.getTableHeader().getColumnModel().getColumn(i).setPreferredWidth(0);
        }
    }

    public void getReplacementPairs() {
        this.replacementListPair = this.gui.getPairReplacementList();
    }

    public List<Pair> getPairs() {
        return this.gui.getPairs();
    }

    public void enableElements() {
        switchPairBtn.setEnabled(true);
        switchPersonBtn.setEnabled(true);
        groupTable.setEnabled(true);
    }

    public void updateTablePair(Pair oldPair, Pair newPair) {

        List<Group> pairAssignedGroups = new ArrayList<>();

        groups.forEach(el -> {
            List<Pair> pairs = new ArrayList<>(el.getPairs());
            pairs.forEach(x -> {
                if (x.getPairID().equals(oldPair.getPairID())) {
                    pairAssignedGroups.add(el);
                }
            });
        });


        for (int i = 0; i < pairAssignedGroups.size(); i++) {
            if (pairAssignedGroups.get(i).getCookID().equals(oldPair.getPairID())) {
                pairAssignedGroups.get(i).setCookID(newPair.getPairID());
                pairAssignedGroups.get(i).setGroupKitchen(newPair.getKitchen());
            }
        }

        newPair.setHasCooked(true);
        newPair.setSignedInTogether(false);

        PathCalculator.calculateNewPathLength(replaceOldPairWithNewPair(pairAssignedGroups, oldPair, newPair), groups, afterParty);

        DefaultTableModel tableModel = (DefaultTableModel) groupTable.getModel();
        tableModel.setDataVector(getTableData(), getTableColumnNames());
        tableModel.fireTableDataChanged();
        updateMetrics();
        hideIDColumns();

        if (lastSelectedRow >= 0 && lastSelectedColumn >= 0 && lastSelectedRow < groupTable.getRowCount() && lastSelectedColumn < groupTable.getColumnCount()) {
            groupTable.setRowSelectionInterval(lastSelectedRow, lastSelectedRow);
            groupTable.setColumnSelectionInterval(lastSelectedColumn, lastSelectedColumn);
        }
        currentSelectedPair = newPair;
        getPotentialPairs();
    }

    // Hilfsmethode, um die Daten für die Tabelle zu erhalten
    private Object[][] getTableData() {
        Object[][] data = new Object[groups.size()][getColumnCount()];
        for (int i = 0; i < groups.size(); i++) {
            Group group = groups.get(i);
            data[i] = new Object[]{
                    group.getPair1().getPairID(),
                    group.getPair2().getPairID(),
                    group.getPair3().getPairID(),
                    group.getPair1().getPerson1().getName() + " & " + group.getPair1().getPerson2().getName(),
                    group.getPair2().getPerson1().getName() + " & " + group.getPair2().getPerson2().getName(),
                    group.getPair3().getPerson1().getName() + " & " + group.getPair3().getPerson2().getName(),
                    convertMealTypeToString(group.getMealType())
            };
        }
        return data;
    }

    private String[] getTableColumnNames() {
        return new String[]{"ID1", "ID2", "ID3", "1. Paar", "2. Paar", "3. Paar", "Gang"};
    }

    private int getColumnCount() {
        return getTableColumnNames().length;
    }

    private String convertMealTypeToString(MealType mealtype) {
        switch (mealtype) {
            case STARTER:
                return "Vorspeise";
            case MAIN:
                return "Hauptspeise";
            case DESSERT:
                return "Nachspeise";
            default:
                return "";
        }
    }

    private List<Group> replaceOldPairWithNewPair(List<Group> pairAssignedGroups, Pair oldPair, Pair newPair){

        for (int i = 0; i < pairAssignedGroups.size(); i++) {

            for (int j = 0; j < pairAssignedGroups.get(i).getPairs().size(); j++) {

                if (pairAssignedGroups.get(i).getPairs().get(j).getPairID().equals(oldPair.getPairID())) {

                    if (j == 0) {
                        pairAssignedGroups.get(i).setPair1(newPair);
                    } else if (j == 1) {
                        pairAssignedGroups.get(i).setPair2(newPair);
                    } else if (j == 2) {
                        pairAssignedGroups.get(i).setPair3(newPair);
                    }
                }
            }
        }

        return pairAssignedGroups;
    }

    public void updateMetrics() {
        getReplacementPairs();
        GroupMetrics groupMetrics = new GroupMetrics(groups, replacementListPair);
        String metricsText = "Gruppen: " + groupMetrics.getCreatedGroupsCount() + ", Nachrückende Pärchen: " + groupMetrics.getReplacementGroupCount() +
                ", Geschlechterdiversität: " + groupMetrics.getAvgGenderDiversity() +  ", Altersdifferenz: " + groupMetrics.getAvgAgeDifference() + ", Vorliebenabweichung: " +
                groupMetrics.getAvgFoodPreferenceDeviation() + ", Weglänge: " + groupMetrics.getPathLength();
        metrics.setText(metricsText);
        metrics.setToolTipText(metricsText);
    }

    public void disableTableElements() {
        groupTable.setEnabled(false);
        switchPairBtn.setEnabled(false);
        switchPersonBtn.setEnabled(false);
    }

    public List<Person> getValidPersonCandidates(List<Person> persons, Person person) {

        List<Person> candidates = new ArrayList<>();

        for (int i = 0; i < persons.size(); i++) {

            if (persons.get(i).getFoodPreference() == currentSelectedPair.getFoodPreference()) {

                if (PairAssigner.checkKitchenAvailability(person, persons.get(i))) {

                    candidates.add(persons.get(i));
                }
            }
        }

        return candidates;
    }

    public void getPotentialPairs() {

        List<Pair> validPairs = new ArrayList<>();

        for(int i = 0; i < replacementListPair.size(); i++) {

            if(replacementListPair.get(i).getFoodPreference() == currentSelectedPair.getFoodPreference()) {
                validPairs.add(replacementListPair.get(i));
            }
        }

        potentialPairs = validPairs;
    }

    public void updateTable(List<Group> groups) {

        this.groups = groups;
        Collections.sort(this.groups);
        DefaultTableModel tableModel = (DefaultTableModel) groupTable.getModel();
        tableModel.setDataVector(getTableData(), getTableColumnNames());
        tableModel.fireTableDataChanged();
        updateMetrics();
        hideIDColumns();
    }
}
