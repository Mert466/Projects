package view.tables;

import controller.PairAssigner;
import data.EFoodPreference;
import data.Pair;
import data.Person;
import view.GUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  The PersonReplacementTable class represents a JPanel that displays a table of replacement persons.
 *  Each person is associated with their food preference.
 *  The table is populated with the provided list of persons.
 */

public class PersonReplacementTable extends JPanel{

    JLabel replacementPersonHeading;
    JScrollPane replacementPersonScroll = new JScrollPane();
    JTable replacementPersonTable;
    List<Person> allPerson = new ArrayList<>();
    JPanel createPairPanel;
    JPanel personTablePanel;
    List<Person> persons;
    JButton createPairButton;
    JButton createButton;
    Person selectedPersonOne;
    Person selectedPersonTwo;
    JList<Person> firstPersonList;
    JList<Person> secondPersonList;
    boolean areBothListsSelected = false;
    GUI gui;
    DefaultListModel<Person> listModel;
    JButton cancelCreateButton;
    /**
     * Constructs a PersonReplacementTable object with the provided list of persons.
     *
     * @param persons the list of persons to populate the table with
     */
    public PersonReplacementTable(List<Person> persons, GUI gui) {
        this.gui = gui;
        this.persons = persons;
        initTable();
        createPairPanel = new JPanel(new BorderLayout());
        personTablePanel = new JPanel(new BorderLayout());

        replacementPersonHeading = new JLabel(gui.getTranslatedString("successorPersons"));

        createPairButton = new JButton(gui.getTranslatedString("createPair"));
        ActionListener createPairListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = personTablePanel.getParent();
                parent.remove(personTablePanel);
                parent.revalidate();
                parent.repaint();
                listModel.clear();
                for(int i = 0; i < PersonReplacementTable.this.persons.size(); i++) {
                    listModel.addElement(PersonReplacementTable.this.persons.get(i));
                }
                add(createPairPanel);
            }
        };
        createPairButton.addActionListener(createPairListener);

        cancelCreateButton = new JButton(gui.getTranslatedString("cancel"));
        ActionListener cancelCreateListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelCreation();
            }
        };
        cancelCreateButton.addActionListener(cancelCreateListener);

        createButton = new JButton(gui.getTranslatedString("create"));
        ActionListener createListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(getSelectedPersonFromFirstList().getUUID().equals(getSelectedPersonFromSecondList().getUUID())) {
                    JOptionPane.showMessageDialog(null, gui.getTranslatedString("different_pair_warning"),
                            gui.getTranslatedString("pairing_not_possible_warning"), JOptionPane.INFORMATION_MESSAGE);
                }
                else if(!PairAssigner.checkKitchenAvailability(getSelectedPersonFromFirstList(), getSelectedPersonFromSecondList())) {
                    JOptionPane.showMessageDialog(null, gui.getTranslatedString("pair_one_kitche_warning"),
                            gui.getTranslatedString("pairing_not_possible_warning"), JOptionPane.INFORMATION_MESSAGE);
                }
                else if(!PairAssigner.checkFoodPreferenceValidation(getSelectedPersonFromFirstList(), getSelectedPersonFromSecondList())) {
                    JOptionPane.showMessageDialog(null, gui.getTranslatedString("pair_food_preferene_warning="),
                            gui.getTranslatedString("pairing_not_possible_warning"), JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    Pair newPair = new Pair(getSelectedPersonFromFirstList(), getSelectedPersonFromSecondList());
                    gui.onNewPearCreated(newPair);
                    cancelCreation();
                }
            }
        };
        createButton.addActionListener(createListener);
        createButton.setEnabled(false);
        listModel = new DefaultListModel<>();

        secondPersonList = new JList<>(listModel);
        secondPersonList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        firstPersonList = new JList<>(listModel);
        firstPersonList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        firstPersonList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Person selectedPerson = firstPersonList.getSelectedValue();
                    if (selectedPerson != null) {
                        disableSelectedItemInList(secondPersonList, selectedPerson);
                        if(checkIfBothSelected()) {
                            createButton.setEnabled(true);
                        }
                    }
                }
            }
        });

        secondPersonList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Person selectedPerson = secondPersonList.getSelectedValue();
                    if (selectedPerson != null) {
                        disableSelectedItemInList(firstPersonList, selectedPerson);
                        if(checkIfBothSelected()) {
                            createButton.setEnabled(true);
                        }
                    }
                }
            }
        });

        JScrollPane scrollPaneFirst = new JScrollPane(firstPersonList);
        JScrollPane scrollPaneSecond = new JScrollPane(secondPersonList);

        setLayout(new BorderLayout());

        JPanel listPanel = new JPanel(new GridLayout(1, 2));
        listPanel.add(scrollPaneFirst);
        listPanel.add(scrollPaneSecond);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(cancelCreateButton);
        buttonPanel.add(createButton);

        createPairPanel.add(listPanel);
        createPairPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel personTableHeader = new JPanel(new BorderLayout());
        personTableHeader.add(replacementPersonHeading, BorderLayout.WEST);
        personTableHeader.add(createPairButton, BorderLayout.EAST);
        personTablePanel.add(personTableHeader, BorderLayout.NORTH);
        personTablePanel.add(replacementPersonScroll);
        add(personTablePanel);
    }

    /**
     * Initializes the table with the column names and sets up the table view.
     */
    private void initTable() {

        TableModel tableModel = new DefaultTableModel(getTableData(), getTableColumnNames()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Zellen nicht editierbar machen
            }
        };

        replacementPersonTable = new JTable(tableModel);
        replacementPersonScroll.setViewportView(replacementPersonTable);
        hideIDColumn();
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

    /**
     * Returns the list of all persons displayed in the table.
     *
     * @return the list of all persons
     */
    public List<Person> getAllPerson() {
        return persons;
    }

