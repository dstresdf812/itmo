package com.dstresdf.client.gui;

import com.dstresdf.client.network.GuiHelper;
import com.dstresdf.common.commands.CommandType;
import com.dstresdf.common.network.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuthWindow {
    private GuiHelper guiHelper;
    private JFrame frame;
    private final ExecutorService networkExecutor = Executors.newSingleThreadExecutor();
    private JButton loginButton;
    private JPasswordField passwordPasswordField;
    private JTextField loginTextField;
    private JButton registerButton;
    private JComboBox languageBox;
    private JPanel Main1;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JLabel authLabel;
    private JLabel languageLabel;

    private LocalizationManager localizationManager;
    public AuthWindow(GuiHelper guiHelper,LocalizationManager localizationManager) {
        this.guiHelper = guiHelper;
        this.localizationManager = localizationManager;
        frame = new JFrame("AuthWindow");
        frame.setMinimumSize(new Dimension(500,250));
        frame.setContentPane(Main1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                networkExecutor.shutdownNow();
            }
        });
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        loginButton.addActionListener(e -> authenticate(CommandType.LOGIN));
        registerButton.addActionListener(e -> authenticate(CommandType.REGISTER));

        languageBox.addActionListener(e -> {
            String selectedLanguage = languageBox.getSelectedItem().toString();
            localizationManager.setLocale(selectedLanguage);
            applyLocalization();
        });
    }

    private void authenticate(CommandType commandType) {
        String login = loginTextField.getText();
        String password = passwordPasswordField.getText();
        loginButton.setEnabled(false);
        registerButton.setEnabled(false);

        networkExecutor.submit(() -> {
            Response response = guiHelper.sendRequest(
                    guiHelper.buildRequest(commandType, login, password)
            );

            SwingUtilities.invokeLater(() -> {
                if (response.isSuccess()) {
                    networkExecutor.shutdownNow();
                    frame.setVisible(false);
                    frame.dispose();
                    new MainWindow(guiHelper, login, password, localizationManager);
                    return;
                }
                loginButton.setEnabled(true);
                registerButton.setEnabled(true);
                JOptionPane.showMessageDialog(frame, response.getMessage(), "", JOptionPane.ERROR_MESSAGE);
            });
        });
    }

    private void applyLocalization() {
        frame.setTitle(localizationManager.get("window.auth"));
        loginButton.setText(localizationManager.get("button.login"));
        registerButton.setText(localizationManager.get("button.register"));
        loginLabel.setText(localizationManager.get("label.login"));
        passwordLabel.setText(localizationManager.get("label.password"));
        authLabel.setText(localizationManager.get("window.auth"));
        languageLabel.setText(localizationManager.get("label.language"));
    }
}
