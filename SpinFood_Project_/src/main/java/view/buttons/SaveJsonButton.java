package view.buttons;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveJsonButton extends JButton {

    public SaveJsonButton(String json) {

        setText("JSON speichern");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("JSON speichern");

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Filter für JSON-Dateien hinzufügen
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Files", "json");
                fileChooser.setFileFilter(filter);

                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    // Datei auswählen oder erstellen
                    String filePath = fileChooser.getSelectedFile().getPath();

                    // Überprüfen, ob die Datei die richtige Erweiterung hat
                    if (!filePath.toLowerCase().endsWith(".json")) {
                        filePath += ".json";
                    }

                    try {
                        // JSON-String in die Datei schreiben
                        FileWriter writer = new FileWriter(filePath);
                        writer.write(json);
                        writer.close();

                        JOptionPane.showMessageDialog(null, "JSON Datei erfolgreich gespeichert.");
                    } catch (IOException er) {
                        JOptionPane.showMessageDialog(null, "Error saving JSON file: " + er.getMessage());
                    }
                }
            }
        });
    }
}
