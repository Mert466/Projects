package view.buttons;

import view.dialogs.ComparePairListDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComparePairButton extends JButton {

    /**
     * Constructs a new ComparePairButton with default settings.
     * The button text is set to "Pärchenlisten vergleichen".
     * An ActionListener is added to handle the button click event and open a ComparePairListDialog.
     */
    public ComparePairButton() {

        setText("Pärchenlisten vergleichen");

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ComparePairListDialog();
            }
        });
    }
}