package view.dialogs;

import controller.Group;
import controller.GroupMetrics;
import controller.json.JsonReader;
import data.Coordinate;
import data.Pair;

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

/**
 * The CompareGroupListDialog class represents a dialog for comparing group lists.
 * It extends the JDialog class and provides functionality for uploading two files,
 * displaying file names, and creating tables for comparing group data.
 */
public class CompareGroupListDialog extends JDialog {

    JPanel uploadFilePanel = new JPanel(new GridLayout(2, 2));
    JPanel leftUploadFilePanel = new JPanel(new GridLayout(2, 1));
    JPanel rightUploadFilePanel = new JPanel(new GridLayout(2, 1));
    String firstFileContent = "";
    String secondFileContent = "";
    JLabel firstSelectedFileName;
    JLabel secondSelectedFileName;
    JButton continueButton;
    JPanel groupTableContainer;
    JPanel firstTableContainer = new JPanel(new BorderLayout());
    JTable firstGroupsTable;
    JPanel secondTableContainer = new JPanel(new BorderLayout());
    JTable secondGroupsTable;

    public CompareGroupListDialog(Coordinate afterParty) {

        setTitle("Gruppenlisten vergleichen");
        setModal(true);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JButton uploadFileButton1 = new JButton("1. Datei");
        JButton uploadFileButton2 = new JButton("2. Datei");

        groupTableContainer = new JPanel(new GridLayout(1,2));

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
                createTables(JsonReader.readGroups(firstFileContent, afterParty), JsonReader.readSuccessorPairs(firstFileContent),
                        JsonReader.readGroups(secondFileContent, afterParty), JsonReader.readSuccessorPairs(secondFileContent));
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
     * Creates tables for comparing group data based on the provided lists of groups and successors.
     * Two tables are created, each representing a different group list.
     * The tables are populated with data from the groups, and metrics labels are added below each table.
     *
     * @param groupOne    the first list of groups
     * @param successorOne    the list of successors corresponding to the first group list
     * @param groupTwo    the second list of groups
     * @param successorTwo    the list of successors corresponding to the second group list
     */

    public void createTables(List<Group> groupOne, List<Pair> successorOne, List<Group> groupTwo, List<Pair> successorTwo) {

        DefaultTableModel modelOne = new DefaultTableModel(getTableData(groupOne), getTableColumnNames()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Setze alle Zellen als nicht editierbar
            }
        };

        JScrollPane groupTableScrollOne = new JScrollPane();
        firstGroupsTable = new JTable(modelOne);
        groupTableScrollOne.setViewportView(firstGroupsTable);
        firstTableContainer.add(groupTableScrollOne, BorderLayout.CENTER);
        JLabel metricsOne = new JLabel(getMetrics(groupOne, successorOne));
        metricsOne.setToolTipText(metricsOne.getText());
        firstTableContainer.add(metricsOne, BorderLayout.SOUTH);

        DefaultTableModel modelTwo = new DefaultTableModel(getTableData(groupTwo), getTableColumnNames()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Setze alle Zellen als nicht editierbar
            }
        };

        JScrollPane groupTableScrollTwo = new JScrollPane();
        secondGroupsTable = new JTable(modelTwo);
        groupTableScrollTwo.setViewportView(secondGroupsTable);
        secondTableContainer.add(groupTableScrollTwo, BorderLayout.CENTER);
        JLabel metricsTwo = new JLabel(getMetrics(groupTwo, successorTwo));
        metricsTwo.setToolTipText(metricsTwo.getText());
        secondTableContainer.add(metricsTwo, BorderLayout.SOUTH);


        groupTableContainer.add(firstTableContainer);
        groupTableContainer.add(secondTableContainer);
        add(groupTableContainer);
        setSize(600, 600);
    }

    private Object[][] getTableData(List<Group> groups) {

        Object[][] createdGroupData = {};

        for (int i = 0; i < groups.size(); i++) {

            Object[] newEntry = {groups.get(i).getPairs().get(0).toString(), groups.get(i).getPairs().get(1).toString(), groups.get(i).getPairs().get(2).toString()};
            createdGroupData = Arrays.copyOf(createdGroupData, createdGroupData.length + 1);
            createdGroupData[createdGroupData.length - 1] = newEntry;
        }

        return createdGroupData;
    }

    private String[] getTableColumnNames() {
        return new String[]{"1. Paar", "2. Paar", "3. Paar"};
    }

    public String getMetrics(List<Group> groups, List<Pair> successorPairs) {

        GroupMetrics groupMetrics = new GroupMetrics(groups, successorPairs);

        return "Gruppen: " + groupMetrics.getCreatedGroupsCount() + ", Nachrückende Pärchen: " + groupMetrics.getReplacementGroupCount() +
                ", Geschlechterdiversität: " + groupMetrics.getAvgGenderDiversity() +  ", Altersdifferenz: " + groupMetrics.getAvgAgeDifference() + ", Vorliebenabweichung: " +
                groupMetrics.getAvgFoodPreferenceDeviation() + ", Weglänge: " + groupMetrics.getPathLength();
    }

}
