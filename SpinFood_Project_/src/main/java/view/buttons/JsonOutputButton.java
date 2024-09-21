package view.buttons;

import view.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a button for JSON output in the GUI.
 * The button triggers a JSON output dialog when clicked.
 */
public class JsonOutputButton extends JButton {

    private GUI gui;
    /**
     * Constructs a new JsonOutputButton with the specified GUI instance.
     *
     * @param gui the GUI instance associated with the button
     */
    public JsonOutputButton(GUI gui) {

        this.gui = gui;

        JLabel label = new JLabel("JSON Ausgabe");

        ActionListener JSONlistener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.showJsonDialog();
            }
        };

        addActionListener(JSONlistener);


        add(label);
    }
}
