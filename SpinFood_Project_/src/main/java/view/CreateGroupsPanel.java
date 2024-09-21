package view;

import data.Coordinate;
import data.EFoodPreference;
import data.Pair;
import view.dialogs.CreateGroupDialog;
import view.tables.PairReplacementTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CreateGroupsPanel extends JPanel {

    JPanel container = new JPanel(new BorderLayout());
    JPanel listPanel = new JPanel(new GridLayout(1, 3));
    JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

    JList<Pair> firstPairList;
    JList<Pair> secondPairList;
    JList<Pair> thirdPairList;
    DefaultListModel<Pair> listModel;

    JButton cancelButton;
    JButton createButton;

    PairReplacementTable pairReplacementTable;

    GUI gui;
    Coordinate afterParty;

    public CreateGroupsPanel(List<Pair> pairs, PairReplacementTable pairReplacementTable, GUI gui) {

        this.gui = gui;
        setLayout(new BorderLayout());
        this.pairReplacementTable = pairReplacementTable;
        listModel = new DefaultListModel<>();

        firstPairList = new JList<>(listModel);
        firstPairList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        secondPairList = new JList<>(listModel);
        secondPairList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        thirdPairList = new JList<>(listModel);
        thirdPairList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        firstPairList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Pair selectedPair = firstPairList.getSelectedValue();
                    if (selectedPair != null) {

                        if(checkIfBothSelected()) {
                            createButton.setEnabled(true);
                        }
                    }
                }
            }
        });

        secondPairList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Pair selectedPair = secondPairList.getSelectedValue();
                    if (selectedPair != null) {
                        if(checkIfBothSelected()) {
                            createButton.setEnabled(true);
                        }
                    }
                }
            }
        });

        thirdPairList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Pair selectedPair = thirdPairList.getSelectedValue();
                    if (selectedPair != null) {
                        if(checkIfBothSelected()) {
                            createButton.setEnabled(true);
                        }
                    }
                }
            }
        });

        JScrollPane scrollPaneFirst = new JScrollPane(firstPairList);
        JScrollPane scrollPaneSecond = new JScrollPane(secondPairList);
        JScrollPane scrollPaneThird = new JScrollPane(thirdPairList);

        for (Pair pair : pairs) {
            listModel.addElement(pair);
        }

        listPanel.add(scrollPaneFirst);
        listPanel.add(scrollPaneSecond);
        listPanel.add(scrollPaneThird);

        cancelButton = new JButton("Abbrechen");

        ActionListener cancelListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        };
        cancelButton.addActionListener(cancelListener);


        createButton = new JButton("Fortfahren");
        createButton.setEnabled(false);
        ActionListener createListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(getSelectedPairFromFirstList().getPairID().equals(getSelectedPairFromSecondList().getPairID())
                        && getSelectedPairFromFirstList().getPairID().equals(getSelectedPairFromThirdList().getPairID())
                        || getSelectedPairFromFirstList().getPairID().equals(getSelectedPairFromSecondList().getPairID())
                        || getSelectedPairFromSecondList().getPairID().equals(getSelectedPairFromThirdList().getPairID())
                        || getSelectedPairFromFirstList().getPairID().equals(getSelectedPairFromThirdList().getPairID())
                ) {
                    JOptionPane.showMessageDialog(null, "Paare müssen unterschiedlich sein",
                            "Paarung nicht möglich", JOptionPane.INFORMATION_MESSAGE);
                }
/*                else if(!PairAssigner.checkKitchenAvailability(getSelectedPersonFromFirstList(), getSelectedPersonFromSecondList())) {
                    JOptionPane.showMessageDialog(null, gui.getTranslatedString("pair_one_kitche_warning"),
                            gui.getTranslatedString("pairing_not_possible_warning"), JOptionPane.INFORMATION_MESSAGE);
                }*/
                else {

                    boolean warning = true;

                    if (!(getSelectedPairFromFirstList().getFoodPreference() == getSelectedPairFromSecondList().getFoodPreference() &&
                            getSelectedPairFromSecondList().getFoodPreference() == getSelectedPairFromThirdList().getFoodPreference())) {

                        List<Pair> pairFood = new ArrayList<>(List.of(getSelectedPairFromFirstList(), getSelectedPairFromSecondList(), getSelectedPairFromThirdList()));

                        int nonePairCount = 0;
                        int meatPairCount = 0;

                        for (int f = 0; f < pairFood.size(); f++) {

                            if (pairFood.get(f).getFoodPreference() == EFoodPreference.MEAT) {
                                meatPairCount++;
                            } else if (pairFood.get(f).getFoodPreference() == EFoodPreference.NONE) {
                                nonePairCount++;
                            }
                        }

                        if ((nonePairCount <= 1 && meatPairCount == 0) || (meatPairCount <= 1 && nonePairCount == 0)) {

                            warning = false;
                        }
                    }

                    new CreateGroupDialog(List.of(getSelectedPairFromFirstList(), getSelectedPairFromSecondList(), getSelectedPairFromThirdList()), gui, warning);
                }

            }
        };

        createButton.addActionListener(createListener);

        buttonPanel.add(cancelButton);
        buttonPanel.add(createButton);

        container.add(listPanel, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);
        add(container);
    }

    private void goBack() {
        this.pairReplacementTable.hideCreateGroupsPanel();
    }

    public Pair getSelectedPairFromFirstList() {
        return firstPairList.getSelectedValue();
    }

    public Pair getSelectedPairFromSecondList() {
        return secondPairList.getSelectedValue();
    }

    public Pair getSelectedPairFromThirdList() {
        return thirdPairList.getSelectedValue();
    }

    private boolean checkIfBothSelected() {

        boolean selected = false;

        if(getSelectedPairFromFirstList() != null && getSelectedPairFromSecondList() != null && getSelectedPairFromThirdList() != null) {
            selected = true;
        }

        return selected;
    }
}
