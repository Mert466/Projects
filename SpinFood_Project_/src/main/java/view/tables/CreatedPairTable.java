package view.tables;

import controller.PairMetrics;
import data.Pair;
import data.Person;
import view.GUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * The CreatedPairTable class represents a JPanel that displays a table of created pairs.
 * It provides functionality to delete selected pairs and updates related metrics and tables.
 */
public class CreatedPairTable extends JPanel {

    private final GUI gui;
    JLabel metrics = new JLabel();
    String selectedPairID;
    List<Pair> pairs;
    JTable pairTable;
    JButton deletePairButton;
    JLabel pairTableHeading;

    /**
     * Constructs a CreatedPairTable object with the given list of pairs and GUI reference.
     *
     * @param pairs the list of pairs to display in the table.
     * @param gui   the GUI reference.
     */
    public CreatedPairTable(List<Pair> pairs, GUI gui) {
        Collections.sort(pairs);
        setLayout(new BorderLayout());

        this.pairs = pairs;
        this.gui = gui;

        DefaultTableModel model = new DefaultTableModel(getTableData(), getTableColumnNames()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Setze alle Zellen als nicht editierbar
            }
        };

        pairTableHeading = new JLabel(gui.getTranslatedString("all_pairs"));

        getCurrentMetrics();

        JScrollPane pairTableScroll = new JScrollPane();
        pairTable = new JTable(model);
/*
        pairTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
*/
        hideIDColumns();

        deletePairButton = new JButton(gui.getTranslatedString("dissolve"));
        deletePairButton.setEnabled(false);
        ActionListener deletePairListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(selectedPairID != null) {
                    Pair deletePair = null;
                    for(int i = 0; i < CreatedPairTable.this.pairs.size(); i++) {
                        if(CreatedPairTable.this.pairs.get(i).getPairID().equals(selectedPairID)) {
                            deletePair = CreatedPairTable.this.pairs.get(i);
                        }
                    }

                    assert deletePair != null;
                    gui.dissolvePair(deletePair);
                    selectedPairID = null;
                }
            }
        };

        deletePairButton.addActionListener(deletePairListener);


        pairTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Überprüfe, ob die Auswahl abgeschlossen ist

                    int selectedRow = pairTable.getSelectionModel().getMinSelectionIndex();

                    if (selectedRow != -1) { // Überprüfe, ob eine Zeile ausgewählt wurde
                        selectedPairID = (String) pairTable.getValueAt(selectedRow, 0);
                        deletePairButton.setEnabled(true);
                    }
                }
            }
        });

        JPanel tableHeader = new JPanel(new BorderLayout());
        tableHeader.add(pairTableHeading, BorderLayout.WEST);
        tableHeader.add(deletePairButton, BorderLayout.EAST);

        pairTableScroll.setViewportView(pairTable);
        add(tableHeader, BorderLayout.NORTH);
        add(metrics, BorderLayout.SOUTH);
        add(pairTableScroll);
    }

    /**
     * Retrieves the person replacement list from the GUI.
     *
     * @return the list of persons in the replacement list.
     */
    public List<Person> getPersonReplacementList() {

        return gui.getPersonReplacementList();
    }

    /**
     * Updates the metrics label with the latest pair metrics.
     *
     */
    public void getCurrentMetrics() {
        PairMetrics pairMetrics = new PairMetrics(pairs, getPersonReplacementList());
        String metricsText = "Paare: " + pairMetrics.getCreatedPairsCount() + ", Nachrückende Personen: " + pairMetrics.getReplacementPairCount() +
                ", Geschlechterdiversität: " + pairMetrics.getAvgGenderDiversity() +  ", Altersdifferenz: " + pairMetrics.getAvgAgeDifference() + ", Vorliebenabweichung: " +
                pairMetrics.getAvgFoodPreferenceDeviation();
        metrics.setText(metricsText);
        metrics.setToolTipText(metricsText);
    }

    public List<Pair> getPairs() {
        return this.pairs;
    }

    /**
     * Switches the specified switchPair with the selectedPair in the list of pairs.
     * The table displaying the pairs is updated to reflect the changes, and the metrics display is updated.
     * @param switchPair   the pair to be switched
     * @param selectedPair the pair to be inserted in place of the switchPair
     */
    public void switchPair(Pair switchPair, Pair selectedPair) {
        for(int i = pairs.size() - 1; i >= 0; i--) {
            if(pairs.get(i).getPairID().equals(switchPair.getPairID())) {
                pairs.remove(i);
                pairs.add(i, selectedPair);
            }
        }
        updateTablePair(switchPair, selectedPair);
        getCurrentMetrics();
    }
    /**
     * Updates the pair table by replacing the oldPair with the newPair.
     * The table displaying the pairs is updated to reflect the changes, and the ID columns are hidden.
     * @param oldPair the pair to be replaced in the table
     * @param newPair the pair to be inserted in place of the oldPair
     */
    public void updateTablePair(Pair oldPair, Pair newPair) {

        for(int i = pairs.size() - 1; i >= 0; i--) {
            if(pairs.get(i).getPairID().equals(oldPair.getPairID())) {
                pairs.remove(i);
                pairs.add(i, newPair);
            }
        }

        DefaultTableModel tableModel = (DefaultTableModel) pairTable.getModel();
        tableModel.setDataVector(getTableData(), getTableColumnNames());
        tableModel.fireTableDataChanged();

        hideIDColumns();
    }

    public void hideIDColumns() {
        pairTable.getColumnModel().getColumn(0).setMinWidth(0);
        pairTable.getColumnModel().getColumn(0).setMaxWidth(0);
        pairTable.getColumnModel().getColumn(0).setWidth(0);
        pairTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        pairTable.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        pairTable.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        pairTable.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    private String[] getTableColumnNames() {
        return new String[]{"ID", "1. Person", "2. Person", "Vorliebe"};
    }

    private Object[][] getTableData() {
        Object[][] createdPairData = {};

        for (int i = 0; i < pairs.size(); i++) {

            String foodPreference = "";

            switch (pairs.get(i).getPerson2().getFoodPreference()) {
                case NONE -> foodPreference = "Keine";
                case MEAT -> foodPreference = "Fleisch";
                case VEGAN -> foodPreference = "Vegan";
                case VEGGIE -> foodPreference = "Vegetarisch";
            }

            Object[] newEntry = {pairs.get(i).getPairID(), pairs.get(i).getPerson1().getName(), pairs.get(i).getPerson2().getName(), foodPreference};
            createdPairData = Arrays.copyOf(createdPairData, createdPairData.length + 1);
            createdPairData[createdPairData.length - 1] = newEntry;
        }

        return createdPairData;
    }
    /**
     * Updates the pair table with the provided list of pairs.
     * The method updates the internal pairs list, refreshes the table model with the new data and column names,
     * hides the ID columns in the table, and updates the metrics display.
     *
     * @param pairs the list of pairs to be displayed in the table
     */
    public void updateTable(List<Pair> pairs) {
        List<Pair> pairList = new ArrayList<>(pairs);
        Collections.sort(pairList);
        this.pairs = pairList;
        DefaultTableModel tableModel = (DefaultTableModel) pairTable.getModel();
        tableModel.setDataVector(getTableData(), getTableColumnNames());
        hideIDColumns();
        getCurrentMetrics();
    }

    public void updateTexts() {
        deletePairButton.setText(gui.getTranslatedString("dissolve"));
        pairTableHeading.setText(gui.getTranslatedString("all_pairs"));
    }
}