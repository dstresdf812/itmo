package com.dstresdf.client.gui;

import com.dstresdf.client.console.ScriptExecutor;
import com.dstresdf.client.network.GuiHelper;
import com.dstresdf.common.commands.ArgumentType;
import com.dstresdf.common.commands.CommandType;
import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Response;
import com.dstresdf.common.util.StudyGroupCriteria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainWindow {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JComboBox languageBox;
    private JComboBox chosenCommandBox;
    private JButton executeButton;
    private JButton logoutButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton buyButton;
    private JButton clearButton;
    private JButton refreshButton;
    private JButton editButton;
    private JTextField filterValueField;
    private JButton resetButton;
    private JComboBox filterColumnBox;
    private JTable table1;
    private JLabel usernameField;
    private JScrollPane commandResult;
    private JTextArea commandResultTEXT;
    private JTextField commandArg;
    private JComboBox operatorBox;
    private JComboBox sortColumnBox;
    private JComboBox sortTypeBox;
    private JButton applyFilterButton;
    private JPanel visualizationContainer;
    private JLabel filterLabel;
    private JLabel sortLabel;
    private JLabel commandLabel;

    private GuiHelper guiHelper;
    private String login;
    private String password;
    private JFrame frame;
    private List<StudyGroup> currentGroups = new ArrayList<>();
    private final ExecutorService networkExecutor = Executors.newSingleThreadExecutor();
    private volatile boolean autoRefreshEnabled = true;
    private Thread autoRefreshThread;

    private VisualizationPanel visualizationPanel;
    private LocalizationManager localizationManager;
    public MainWindow(GuiHelper guiHelper, String login, String password, LocalizationManager localizationManager) {
        this.guiHelper = guiHelper;
        this.login = login;
        this.password = password;
        this.localizationManager = localizationManager;
        show();
        initActions();
        this.visualizationPanel = new VisualizationPanel(localizationManager);
        this.visualizationPanel.setOnEditGroup(this::openEditDialog);
        visualizationContainer.setLayout(new BorderLayout());
        visualizationContainer.add(visualizationPanel, BorderLayout.CENTER);

        applyLocalization();
        autoRefreshThread = new Thread(() -> {
            while (autoRefreshEnabled) {
                Response response = guiHelper.sendRequest(
                        guiHelper.buildRequest(CommandType.SHOW,login,password)
                );
                List<StudyGroup> l = response.getStudyGroups();
                SwingUtilities.invokeLater(() -> fillTable(l));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        autoRefreshThread.setDaemon(true);
        autoRefreshThread.start();

    }

    private void onAddButton() {
        AddStudyGroup dialog = new AddStudyGroup(guiHelper, login, password, localizationManager);
        dialog.pack();
        dialog.setLocationRelativeTo(panel1);
        dialog.setVisible(true);
    }

    private void onEditButton() {
        int row = table1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, localizationManager.get("message.chooseEdit"));
            return;
        }

        int modelRow = table1.convertRowIndexToModel(row);
        int id = (int) table1.getModel().getValueAt(modelRow, 0);
        StudyGroup selected = null;
        for (StudyGroup group : currentGroups) {
            if (group.getId() == id) {
                selected = group;
                break;
            }
        }

        openEditDialog(selected);
    }

    private void openEditDialog(StudyGroup studyGroup) {
        if (studyGroup == null) {
            return;
        }
        AddStudyGroup dialog = new AddStudyGroup(guiHelper, login, password, localizationManager, studyGroup.getId(), studyGroup);
        dialog.pack();
        dialog.setLocationRelativeTo(panel1);
        dialog.setVisible(true);
        onRefreshButton();
    }

    private void onRefreshButton() {
        refreshTableAsync();
    }

    private void onLogoutButton() {
        shutdownBackgroundTasks();
        new AuthWindow(guiHelper, localizationManager);
        this.frame.dispose();
    }

    private void onBuyButton() {
        Integer id = getSelectedId(localizationManager.get("message.chooseBuy"));
        if (id == null) {
            return;
        }
        networkExecutor.submit(() -> {
            Response response = guiHelper.sendRequest(
                    guiHelper.buildRequest(CommandType.BUY,login,password, id)
            );
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, response.getMessage(), "", JOptionPane.INFORMATION_MESSAGE));
        });
    }

    private void onRemoveButton() {
        Integer id = getSelectedId(localizationManager.get("message.chooseDelete"));
        if (id == null) {
            return;
        }
        networkExecutor.submit(() -> {
            Response response = guiHelper.sendRequest(
                    guiHelper.buildRequest(CommandType.REMOVE_KEY,login,password, id)
            );
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(frame, response.getMessage(), "", JOptionPane.INFORMATION_MESSAGE);
                refreshTableAsync();
            });
        });
    }

    private Integer getSelectedId(String message) {
        int i = table1.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, message);
            return null;
        }
        int modelRow = table1.convertRowIndexToModel(i);
        return (int) table1.getModel().getValueAt(modelRow, 0);
    }

    private void onClearButton() {
        networkExecutor.submit(() -> {
            Response response = guiHelper.sendRequest(
                    guiHelper.buildRequest(CommandType.CLEAR,login,password)
            );
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(frame, response.getMessage(), "", JOptionPane.INFORMATION_MESSAGE);
                refreshTableAsync();
            });
        });
    }

    private void onExecuteButton() {
        commandResult.setVisible(true);
        CommandType command = CommandType.getCommand(chosenCommandBox.getSelectedItem().toString());
        String argText = commandArg.getText();

        if (command.getArg() == ArgumentType.ONE_ARG && (argText == null || argText.isEmpty())) {
            JOptionPane.showMessageDialog(frame, localizationManager.get("message.commandArgRequired"), "", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Integer arg = null;
        if (command.getArg() == ArgumentType.ONE_ARG && !command.equals(CommandType.EXECUTE_SCRIPT)) {
            try {
                arg = Integer.parseInt(argText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, localizationManager.get("message.commandArgNumber"), "", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Integer finalArg = arg;
        networkExecutor.submit(() -> {
            Response response;
            if (command.equals(CommandType.EXECUTE_SCRIPT)) {
                ScriptExecutor scriptExecutor = ScriptExecutor.getScriptExecutor();
                scriptExecutor.executeScript(argText);
                response = new Response(true, localizationManager.get("message.scriptDone"), null);
            } else if (command.getArg() == ArgumentType.NO_ARGS) {
                response = guiHelper.sendRequest(
                        guiHelper.buildRequest(command, login, password)
                );
            } else {
                response = guiHelper.sendRequest(
                        guiHelper.buildRequest(command, login, password, finalArg));
            }

            Response finalResponse = response;
            SwingUtilities.invokeLater(() -> {
                commandResultTEXT.setText(finalResponse.getMessage());
                frame.pack();
                frame.revalidate();
                frame.repaint();
            });
        });
    }

    private void onApplyFilterButton() {
        String field = filterColumnBox.getSelectedItem().toString();
        String operator = operatorBox.getSelectedItem().toString();
        String value = filterValueField.getText();
        String sortField = sortColumnBox.getSelectedItem().toString();
        String sortOrder = sortTypeBox.getSelectedItem().toString();
        StudyGroupCriteria studyGroupCriteria = new StudyGroupCriteria(field,  operator, value, sortField, sortOrder);

        networkExecutor.submit(() -> {
            Response response = guiHelper.sendRequest(
                guiHelper.buildRequest(CommandType.SHOW,login,password, studyGroupCriteria)
            );
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(frame, response.getMessage(), "", JOptionPane.INFORMATION_MESSAGE);
                fillTable(response.getStudyGroups());
            });
        });
    }
    private void refreshTableAsync() {
        networkExecutor.submit(() -> {
            Response response = guiHelper.sendRequest(
                    guiHelper.buildRequest(CommandType.SHOW,login,password)
            );
            List<StudyGroup> l = response.getStudyGroups();
            SwingUtilities.invokeLater(() -> fillTable(l));
        });
    }

    private void shutdownBackgroundTasks() {
        autoRefreshEnabled = false;
        if (autoRefreshThread != null) {
            autoRefreshThread.interrupt();
        }
        networkExecutor.shutdownNow();
    }

    private void show(){
        JFrame frame = new JFrame("Main Window");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                shutdownBackgroundTasks();
            }
        });
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.frame = frame;
    }

    private void initActions() {
        addButton.addActionListener(e -> onAddButton());
        editButton.addActionListener(e -> onEditButton());
        refreshButton.addActionListener(e -> onRefreshButton());
        logoutButton.addActionListener(e -> onLogoutButton());
        buyButton.addActionListener(e -> onBuyButton());
        clearButton.addActionListener(e -> onClearButton());
        removeButton.addActionListener(e -> onRemoveButton());
        executeButton.addActionListener(e -> onExecuteButton());
        applyFilterButton.addActionListener(e -> onApplyFilterButton());
        resetButton.addActionListener(e -> {
            filterColumnBox.setSelectedIndex(0);
            filterValueField.setText("");
            sortColumnBox.setSelectedIndex(0);
            sortTypeBox.setSelectedIndex(0);
            refreshTableAsync();
        });

        languageBox.addActionListener(e -> {
            String selectedLanguage = languageBox.getSelectedItem().toString();
            localizationManager.setLocale(selectedLanguage);
            applyLocalization();
        });
    }

    private void fillTable(List<StudyGroup> groups) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> fillTable(groups));
            return;
        }
        if (groups == null) {
            return;
        }
        currentGroups = new ArrayList<>(groups);
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn(localizationManager.get("table.id"));
        model.addColumn(localizationManager.get("table.name"));
        model.addColumn(localizationManager.get("table.x"));
        model.addColumn(localizationManager.get("table.y"));
        model.addColumn(localizationManager.get("table.creationDate"));
        model.addColumn(localizationManager.get("table.studentsCount"));
        model.addColumn(localizationManager.get("table.expelledStudents"));
        model.addColumn(localizationManager.get("table.shouldBeExpelled"));
        model.addColumn(localizationManager.get("table.formOfEducation"));
        model.addColumn(localizationManager.get("table.groupAdmin"));
        model.addColumn(localizationManager.get("table.owner"));
        model.addColumn(localizationManager.get("table.price"));

        for (StudyGroup group : groups) {
            model.addRow(new Object[]{
                    group.getId(),
                    group.getName(),
                    group.getCoordinates() == null ? null : group.getCoordinates().getX(),
                    group.getCoordinates() == null ? null : group.getCoordinates().getY(),
                    group.getCreationDate(),
                    group.getStudentsCount(),
                    group.getExpelledStudents(),
                    group.getShouldBeExpelled(),
                    group.getFormOfEducation(),
                    group.getGroupAdmin() == null ? null : group.getGroupAdmin().getName(),
                    group.getOwnerLogin(),
                    group.getPrice()
            });
        }

        table1.setModel(model);
        visualizationPanel.setGroups(groups);
    }

    private void applyLocalization() {
        frame.setTitle(localizationManager.get("window.main"));
        tabbedPane1.setTitleAt(0, localizationManager.get("tab.main"));
        tabbedPane1.setTitleAt(1, localizationManager.get("tab.visualization"));

        usernameField.setText(localizationManager.get("label.user") + " " + login);

        addButton.setText(localizationManager.get("button.add"));
        editButton.setText(localizationManager.get("button.edit"));
        removeButton.setText(localizationManager.get("button.remove"));
        buyButton.setText(localizationManager.get("button.buy"));
        clearButton.setText(localizationManager.get("button.clear"));
        refreshButton.setText(localizationManager.get("button.refresh"));
        logoutButton.setText(localizationManager.get("button.logout"));
        applyFilterButton.setText(localizationManager.get("button.apply"));
        resetButton.setText(localizationManager.get("button.reset"));
        executeButton.setText(localizationManager.get("button.execute"));
        filterLabel.setText(localizationManager.get("label.filter"));
        sortLabel.setText(localizationManager.get("label.sort"));
        commandLabel.setText(localizationManager.get("label.command"));

        fillTable(currentGroups);
    }
}
