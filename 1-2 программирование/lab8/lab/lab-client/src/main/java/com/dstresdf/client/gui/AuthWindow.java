package com.dstresdf.client.gui;

import com.dstresdf.client.network.GuiHelper;
import com.dstresdf.common.commands.CommandType;
import com.dstresdf.common.network.Response;

import javax.swing.*;

public class AuthWindow {
    private GuiHelper guiHelper;
    private JButton loginButton;
    private JPasswordField passwordPasswordField;
    private JTextField loginTextField;
    private JButton registerButton;
    private JComboBox comboBox1;
    private JPanel Main1;

    public AuthWindow(GuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        JFrame frame = new JFrame("AuthWindow");
        frame.setContentPane(Main1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            Response response = guiHelper.sendRequest(
                    guiHelper.buildRequest(CommandType.LOGIN, loginTextField.getText(), passwordPasswordField.getText())
            );
            if (response.isSuccess()) {
                SwingUtilities.invokeLater(() -> {
                    new MainWindow(guiHelper, loginTextField.getText(), passwordPasswordField.getText());
                });
                frame.dispose();
            }
        });

        registerButton.addActionListener(e -> {
            Response response = guiHelper.sendRequest(
                    guiHelper.buildRequest(CommandType.REGISTER, loginTextField.getText(), passwordPasswordField.getText())
            );
            if (response.isSuccess()) {
                SwingUtilities.invokeLater(() -> {
                    new MainWindow(guiHelper, loginTextField.getText(), passwordPasswordField.getText());
                });
                frame.dispose();
            }
        });
    }
}
