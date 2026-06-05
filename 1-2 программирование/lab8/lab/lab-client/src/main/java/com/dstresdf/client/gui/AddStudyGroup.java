package com.dstresdf.client.gui;

import com.dstresdf.client.network.GuiHelper;
import com.dstresdf.common.commands.CommandType;
import com.dstresdf.common.model.*;
import com.dstresdf.common.network.Response;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.*;

public class AddStudyGroup extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameField;
    private JComboBox formOfEducationBox;
    private JTextField xField;
    private JTextField yField;
    private JTextField shouldBeExpelledField;
    private JTextField expelledField;
    private JTextField studentsField;
    private JTextField groupAdminNameField;
    private JTextField groupAdminWeightField;
    private JComboBox eyeColorBox;
    private JComboBox hairColorBox;
    private JCheckBox hasGroupAdmin;
    private JPanel GroupAdminPanel;

    private GuiHelper guiHelper;
    private String login;
    private String password;

    public AddStudyGroup(GuiHelper guiHelper, String login, String password) {
        this.guiHelper = guiHelper;
        this.login = login;
        this.password = password;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        initComponents();
    }

    private void onOK() {
        String t = validateFields();
        if (!t.equals("TRUE")) {
            JOptionPane.showMessageDialog(this, t, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName(nameField.getText());

        Coordinates coordinates = new Coordinates();
        coordinates.setX(xField.getText() == null ? null : Long.parseLong(xField.getText()));
        coordinates.setY(Long.parseLong(yField.getText()));
        studyGroup.setCoordinates(coordinates);

        studyGroup.setStudentsCount(Integer.parseInt(studentsField.getText()));
        studyGroup.setExpelledStudents(Long.parseLong(expelledField.getText()));
        if (shouldBeExpelledField.getText() == null || shouldBeExpelledField.getText().length() == 0) {
            studyGroup.setShouldBeExpelled(null);
        } else {
            studyGroup.setShouldBeExpelled(Long.parseLong(shouldBeExpelledField.getText()));
        }
        studyGroup.setFormOfEducation((FormOfEducation) formOfEducationBox.getSelectedItem());


        if (hasGroupAdmin.isSelected()){
            Person person = new Person();
            person.setName(groupAdminNameField.getText());
            person.setWeight(Integer.parseInt(groupAdminWeightField.getText()));
            person.setEyeColor((EyeColor) eyeColorBox.getSelectedItem());
            person.setHairColor((HairColor) hairColorBox.getSelectedItem());
            studyGroup.setGroupAdmin(person);
        }
        studyGroup.check();
        Response response = guiHelper.sendRequest(
        guiHelper.buildRequest(CommandType.INSERT, login, password, 0, studyGroup)
        );
        System.out.println(response.isSuccess() + response.getMessage());
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void initComponents() {
        allowOnlyDigits(studentsField);
        allowOnlyDigits(expelledField);
        allowOnlyDigits(shouldBeExpelledField);
        allowOnlyDigits(xField);
        allowOnlyDigits(yField);
        allowOnlyDigits(groupAdminWeightField);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        GroupAdminPanel.setVisible(false);
        hasGroupAdmin.addActionListener(e -> {
            boolean t = hasGroupAdmin.isSelected();
            GroupAdminPanel.setVisible(t);
            this.pack();
            GroupAdminPanel.revalidate();
            GroupAdminPanel.repaint();
        });

        DefaultComboBoxModel<FormOfEducation> model = new DefaultComboBoxModel<>();
        model.addElement(null);

        for (FormOfEducation value : FormOfEducation.values()) {
            model.addElement(value);
        }
        formOfEducationBox.setModel(model);

        DefaultComboBoxModel<EyeColor> eyeColorModel = new DefaultComboBoxModel<>();
        eyeColorModel.addElement(null);
        for (EyeColor value : EyeColor.values()) {
            eyeColorModel.addElement(value);
        }
        eyeColorBox.setModel(eyeColorModel);

        DefaultComboBoxModel<HairColor> hairColorModel = new DefaultComboBoxModel<>();
        hairColorModel.addElement(null);
        for (HairColor value : HairColor.values()) {
            hairColorModel.addElement(value);
        }
        hairColorBox.setModel(hairColorModel);
    }

    private String validateFields() {
        StringBuilder sb = new StringBuilder();
        String name = nameField.getText();

        if (name == null || name.isEmpty()) {
            sb.append("Введите имя.\n");
        }

        if (xField.getText() == null || xField.getText().length() == 0) {
            sb.append("Введите x.\n");
        } else {
            Long x = Long.parseLong(xField.getText());
            if (x <= -23) {
                sb.append("X должен быть > -23.\n");
            }
        }


        if (yField.getText() == null || yField.getText().length() == 0) {
            sb.append("Введите y.\n");
        } else {
            Long y = Long.parseLong(yField.getText());
            if (y > 316) {
                sb.append("Y должен быть <= 316.\n");
            }
        }


        if (studentsField.getText() == null || studentsField.getText().length() == 0) {
            sb.append("Введите кол-во студентов.\n");
        } else {
            Integer studentsCount = Integer.parseInt(studentsField.getText());
            if (studentsCount <= 0) {
                sb.append("Кол-во студентов должно быть больше 0.\n");
            }
        }

        if (expelledField.getText() == null || expelledField.getText().length() == 0) {
            sb.append("Введите кол-во отчисленных студентов.\n");
        } else {
            Long expelledStudents = Long.parseLong(expelledField.getText());
            if (expelledStudents <= 0) {
                sb.append("Кол-во отчисленных студентов должно быть больше 0.\n");
            }
        }

        if (shouldBeExpelledField.getText() != null && shouldBeExpelledField.getText().length() != 0) {
            Long shouldBeExpelled = Long.parseLong(shouldBeExpelledField.getText());
            if (shouldBeExpelled <= 0) {
                sb.append("Кол-во студентов, которые должны быть отчислены, должно быть больше 0.\n");
            }
        }

        if (hasGroupAdmin.isSelected()) {
            String adminName = groupAdminNameField.getText();
            if (adminName == null || adminName.isEmpty()) {
                sb.append("Укажите имя старосты.\n");
            }

            if (groupAdminWeightField.getText() == null || groupAdminWeightField.getText().length() == 0) {
                sb.append("Введите вес старосты.\n");
            } else {
                Integer groupAdminWeight = Integer.parseInt(groupAdminWeightField.getText());
                if (groupAdminWeight <= 0) {
                    sb.append("Вес старосты должен быть > 0.\n");
                }
            }

            if (eyeColorBox.getSelectedItem() == null) {
                sb.append("Выберите цвет глаз старосты.\n");
            }
            if (hairColorBox.getSelectedItem() == null) {
                sb.append("Выберите цвет волос старосты.\n");
            }
        }

        if (sb.length() == 0) {
            sb.append("TRUE");
        }
        return sb.toString();
    }
    private void allowOnlyDigits(JTextField field) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string != null && string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text != null && text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
}
