package com.dstresdf.client.gui;

import com.dstresdf.client.console.ScriptExecutor;
import com.dstresdf.client.network.GuiHelper;
import com.dstresdf.common.commands.ArgumentType;
import com.dstresdf.common.commands.CommandType;
import com.dstresdf.common.model.HairColor;
import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MainWindow {
    private JTabbedPane tabbedPane1;
    private JPanel panel2;
    private JComboBox comboBox2;
    private JComboBox chosenCommandBox;
    private JButton executeButton;
    private JButton logoutButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton buyButton;
    private JButton clearButton;
    private JButton refreshButton;
    private JTextField textField1;
    private JButton resetButton;
    private JComboBox comboBox3;
    private JTable table1;
    private JLabel usernameField;
    private JScrollPane commandResult;
    private JTextArea commandResultTEXT;
    private JTextField commandArg;

    private GuiHelper guiHelper;
    private String login;
    private String password;
    private JFrame frame;
    public MainWindow(GuiHelper guiHelper, String login, String password) {
        this.guiHelper = guiHelper;
        this.login = login;
        this.password = password;

        show();
        initActions();
        usernameField.setText("Пользователь: " +login);
        Thread newThread = new Thread(() -> {
            while (true) {
                Response response = guiHelper.sendRequest(
                        guiHelper.buildRequest(CommandType.SHOW,login,password)
                );
                List<StudyGroup> l = response.getStudyGroups();
                fillTable(l);
                try {
                    Thread.sleep(50000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        newThread.setDaemon(true);
        newThread.start();

    }

    private void onAddButton() {
        AddStudyGroup dialog = new AddStudyGroup(guiHelper, login, password);
        dialog.pack();
        dialog.setLocationRelativeTo(panel2);
        dialog.setVisible(true);
    }

    private void onRefreshButton() {
        Response response = guiHelper.sendRequest(
                guiHelper.buildRequest(CommandType.SHOW,login,password)
        );
        List<StudyGroup> l = response.getStudyGroups();
        fillTable(l);
    }

    private void onLogoutButton() {
        SwingUtilities.invokeLater(() -> {
            new AuthWindow(guiHelper);
        });
        this.frame.dispose();
    }

    private void onBuyButton() {
        int i = table1.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Выберите элемент для покупки");
            return;
        }
        int id = (int) table1.getModel().getValueAt(i, 0);
        Response response = guiHelper.sendRequest(
                guiHelper.buildRequest(CommandType.BUY,login,password, id)
        );
        JOptionPane.showMessageDialog(frame, response.getMessage(), "", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onRemoveButton() {
        int i = table1.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Выберите элемент для удаления");
            return;
        }
        int id = (int) table1.getModel().getValueAt(i, 0);
        Response response = guiHelper.sendRequest(
                guiHelper.buildRequest(CommandType.REMOVE_KEY,login,password, id)
        );
        JOptionPane.showMessageDialog(frame, response.getMessage(), "", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onClearButton() {
        Response response = guiHelper.sendRequest(
                guiHelper.buildRequest(CommandType.CLEAR,login,password)
        );
        JOptionPane.showMessageDialog(frame, response.getMessage(), "", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onExecuteButton() {
        new Thread(() -> {
            commandResult.setVisible(true);
            CommandType command = CommandType.getCommand(chosenCommandBox.getSelectedItem().toString());
            Response response = null;
            if (command.equals(CommandType.EXECUTE_SCRIPT)) {
                ScriptExecutor scriptExecutor = ScriptExecutor.getScriptExecutor();
                scriptExecutor.executeScript(commandArg.getText());
            }

            if (command.getArg() == ArgumentType.NO_ARGS) {
                response = guiHelper.sendRequest(
                        guiHelper.buildRequest(command, login, password)
                );
            } else {
                response = guiHelper.sendRequest(
                        guiHelper.buildRequest(command, login, password, commandArg == null ? null : Integer.parseInt(commandArg.getText())));
            }

            commandResultTEXT.setText(response.getMessage());
            frame.pack();
            frame.revalidate();
            frame.repaint();
        }).start();
    }
    private void show(){
        JFrame frame = new JFrame("Main Window");
        frame.setContentPane(panel2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.frame = frame;
    }
    private void initActions() {
        addButton.addActionListener(e -> onAddButton());
        refreshButton.addActionListener(e -> onRefreshButton());
        logoutButton.addActionListener(e -> onLogoutButton());
        buyButton.addActionListener(e -> onBuyButton());
        clearButton.addActionListener(e -> onClearButton());
        removeButton.addActionListener(e -> onRemoveButton());
        executeButton.addActionListener(e -> onExecuteButton());
    }



    private void fillTable(List<StudyGroup> groups) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Имя");
        model.addColumn("X");
        model.addColumn("Y");
        model.addColumn("Дата создания");
        model.addColumn("Студентов");
        model.addColumn("Отчислено");
        model.addColumn("Должно быть отчислено");
        model.addColumn("Форма обучения");
        model.addColumn("Староста");
        model.addColumn("Владелец");
        model.addColumn("Цена");

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
    }
}
