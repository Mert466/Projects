package view.buttons;

import view.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Represents a panel containing language selection buttons in the GUI.
 * The panel allows the user to switch between different languages by clicking on the corresponding buttons.
 */
public class LanguageButtons extends JPanel {

    private GUI gui;
    ResourceBundle bundle;

    /**
     * Constructs a new LanguageButtons panel with the specified GUI instance.
     *
     * @param gui the GUI instance associated with the panel
     */
    public LanguageButtons(GUI gui) {
        this.gui = gui;
        Locale.setDefault(Locale.ENGLISH);
        bundle = ResourceBundle.getBundle("trans");


        JButton button1 = new JButton("DE");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                gui.changeLanguage(Locale.GERMAN);
            }
        });


        JButton button2 = new JButton("EN");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                gui.changeLanguage(Locale.ENGLISH);
            }
        });

        add(button1);
        add(button2);
    }
}