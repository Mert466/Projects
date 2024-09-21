package view;

import data.Coordinate;
import view.buttons.CompareGroupButton;
import view.buttons.ComparePairButton;
import view.buttons.LanguageButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Represents a panel for creating an event in the GUI.
 * The panel allows the user to input various factors and settings for event creation, including criteria values and file selection.
 * It also provides a button for creating the event based on the entered data.
 */
public class CreateEventPanel extends JPanel {

    JPanel welcomePanel;
    String currentSelectedFile;
    GUI gui;
    JLabel selectedFileName;
    JLabel welcomeLabel;
    JLabel criteriaLabel;
    JLabel criteriaSubheading;
    JLabel foodPreferenceLabel;
    JLabel ageDifferenceLabel;
    JLabel genderDiversityLabel;
    JLabel infoLabel;
    JLabel maximumNumberPeopleLabel;
    CreateEventButton getFileButton;
    JLabel noFileSelectedLabel;
    String createEventText;
    JButton createButton;
    LanguageButtons languageButtons;

    /**
     * Constructs a new CreateEventPanel with the specified GUI instance.
     *
     * @param gui the GUI instance associated with the panel
     */
    public CreateEventPanel(GUI gui, LanguageButtons languageButtons, Coordinate afterParty) {
        this.languageButtons = languageButtons;
        this.gui = gui;

        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        JPanel bottomPanel = new JPanel(new GridLayout(14, 1));

        welcomeLabel = new JLabel(gui.getTranslatedString("welcome"));
        Font font = new Font("Arial", Font.BOLD, 30);
        welcomeLabel.setFont(font);
        welcomeLabel.setForeground(Color.ORANGE);
        welcomePanel = new JPanel();
        welcomePanel.add(welcomeLabel);

        JPanel compareListPanel = new JPanel(new GridLayout(1, 2));
        compareListPanel.add(new ComparePairButton());
        compareListPanel.add(new CompareGroupButton(afterParty));
        topPanel.add(compareListPanel);
        topPanel.add(languageButtons);
        topPanel.add(welcomePanel);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        criteriaLabel = new JLabel(gui.getTranslatedString("criteria"));
        Font criteriaFont = new Font("Arial", Font.BOLD, 20);
        criteriaLabel.setFont(criteriaFont);
        bottomPanel.add(criteriaLabel);

        criteriaSubheading = new JLabel(gui.getTranslatedString("factor_input"));
        bottomPanel.add(criteriaSubheading);

        foodPreferenceLabel = new JLabel(gui.getTranslatedString("food_preference"));
        bottomPanel.add(foodPreferenceLabel);
        NumberTextField inputField1 = new NumberTextField(15);
        inputField1.setEditable(true);
        bottomPanel.add(inputField1);

        ageDifferenceLabel = new JLabel(gui.getTranslatedString("age_difference"));
        bottomPanel.add(ageDifferenceLabel);
        NumberTextField inputField2 = new NumberTextField(15);
        bottomPanel.add(inputField2);

        genderDiversityLabel = new JLabel(gui.getTranslatedString("gender_diversity"));
        bottomPanel.add(genderDiversityLabel);
        NumberTextField inputField3 = new NumberTextField(15);
        bottomPanel.add(inputField3);

        infoLabel = new JLabel(gui.getTranslatedString("more_information"));
        Font infoLabelFont = new Font("Arial", Font.BOLD, 20);
        infoLabel.setFont(infoLabelFont);
        bottomPanel.add(infoLabel);

        maximumNumberPeopleLabel = new JLabel(gui.getTranslatedString("max_number_persons"));
        bottomPanel.add(maximumNumberPeopleLabel);
        NumberTextField inputField4 = new NumberTextField(Integer.MAX_VALUE);
        bottomPanel.add(inputField4);

        getFileButton = new CreateEventButton(this);
        bottomPanel.add(getFileButton);
        noFileSelectedLabel = new JLabel("Datei:");
        selectedFileName = noFileSelectedLabel;
        bottomPanel.add(selectedFileName);
        createEventText = gui.getTranslatedString("create_event");
        createButton = new JButton();
        createButton.setText(createEventText);
        bottomPanel.add(createButton);
        createButton.setBackground(Color.ORANGE);
        createButton.setOpaque(true);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text1 = inputField1.getText();
                String text2 = inputField2.getText();
                String text3 = inputField3.getText();
                String text4 = inputField4.getText();

                int value1 = text1.isEmpty() ? 1 : (text1.equals("0") ? 1 : Integer.parseInt(text1));
                int value2 = text2.isEmpty() ? 1 : (text2.equals("0") ? 1 : Integer.parseInt(text2));
                int value3 = text3.isEmpty() ? 1 : (text3.equals("0") ? 1 : Integer.parseInt(text3));
                int value4 = text4.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(text4);

                CreateEventData data = new CreateEventData(value1, value2, value3, value4, currentSelectedFile);

                if(currentSelectedFile != null && !text4.equals("0")) {
                    gui.createEvent(data);
                }
            }
        });
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.CENTER);
        add(panel);
        Dimension currentSize = panel.getPreferredSize();
        int newWidth = currentSize.width + 200;
        panel.setPreferredSize(new Dimension(newWidth, currentSize.height));
        panel.revalidate();
    }

    public void setCurrentSelectedFile(String selectedFile) {
        this.selectedFileName.setText("Datei: " + new File(selectedFile).getName());
        this.currentSelectedFile = selectedFile;
    }

    public void updateTexts() {
        this.welcomeLabel.setText(gui.getTranslatedString("welcome"));
        this.criteriaLabel.setText(gui.getTranslatedString("criteria"));
        this.criteriaSubheading.setText(gui.getTranslatedString("factor_input"));
        this.foodPreferenceLabel.setText(gui.getTranslatedString("food_preference"));
        this.ageDifferenceLabel.setText(gui.getTranslatedString("age_difference"));
        this.genderDiversityLabel.setText(gui.getTranslatedString("gender_diversity"));
        this.infoLabel.setText(gui.getTranslatedString("more_information"));
        this.maximumNumberPeopleLabel.setText(gui.getTranslatedString("max_number_persons"));
        this.getFileButton.updateTexts();
        this.createEventText = gui.getTranslatedString("create_event");
        this.createButton.setText(createEventText);
    }
}
