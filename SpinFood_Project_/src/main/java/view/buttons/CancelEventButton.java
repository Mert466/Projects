package view.buttons;

import view.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * A  JButton that represents a cancel event button.
 */

public class CancelEventButton extends JButton {

    private GUI gui;
    /**
     * Constructs a new CancelEventButton with the specified GUI.
     *
     * @param gui the GUI associated with the button
     */

    public CancelEventButton(GUI gui) {

        this.gui = gui;

        setText("Event beenden");

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.cancelEvent();
            }
        });
    }
}