/*
    public void updateTable(Person oldPerson, Person newPerson) {

        for(int i = persons.size() - 1; i >= 0; i--) {
            if(persons.get(i).getUUID().equals(oldPerson.getUUID())) {
                persons.remove(i);
                persons.add(i, newPerson);
            }
        }

        DefaultTableModel tableModel = (DefaultTableModel) replacementPersonTable.getModel();
        tableModel.setDataVector(getTableData(), getTableColumnNames());
        tableModel.fireTableDataChanged();

        hideIDColumn();
    }
*/

    public void hideIDColumn() {
        replacementPersonTable.getColumnModel().getColumn(0).setMinWidth(0);
        replacementPersonTable.getColumnModel().getColumn(0).setMaxWidth(0);
        replacementPersonTable.getColumnModel().getColumn(0).setWidth(0);
        replacementPersonTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        replacementPersonTable.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        replacementPersonTable.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        replacementPersonTable.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public Object[][] getTableData() {

        Object[][] personData = {};

        for (Person person : persons) {
            Object[] newEntry = {person.getUUID(), person.getName(), getFoodPreferenceString(person.getFoodPreference())};
            personData = Arrays.copyOf(personData, personData.length + 1);
            personData[personData.length - 1] = newEntry;
        }

        return personData;
    }

    private String[] getTableColumnNames() {
        return new String[]{"ID", "Person", "Vorliebe"};
    }

    public void updateTable(List<Person> persons) {
        this.persons = persons;
        DefaultTableModel tableModel = (DefaultTableModel) replacementPersonTable.getModel();
        tableModel.setDataVector(getTableData(), getTableColumnNames());
        hideIDColumn();

        if(persons.size() < 2) {
            createPairButton.setEnabled(false);
        }

    }

    private static void disableSelectedItemInList(JList<Person> list, Person selectedPerson) {
        ListModel<Person> model = list.getModel();
        int size = model.getSize();

        for (int i = 0; i < size; i++) {
            Person person = model.getElementAt(i);
            if (person.equals(selectedPerson)) {
                list.setCellRenderer(new DisabledItemRenderer(i));
                break;
            }
        }
    }

    static class DisabledItemRenderer extends DefaultListCellRenderer {
        private int disabledIndex;

        public DisabledItemRenderer(int disabledIndex) {
            this.disabledIndex = disabledIndex;
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (index == disabledIndex) {
                component.setEnabled(false);
            } else {
                component.setEnabled(true);
            }
            return component;
        }
    }

    public Person getSelectedPersonFromFirstList() {
        return firstPersonList.getSelectedValue();
    }

    public Person getSelectedPersonFromSecondList() {
        return secondPersonList.getSelectedValue();
    }

    private boolean checkIfBothSelected() {

        boolean selected = false;

        if(getSelectedPersonFromFirstList() != null && getSelectedPersonFromSecondList() != null) {
            selected = true;
        }

        return selected;
    }

    private void cancelCreation() {

        DefaultListModel<Person> model = (DefaultListModel<Person>) firstPersonList.getModel();
        model.clear();

        for(int i = 0; i < persons.size(); i++) {
            model.addElement(persons.get(i));
        }

        ListCellRenderer<Object> renderer = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                component.setEnabled(true);
                return component;
            }
        };

        firstPersonList.setCellRenderer(renderer);
        secondPersonList.setCellRenderer(renderer);
        firstPersonList.revalidate();
        firstPersonList.repaint();
        secondPersonList.revalidate();
        secondPersonList.repaint();

        firstPersonList.clearSelection();
        secondPersonList.clearSelection();
        areBothListsSelected = false;
        createButton.setEnabled(false);
        Container parent = createPairPanel.getParent();
        parent.remove(createPairPanel);
        parent.revalidate();
        parent.repaint();
        add(personTablePanel);
    }

    public void updateTexts() {
        replacementPersonHeading.setText(gui.getTranslatedString("successorPersons"));
        createPairButton.setText(gui.getTranslatedString("createPair"));
        cancelCreateButton.setText(gui.getTranslatedString("cancel"));
        createButton.setText(gui.getTranslatedString("create"));
    }
}