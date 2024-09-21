package view;

import data.EHasKitchen;
import data.Pair;
import data.Person;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a panel for switching persons.
 */
public class SwitchPersonPanel extends JPanel {

    private JButton button1;
    private JButton button2;
    private Map<Integer, Person> buttonToObjectMap;
    private GUI gui;
    private JPanel personListSelectionContainer;
    private JPanel personSelectionContainer;
    private List<Person> currentCandidates;
    private Person currentSelectedCandidate;
    private Person currentPerson;
    private Person personReplace;

    /**
     * Constructs a SwitchPersonPanel with the given GUI, pair, and lists of person candidates.
     *
     * @param gui                   the GUI instance
     * @param pair                  the pair to switch persons
     * @param personOneCandidates   the list of candidates for person one
     * @param personTwoCandidates   the list of candidates for person two
     */
    public SwitchPersonPanel(GUI gui, Pair pair, List<Person> personOneCandidates, List<Person> personTwoCandidates) {

        this.gui = gui;
        setLayout(new BorderLayout());

        JLabel personSwitchHeading = new JLabel("Welche Person möchtest du tauschen");

        button1 = new JButton(pair.getPerson1().getName());
        button2 = new JButton(pair.getPerson2().getName());
        buttonToObjectMap = new HashMap<>();

        buttonToObjectMap.put(1, pair.getPerson1());
        buttonToObjectMap.put(2, pair.getPerson2());

        DefaultListModel<Person> listModel = new DefaultListModel<>();

        JList<Person> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) {
                        Person selectedItem = list.getSelectedValue();
                        if (selectedItem != null) {
                            for (int i = 0; i < currentCandidates.size(); i++) {
                                if (currentCandidates.get(i).getUUID().equals(selectedItem.getUUID())) {
                                    currentSelectedCandidate = currentCandidates.get(i);
                                }
                            }
                        }
                    }
                });

        JButton cancelPersonSwitchPanel = new JButton("Abbrechen");
        ActionListener cancelPersonSwitchPanelListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parentContainer = personListSelectionContainer.getParent();
                parentContainer.remove(personListSelectionContainer);
                parentContainer.revalidate();
                parentContainer.repaint();
                parentContainer.add(personSelectionContainer);
            }
        };
        cancelPersonSwitchPanel.addActionListener(cancelPersonSwitchPanelListener);

        JButton executePersonSwitchBtn = new JButton("Tauschen");
        ActionListener executePersonSwitchListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Pair newPair = new Pair(currentPerson, currentSelectedCandidate);

                if(newPair.getPerson1().getKitchen().getHasKitchen() == EHasKitchen.YES) {
                    newPair.setKitchen(newPair.getPerson1().getKitchen());
                }
                else if(newPair.getPerson2().getKitchen().getHasKitchen() == EHasKitchen.YES) {
                    newPair.setKitchen(newPair.getPerson2().getKitchen());
                }

                gui.updatePersonSwitch(pair, newPair, currentSelectedCandidate, personReplace);
            }
        };
        executePersonSwitchBtn.addActionListener(executePersonSwitchListener);

        JPanel switchPersonActionButtonPanel = new JPanel(new GridLayout(1,2));
        switchPersonActionButtonPanel.add(cancelPersonSwitchPanel);
        switchPersonActionButtonPanel.add(executePersonSwitchBtn);

        personListSelectionContainer = new JPanel(new BorderLayout());
        personListSelectionContainer.add(list);
        personListSelectionContainer.add(switchPersonActionButtonPanel, BorderLayout.SOUTH);

        ActionListener personSelectionClickListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                int buttonNumber = getButtonNumber(button);
                Person person = buttonToObjectMap.get(buttonNumber);

                if(buttonNumber == 1) {
                    currentCandidates = personTwoCandidates;
                    personReplace = pair.getPerson1();
                    currentPerson = pair.getPerson2();
                }
                else if(buttonNumber == 2) {
                    currentCandidates = personOneCandidates;
                    personReplace = pair.getPerson2();
                    currentPerson = pair.getPerson1();
                }

                listModel.clear();

                for(int i = 0; i < currentCandidates.size(); i++) {
                    listModel.addElement(currentCandidates.get(i));
                }

                Container parentContainer = personSelectionContainer.getParent();
                parentContainer.remove(personSelectionContainer);
                parentContainer.revalidate();
                parentContainer.repaint();
                parentContainer.add(personListSelectionContainer);
            }
        };

        button1.addActionListener(personSelectionClickListener);
        button2.addActionListener(personSelectionClickListener);

        JButton cancelPersonBtnPanel = new JButton("Abbrechen");
        ActionListener cancelPersonSelectionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.removeSwitchPersonPanel();
            }
        };
        cancelPersonBtnPanel.addActionListener(cancelPersonSelectionListener);

        JPanel personBtn = new JPanel();
        personBtn.setLayout(new BoxLayout(personBtn, BoxLayout.Y_AXIS));
        personBtn.add(button1);
        personBtn.add(button2);

        personSelectionContainer = new JPanel(new BorderLayout());
        personSelectionContainer.add(personSwitchHeading, BorderLayout.NORTH);
        personSelectionContainer.add(personBtn);
        personSelectionContainer.add(cancelPersonBtnPanel, BorderLayout.SOUTH);

        add(personSelectionContainer);
    }

    private int getButtonNumber(JButton button) {
        if (button == button1) {
            return 1;
        } else if (button == button2) {
            return 2;
        }
        return -1; // Invalid button
    }
}
