package view;

import data.Pair;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a panel for switching pairs.
 */
public class SwitchPairPanel extends JPanel {

    GUI gui;
    JButton executeSwitchButton;
    Pair selectedPair;

    /**
     * Constructs a SwitchPairPanel with the given switch pair, candidate pairs, and GUI instance.
     *
     * @param switchPair the pair to be switched
     * @param candidates the list of candidate pairs for switching
     * @param gui        the GUI instance
     */
    public SwitchPairPanel(Pair switchPair, List<Pair> candidates, GUI gui) {

        this.gui = gui;

        // Create a DefaultListModel
        DefaultListModel<Pair> listModel = new DefaultListModel<>();

        for(int i = 0; i < candidates.size(); i++) {
            listModel.addElement(candidates.get(i));
        }

        JButton closeSwitchPairPanel = new JButton("Abbrechen");
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.removeSwitchPairPanel();
            }
        };
        closeSwitchPairPanel.addActionListener(listener);

        executeSwitchButton = new JButton("Tauschen");
        ActionListener executeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.addEntriesToPairReplacementTable(List.of(switchPair));
                gui.removeEntriesFromPairReplacementTable(List.of(selectedPair));
                gui.updatePairInGroupTable(switchPair, selectedPair);
                gui.switchPairInCreatedPairTable(switchPair, selectedPair);
                gui.updatePairReplacementInGroupTable();
                gui.removeSwitchPairPanel();
            }
        };
        executeSwitchButton.addActionListener(executeListener);
        executeSwitchButton.setEnabled(false);

        JList<Pair> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Pair selectedItem = list.getSelectedValue();
                if (selectedItem != null) {
                    for(int i = 0; i < candidates.size(); i++) {
                        if(candidates.get(i).getPairID().equals(selectedItem.getPairID())) {
                            selectedPair = candidates.get(i);
                        }
                    }
                    executeSwitchButtonState();
                }
            }
        });
        JLabel listHeading = new JLabel(switchPair.getPerson1().getName() + " & "+ switchPair.getPerson2().getName() + " tauschen mit");

        Font font = listHeading.getFont();
        float newSize = font.getSize() + 5; // Increase the font size by 5
        listHeading.setFont(font.deriveFont(newSize));


        list.setBackground(Color.pink);
        setLayout(new BorderLayout());
        JPanel body = new JPanel(new BorderLayout());
        JPanel footerButtons = new JPanel(new GridLayout(1, 2));
        footerButtons.add(closeSwitchPairPanel);
        footerButtons.add(executeSwitchButton);
        JScrollPane scrollPane = new JScrollPane(list);
        body.add(listHeading, BorderLayout.NORTH);
        body.add(scrollPane);
        body.add(footerButtons, BorderLayout.SOUTH);

        add(body);
    }
        // the execute SwitchButton
    public void executeSwitchButtonState() {
        this.executeSwitchButton.setEnabled(true);
    }
}
