package view;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Represents a button for creating an event and selecting a CSV file.
 */
public class CreateEventButton extends JButton {

    CreateEventPanel panel;
    public String currentSeletedFilePath;
    String buttonText;

    /**
     * Creates a button for event creation and associates it with an action event.
     *
     * @param panel The CreateEventPanel object to which the selected file will be passed.
     */
    public CreateEventButton(CreateEventPanel panel) {
        this.panel = panel;
        buttonText = panel.gui.getTranslatedString("upload_persons");
        setText(buttonText);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false); // Deaktiviert die Option "Alle Dateien"
                fileChooser.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return file.isDirectory() || file.getName().toLowerCase().endsWith(".csv");
                    }

                    @Override
                    public String getDescription() {
                        return "CSV Files (*.csv)";
                    }
                });
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    panel.setCurrentSelectedFile(selectedFile.getAbsolutePath());
                }
            }
        });

    }

    public void updateTexts() {
        buttonText = this.panel.gui.getTranslatedString("upload_persons");
        setText(buttonText);
    }
}