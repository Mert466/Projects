package controller;


import data.Pair;
import data.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * TableStates class manages the history of table states and provides undo and redo functionality.
 */
public class TableStates {
    private List<TableState> history;
    private int currentIndex;

    /**
     * Initializes a new instance of the TableStates class.
     */
    public TableStates() {
        this.history = new ArrayList<>();
        this.currentIndex = -1;
    }

    /**
     * Saves the current state of the table.
     *
     * @param moveUpPersonTable  the list of persons in the move-up table
     * @param moveUpPairTable    the list of pairs in the move-up table
     * @param groups             the list of groups
     */
    public void saveState(List<Person> moveUpPersonTable, List<Pair> moveUpPairTable, List<Group> groups) {
        List<Person> moveUpPersonTableCopy = new ArrayList<>(moveUpPersonTable);
        List<Pair> moveUpPairTableCopy = new ArrayList<>(moveUpPairTable);
        List<Group> groupsCopy = new ArrayList<>(groups);

        TableState currentState = new TableState(moveUpPersonTableCopy, moveUpPairTableCopy, groupsCopy);
        if (currentIndex < history.size() - 1) {
            // Remove all states after the current index
            history.subList(currentIndex + 1, history.size()).clear();
        }
        history.add(currentState);
        currentIndex++;
    }

    /**
     * Resets the table states, clearing the history.
     */
    public void reset() {
        history.clear();
        currentIndex = -1;
    }

    /**
     * Undoes the last table state change.
     */
    public void undo() {
        System.out.println(currentIndex);
        if (currentIndex > 0) {
            currentIndex--;
        }
        System.out.println(currentIndex);

    }

    /**
     * Redoes the last undone table state change.
     */
    public void redo() {
        if (currentIndex < history.size() - 1) {
            currentIndex++;
        }
    }

    /**
     * Retrieves the current move-up person table.
     *
     * @return the current move-up person table, or null if no table state is available
     */
    public List<Person> getCurrentMoveUpPersonTable() {
        if (currentIndex >= 0 && currentIndex < history.size()) {
            return new ArrayList<>(history.get(currentIndex).getMoveUpPersonTable());
        }
        return null;
    }

    /**
     * Retrieves the current move-up pair table.
     *
     * @return the current move-up pair table, or null if no table state is available
     */
    public List<Pair> getCurrentMoveUpPairTable() {
        if (currentIndex >= 0 && currentIndex < history.size()) {
            return new ArrayList<>(history.get(currentIndex).getMoveUpPairTable());
        }
        return null;
    }

    /**
     * Retrieves the current groups.
     *
     * @return the current groups, or null if no table state is available
     */
    public List<Group> getCurrentGroups() {
        if (currentIndex >= 0 && currentIndex < history.size()) {
            return new ArrayList<>(history.get(currentIndex).getGroups());
        }
        return null;
    }
}
/**
 * TableState class represents a state of the table with the move-up person table, move-up pair table, and groups.
 */
class TableState {
    private List<Person> moveUpPersonTable;
    private List<Pair> moveUpPairTable;
    private List<Group> groups;

    /**
     * Initializes a new instance of the TableState class.
     *
     * @param moveUpPersonTable  the list of persons in the move-up table
     * @param moveUpPairTable    the list of pairs in the move-up table
     * @param groups             the list of groups
     */
    public TableState(List<Person> moveUpPersonTable, List<Pair> moveUpPairTable, List<Group> groups) {
        this.moveUpPersonTable = new ArrayList<>(moveUpPersonTable);
        this.moveUpPairTable = new ArrayList<>(moveUpPairTable);
        this.groups = new ArrayList<>(groups);
    }

    /**
     * Retrieves the move-up person table.
     *
     * @return the move-up person table
     */
    public List<Person> getMoveUpPersonTable() {
        return moveUpPersonTable;
    }

    /**
     * Retrieves the move-up pair table.
     *
     * @return the move-up pair table
     */
    public List<Pair> getMoveUpPairTable() {
        return moveUpPairTable;
    }

    /**
     * Retrieves the groups.
     *
     * @return the groups
     */
    public List<Group> getGroups() {
        return groups;
    }
}