package view.tables;

import data.Coordinate;
import data.EFoodPreference;
import data.Pair;
import view.CreateGroupsPanel;
import view.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The PairReplacementTable class represents a JPanel that displays a table of replacement pairs.
 * Each pair consists of two persons and their food preference.
 * The table is populated with the provided list of pairs.
 */
public class PairReplacementTable extends JPanel {

    List<Pair> pairs = new ArrayList<>();

    JPanel pairReplacementPanel = new JPanel(new BorderLayout());
    CreateGroupsPanel createGroupPanel;

    JScrollPane replacementPairScroll = new JScrollPane();
    JTable replacementPairTable;
    JPanel headerPanel = new JPanel(new BorderLayout());

    JButton createButton;
    GUI gui;
    Coordinate afterParty;
    /**
     * Constructs a PairReplacementTable object with the given list of pairs.
     *
     * @param pairs the list of pairs to display in the table
     */
    public PairReplacementTable(List<Pair> pairs, GUI gui, Coordinate afterParty) {
        this.gui = gui;
        this.afterParty = afterParty;
        this.pairs.addAll(pairs);

        setLayout(new BorderLayout());
        JLabel replacementPairHeading = new JLabel("Nachrückende Pärchen");
        initTable();
        addEntriesToTable(pairs);

        createButton = new JButton("+ Gruppe erstellen");

        ActionListener createListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = pairReplacementPanel.getParent();
                parent.remove(pairReplacementPanel);
                parent.revalidate();
                parent.repaint();
                createGroupPanel = new CreateGroupsPanel(PairReplacementTable.this.pairs, PairReplacementTable.this, gui);
                add(createGroupPanel, BorderLayout.CENTER);
            }
        };

        createButton.addActionListener(createListener);

        headerPanel.add(replacementPairHeading, BorderLayout.WEST);
        headerPanel.add(createButton, BorderLayout.EAST);

        pairReplacementPanel.add(headerPanel, BorderLayout.NORTH);
        pairReplacementPanel.add(replacementPairScroll);

        add(pairReplacementPanel);
    }

    private void initTable() {
        String[] replacementPersonColumns = {"ID", "1. Person", "2. Person", "Vorliebe"};
        TableModel tableModel = new DefaultTableModel(replacementPersonColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Zellen nicht editierbar machen
            }
        };
        replacementPairTable = new JTable(tableModel);
        replacementPairScroll.setViewportView(replacementPairTable);
    }

    /**
     * Adds entries to the replacement pair table based on the given list of pairs.
     *
     * @param pairs the list of pairs to add to the table
     */
    public void addEntriesToTable(List<Pair> pairs) {

        DefaultTableModel tableModel = (DefaultTableModel) replacementPairTable.getModel();

        for (Pair pair : pairs) {
            String foodPreference = getFoodPreferenceString(pair.getFoodPreference());
            Object[] newEntry = {pair.getPairID(), pair.getPerson1().getName(), pair.getPerson2().getName(), foodPreference};
            tableModel.addRow(newEntry);

            if (!this.pairs.contains(pair)) {
                this.pairs.add(pair);
            }
        }

        hideIDColumn();
    }

    public void removeEntriesFromTable(List<Pair> pairsToRemove) {

        for(int i = pairs.size() - 1; i >= 0; i--) {

            for(int j = pairsToRemove.size() - 1; i >= 0; i--) {

                if(pairs.get(i).getPairID().equals(pairsToRemove.get(j).getPairID())) {
                    pairs.remove(i);
                }
            }
        }

        DefaultTableModel tableModel = (DefaultTableModel) replacementPairTable.getModel();

        for (Pair pair : pairsToRemove) {
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                String pairID = (String) tableModel.getValueAt(row, 0);
                if (pair.getPairID().equals(pairID)) {
                    tableModel.removeRow(row);
                    break;
                }
            }
        }
    }

    public List<Pair> getPairs() {

        return this.pairs;
    }

    /**
     * Converts the food preference enum value into a corresponding string representation.
     *
     * @param foodPreference the food preference enum value
     * @return the string representation of the food preference
     */
    private String getFoodPreferenceString(EFoodPreference foodPreference) {
        switch (foodPreference) {
            case NONE:
                return "Keine";
            case MEAT:
                return "Fleisch";
            case VEGAN:
                return "Vegan";
            case VEGGIE:
                return "Vegetarisch";
            default:
                return "";
        }
    }

    private void hideIDColumn() {
        replacementPairTable.getColumnModel().getColumn(0).setMinWidth(0);
        replacementPairTable.getColumnModel().getColumn(0).setMaxWidth(0);
        replacementPairTable.getColumnModel().getColumn(0).setWidth(0);
        replacementPairTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        replacementPairTable.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        replacementPairTable.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        replacementPairTable.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void updateTable(List<Pair> pairs) {
        // Alte Einträge entfernen
        DefaultTableModel tableModel = (DefaultTableModel) replacementPairTable.getModel();
        tableModel.setRowCount(0);

        // Neue Einträge hinzufügen
        this.pairs = pairs;
        addEntriesToTable(pairs);
    }

    public void hideCreateGroupsPanel() {
        Container parent = createGroupPanel.getParent();
        parent.remove(createGroupPanel);
        parent.revalidate();
        parent.repaint();
        add(pairReplacementPanel, BorderLayout.CENTER);
    }
}
