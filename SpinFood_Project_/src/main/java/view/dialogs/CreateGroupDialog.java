package view.dialogs;

import controller.Group;
import controller.MealType;
import controller.PathCalculator;
import data.Coordinate;
import data.EMeal;
import data.Pair;
import view.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
import java.util.List;

public class CreateGroupDialog {

    JPanel warningPanel = new JPanel();
    JLabel warning;
    JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

    JPanel radioButton1Panel = new JPanel(new GridLayout(1, 3));
    JPanel radioButton2Panel = new JPanel(new GridLayout(1, 3));

    MealType selectedMeal;
    Pair selectedCook;

    GUI gui;

    /**
     * Constructs a dialog for creating a group.
     *
     * @param pairs     The list of pairs available for group creation.
     * @param gui       The GUI instance.
     * @param isWarning Determines whether to display a warning message.
     */

    public CreateGroupDialog(List<Pair> pairs, GUI gui, boolean isWarning) {
        this.gui = gui;
        warning = new JLabel("Kriterien sind nicht erfüllt");
        warning.setForeground(Color.RED);
        warningPanel.add(warning);
        JDialog dialog = new JDialog();
        dialog.setTitle("Gruppe erstellen");
        dialog.setModal(true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();

        if(isWarning) {
            panel.setLayout(new GridLayout(6, 1));
        }
        else {
            panel.setLayout(new GridLayout(5, 1));
        }

        ButtonGroup radioButtonGroup1 = new ButtonGroup();
        ButtonGroup radioButtonGroup2 = new ButtonGroup();

        JRadioButton radioBtn1 = new JRadioButton(getOptionText(MealType.STARTER));
        JRadioButton radioBtn2 = new JRadioButton(getOptionText(MealType.MAIN));
        JRadioButton radioBtn3 = new JRadioButton(getOptionText(MealType.DESSERT));

        JRadioButton radioBtn4 = new JRadioButton(pairs.get(0).toString());
        JRadioButton radioBtn5 = new JRadioButton(pairs.get(1).toString());
        JRadioButton radioBtn6 = new JRadioButton(pairs.get(2).toString());

        radioButtonGroup1.add(radioBtn1);
        radioButtonGroup1.add(radioBtn2);
        radioButtonGroup1.add(radioBtn3);

        radioButtonGroup2.add(radioBtn4);
        radioButtonGroup2.add(radioBtn5);
        radioButtonGroup2.add(radioBtn6);

        radioButton1Panel.add(radioBtn1);
        radioButton1Panel.add(radioBtn2);
        radioButton1Panel.add(radioBtn3);

        radioButton2Panel.add(radioBtn4);
        radioButton2Panel.add(radioBtn5);
        radioButton2Panel.add(radioBtn6);

        if(isWarning) {
            panel.add(warningPanel);
        }

        JLabel mealHeading = new JLabel("Gang");

        mealHeading.setForeground(Color.ORANGE);
        mealHeading.setFont(new Font(mealHeading.getFont().getName(), mealHeading.getFont().getStyle(), 20));

        JLabel cookHeading = new JLabel("Koch");

        cookHeading.setForeground(Color.ORANGE);
        cookHeading.setFont(new Font(cookHeading.getFont().getName(), cookHeading.getFont().getStyle(), 20));

        panel.add(mealHeading);
        panel.add(radioButton1Panel);
        panel.add(cookHeading);
        panel.add(radioButton2Panel);

        JButton okButton = new JButton("Erstellen");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (radioBtn1.isSelected()) {
                    selectedMeal = MealType.STARTER;
                } else if (radioBtn2.isSelected()) {
                    selectedMeal = MealType.MAIN;
                } else if (radioBtn3.isSelected()) {
                    selectedMeal = MealType.DESSERT;
                }

                if (radioBtn4.isSelected()) {
                    selectedCook = pairs.get(0);
                } else if (radioBtn5.isSelected()) {
                    selectedCook = pairs.get(1);
                } else if (radioBtn6.isSelected()) {
                    selectedCook = pairs.get(2);
                }

                Group newGroup = new Group(pairs.get(0), pairs.get(1), pairs.get(2), getSelectedMeal());
                newGroup.setGroupKitchen(getSeletedCook().getKitchen());
                gui.onNewGroupCreate(newGroup);
                dialog.dispose();
            }
        });

        JButton cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);

        panel.add(buttonPanel);
        dialog.setSize(600, 300);
        dialog.add(panel);
        dialog.setVisible(true);
    }

    private String getOptionText(MealType option) {
        switch (option) {
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

    public MealType getSelectedMeal() {
        return selectedMeal;
    }

    public Pair getSeletedCook() {
        return selectedCook;
    }
}