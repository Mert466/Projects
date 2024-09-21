package controller;

import data.Pair;

import java.util.List;

/**
 * The ListAndCutElements class represents a container for two lists of Pair objects.
 */
public class ListAndCutElements {

    private List<Pair> cutList;
    private List<Pair> cutElements;

    /**
     * Constructs a ListAndCutElements object with the specified lists.
     *
     * @param cutList      the list of pairs representing the cut list
     * @param cutElements  the list of pairs representing the cut elements
     */
    public ListAndCutElements(List<Pair> cutList, List<Pair> cutElements) {
        this.cutList = cutList;
        this.cutElements = cutElements;
    }

    /**
     * Retrieves the cut list.
     *
     * @return the list of pairs representing the cut list
     */
    public List<Pair> getCutList() {
        return cutList;
    }

    /**
     * Retrieves the cut elements.
     *
     * @return the list of pairs representing the cut elements
     */
    public List<Pair> getCutElements() {
        return cutElements;
    }
}