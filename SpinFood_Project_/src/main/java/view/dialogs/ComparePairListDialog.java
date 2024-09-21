package view.dialogs;

import controller.PairMetrics;
import controller.json.JsonReader;
import data.Pair;
import data.Person;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ComparePairListDialog extends JDialog {

    JPanel uploadFilePanel = new JPanel(new GridLayout(2, 2));
    JPanel leftUploadFilePanel = new JPanel(new GridLayout(2, 1));
    JPanel rightUploadFilePanel = new JPanel(new GridLayout(2, 1));
    String firstFileContent = "";
    String secondFileContent = "";
    JLabel firstSelectedFileName;
    JLabel secondSelectedFileName;
    JButton continueButton;
    JPanel pairTableContainer;
    JPanel firstTableContainer = new JPanel(new BorderLayout());
    JTable firstPairsTable;
    JPanel secondTableContainer = new JPanel(new BorderLayout());
    JTable secondPairsTable;

    /**
     * The ComparePairListDialog class represents a dialog for comparing pair lists.
     * It extends the JDialog class and provides functionality for uploading two files,
     * displaying file names, and creating tables for comparing pair data.
     */

    public ComparePairListDialog() {

        setTitle("Pärchenliste vergleichen");
        setModal(true);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JButton uploadFileButton1 = new JButton("1. Datei");
        JButton uploadFileButton2 = new JButton("2. Datei");

        pairTableContainer = new JPanel(new GridLayout(1,2));

        firstSelectedFileName = new JLabel("Datei: ");
        secondSelectedFileName = new JLabel("Datei: ");


        uploadFileButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstFileContent = onFileUpload(true);
                if(checkIfTwoFilesSelected()) {
                    continueButton.setEnabled(true);
                }
            }
        });

        leftUploadFilePanel.add(uploadFileButton1);
        leftUploadFilePanel.add(firstSelectedFileName);

        uploadFileButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                secondFileContent = onFileUpload(false);
                if(checkIfTwoFilesSelected()) {
                    continueButton.setEnabled(true);
                }
            }
        });

        rightUploadFilePanel.add(uploadFileButton2);
        rightUploadFilePanel.add(secondSelectedFileName);

        continueButton = new JButton("Vergleichen");
        continueButton.setEnabled(false);
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = uploadFilePanel.getParent();
                parent.remove(uploadFilePanel);
                revalidate();
                repaint();

                createTables(JsonReader.readPairs(firstFileContent, "pairs"), JsonReader.readSuccessorParticipants(firstFileContent),
                        JsonReader.readPairs(secondFileContent, "pairs"), JsonReader.readSuccessorParticipants(secondFileContent));
            }
        });

        JPanel placeholderPanel = new JPanel();
        placeholderPanel.setLayout(new BorderLayout());
        placeholderPanel.add(Box.createHorizontalGlue(), BorderLayout.CENTER);

        uploadFilePanel.add(leftUploadFilePanel);
        uploadFilePanel.add(rightUploadFilePanel);
        uploadFilePanel.add(placeholderPanel);
        uploadFilePanel.add(continueButton);
        add(uploadFilePanel);

        setVisible(true);
    }

    public String onFileUpload(boolean isFirstFile) {
        String content = "";
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Datei auswählen");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Dateien", "json"));

        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {

                if(isFirstFile) {
                    firstSelectedFileName.setText("Datei: " + selectedFile.getName());
                }
                else {
                    secondSelectedFileName.setText("Datei: " + selectedFile.getName());
                }

                content = Files.readString(selectedFile.toPath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        frame.dispose();
        return content;
    }

    public boolean checkIfTwoFilesSelected() {

        boolean isSelected = false;

        if(!Objects.equals(firstFileContent, "") && !Objects.equals(secondFileContent, "")) {
            isSelected = true;
        }

        return isSelected;
    }

    /**
     * Creates tables for displaying pair data and metrics.
     * Two pairs of lists and their corresponding successor lists are provided as input.
     * The method creates two JTables with scroll panes, adds them to the table containers,
     * sets the table models with the pair data and column names, and adds metrics labels.
     * The table cells are set as non-editable.
     *
     * @param pairsOne     The first list of pairs.
     * @param successorOne The successor list corresponding to pairsOne.
     * @param pairsTwo     The second list of pairs.
     * @param successorTwo The successor list corresponding to pairsTwo.
     */
    public void createTables(List<Pair> pairsOne, List<Person> successorOne, List<Pair> pairsTwo, List<Person> successorTwo) {

        DefaultTableModel modelOne = new DefaultTableModel(getTableData(pairsOne), getTableColumnNames()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Setze alle Zellen als nicht editierbar
            }
        };

        JScrollPane pairTableScrollOne = new JScrollPane();
        firstPairsTable = new JTable(modelOne);
        pairTableScrollOne.setViewportView(firstPairsTable);
        firstTableContainer.add(pairTableScrollOne, BorderLayout.CENTER);
        JLabel metricsOne = new JLabel(getMetrics(pairsOne, successorOne));
        metricsOne.setToolTipText(metricsOne.getText());
        firstTableContainer.add(metricsOne, BorderLayout.SOUTH);

        DefaultTableModel modelTwo = new DefaultTableModel(getTableData(pairsTwo), getTableColumnNames()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Setze alle Zellen als nicht editierbar
            }
        };

        JScrollPane pairTableScrollTwo = new JScrollPane();
        secondPairsTable = new JTable(modelTwo);
        pairTableScrollTwo.setViewportView(secondPairsTable);
        secondTableContainer.add(pairTableScrollTwo, BorderLayout.CENTER);
        JLabel metricsTwo = new JLabel(getMetrics(pairsTwo, successorTwo));
        metricsTwo.setToolTipText(metricsTwo.getText());
        secondTableContainer.add(metricsTwo, BorderLayout.SOUTH);


        pairTableContainer.add(firstTableContainer);
        pairTableContainer.add(secondTableContainer);
        add(pairTableContainer);
        setSize(600, 600);
    }

    private Object[][] getTableData(List<Pair> pairs) {

        Object[][] createdPairData = {};

        for (int i = 0; i < pairs.size(); i++) {

            String foodPreference = "";

            switch (pairs.get(i).getPerson2().getFoodPreference()) {
                case NONE -> foodPreference = "Keine";
                case MEAT -> foodPreference = "Fleisch";
                case VEGAN -> foodPreference = "Vegan";
                case VEGGIE -> foodPreference = "Vegetarisch";
            }

            Object[] newEntry = {pairs.get(i).getPerson1().getName(), pairs.get(i).getPerson2().getName(), foodPreference};
            createdPairData = Arrays.copyOf(createdPairData, createdPairData.length + 1);
            createdPairData[createdPairData.length - 1] = newEntry;
        }

        return createdPairData;
    }

    private String[] getTableColumnNames() {
        return new String[]{"1. Person", "2. Person", "Vorliebe"};
    }

    public String getMetrics(List<Pair> pairs, List<Person> persons) {

        PairMetrics pairMetrics = new PairMetrics(pairs, persons);

        return "Paare: " + pairMetrics.getCreatedPairsCount() + ", Nachrückende Personen: " + pairMetrics.getReplacementPairCount() +
                ", Geschlechterdiversität: " + pairMetrics.getAvgGenderDiversity() +  ", Altersdifferenz: " + pairMetrics.getAvgAgeDifference() + ", Vorliebenabweichung: " +
                pairMetrics.getAvgFoodPreferenceDeviation();
    }
}
