package view.buttons;

import data.Coordinate;
import view.dialogs.CompareGroupListDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompareGroupButton extends JButton {

    /**
     * Constructs a new CompareGroupButton with the specified afterParty coordinate.
     * The button text is set to "Gruppenlisten vergleichen".
     * An ActionListener is added to handle the button click event and open a CompareGroupListDialog
     * with the provided afterParty coordinate.
     *
     * @param afterParty the coordinate of the after party
     */
    public CompareGroupButton(Coordinate afterParty) {

        setText("Gruppenlisten vergleichen");

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CompareGroupListDialog(afterParty);
            }
        });
    }
}