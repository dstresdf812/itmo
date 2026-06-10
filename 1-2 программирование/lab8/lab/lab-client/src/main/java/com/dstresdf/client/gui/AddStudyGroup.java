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
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddStudyGroup extends JDialog {
    private JPanel contentPane1;
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
    private JLabel titleLabel;
    private JLabel nameLabel;
    private JLabel formOfEducationLabel;
    private JLabel coordinatesLabel;
    private JLabel xLabel;
    private JLabel yLabel;
    private JLabel groupParamsLabel;
    private JLabel studentsLabel;
    private JLabel expelledLabel;
    private JLabel shouldBeExpelledLabel;
    private JLabel groupAdminLabel;
    private JLabel adminNameLabel;
    private JLabel adminWeightLabel;
    private JLabel eyeColorLabel;
    private JLabel hairColorLabel;

    private GuiHelper guiHelper;
    private String login;
    private String password;
    private LocalizationManager localizationManager;
    private CommandType commandType = CommandType.INSERT;
    private Integer elementId = 0;
    private final ExecutorService networkExecutor = Executors.newSingleThreadExecutor();

    public AddStudyGroup(GuiHelper guiHelper, String login, String password) {
        this(guiHelper, login, password, new LocalizationManager());
    }

    public AddStudyGroup(GuiHelper guiHelper, String login, String password, LocalizationManager localizationManager) {
        this.guiHelper = guiHelper;
        this.login = login;
        this.password = password;
        this.localizationManager = localizationManager;
        setContentPane(contentPane1);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.setMinimumSize(new Dimension(342, 600));
        initComponents();
        applyLocalization();
    }


    public AddStudyGroup(GuiHelper guiHelper, String login, String password, LocalizationManager localizationManager, Integer elementId, StudyGroup studyGroup) {
        this(guiHelper, login, password, localizationManager);
        this.commandType = CommandType.UPDATE;
        this.elementId = elementId;

        fillFields(studyGroup);
    }

    private void onOK() {
        String t = validateFields();
        if (!t.equals("TRUE")) {
            JOptionPane.showMessageDialog(this, t, localizationManager.get("message.validationError"), JOptionPane.ERROR_MESSAGE);
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
        buttonOK.setEnabled(false);
        buttonCancel.setEnabled(false);
        networkExecutor.submit(() -> {
            Response response = guiHelper.sendRequest(
                    guiHelper.buildRequest(commandType, login, password, elementId, studyGroup)
            );
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, response.getMessage(), "", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            });
        });
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
        contentPane1.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setGroupAdminVisible(false);
        hasGroupAdmin.addActionListener(e -> {
            setGroupAdminVisible(hasGroupAdmin.isSelected());
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

    private void fillFields(StudyGroup studyGroup) {
        if (studyGroup == null) {
            return;
        }

        nameField.setText(studyGroup.getName());
        if (studyGroup.getCoordinates() != null) {
            xField.setText(String.valueOf(studyGroup.getCoordinates().getX()));
            yField.setText(String.valueOf(studyGroup.getCoordinates().getY()));
        }
        studentsField.setText(String.valueOf(studyGroup.getStudentsCount()));
        expelledField.setText(String.valueOf(studyGroup.getExpelledStudents()));
        if (studyGroup.getShouldBeExpelled() != null) {
            shouldBeExpelledField.setText(String.valueOf(studyGroup.getShouldBeExpelled()));
        }
        formOfEducationBox.setSelectedItem(studyGroup.getFormOfEducation());

        if (hasGroupAdminData(studyGroup.getGroupAdmin())) {
            Person admin = studyGroup.getGroupAdmin();
            setGroupAdminVisible(true);
            groupAdminNameField.setText(admin.getName());
            groupAdminWeightField.setText(String.valueOf((int) admin.getWeight()));
            eyeColorBox.setSelectedItem(admin.getEyeColor());
            hairColorBox.setSelectedItem(admin.getHairColor());
        } else {
            setGroupAdminVisible(false);
            groupAdminNameField.setText("");
            groupAdminWeightField.setText("");
            eyeColorBox.setSelectedItem(null);
            hairColorBox.setSelectedItem(null);
        }
    }

    private void setGroupAdminVisible(boolean visible) {
        hasGroupAdmin.setSelected(visible);
        GroupAdminPanel.setVisible(visible);
        pack();
        GroupAdminPanel.revalidate();
        GroupAdminPanel.repaint();
    }

    private boolean hasGroupAdminData(Person person) {
        return person != null
                && person.getName() != null
                && !person.getName().isEmpty()
                && person.getWeight() > 0;
    }

    private String validateFields() {
        StringBuilder sb = new StringBuilder();
        String name = nameField.getText();

        if (name == null || name.isEmpty()) {
            sb.append(localizationManager.get("validation.nameRequired")).append("\n");
        }

        if (xField.getText() == null || xField.getText().length() == 0) {
            sb.append(localizationManager.get("validation.xRequired")).append("\n");
        } else {
            Long x = Long.parseLong(xField.getText());
            if (x <= -23) {
                sb.append(localizationManager.get("validation.xMin")).append("\n");
            }
        }


        if (yField.getText() == null || yField.getText().length() == 0) {
            sb.append(localizationManager.get("validation.yRequired")).append("\n");
        } else {
            Long y = Long.parseLong(yField.getText());
            if (y > 316) {
                sb.append(localizationManager.get("validation.yMax")).append("\n");
            }
        }


        if (studentsField.getText() == null || studentsField.getText().length() == 0) {
            sb.append(localizationManager.get("validation.studentsRequired")).append("\n");
        } else {
            Integer studentsCount = Integer.parseInt(studentsField.getText());
            if (studentsCount <= 0) {
                sb.append(localizationManager.get("validation.studentsPositive")).append("\n");
            }
        }

        if (expelledField.getText() == null || expelledField.getText().length() == 0) {
            sb.append(localizationManager.get("validation.expelledRequired")).append("\n");
        } else {
            Long expelledStudents = Long.parseLong(expelledField.getText());
            if (expelledStudents <= 0) {
                sb.append(localizationManager.get("validation.expelledPositive")).append("\n");
            }
        }

        if (shouldBeExpelledField.getText() != null && shouldBeExpelledField.getText().length() != 0) {
            Long shouldBeExpelled = Long.parseLong(shouldBeExpelledField.getText());
            if (shouldBeExpelled <= 0) {
                sb.append(localizationManager.get("validation.shouldBeExpelledPositive")).append("\n");
            }
        }

        if (hasGroupAdmin.isSelected()) {
            String adminName = groupAdminNameField.getText();
            if (adminName == null || adminName.isEmpty()) {
                sb.append(localizationManager.get("validation.adminNameRequired")).append("\n");
            }

            if (groupAdminWeightField.getText() == null || groupAdminWeightField.getText().length() == 0) {
                sb.append(localizationManager.get("validation.adminWeightRequired")).append("\n");
            } else {
                Integer groupAdminWeight = Integer.parseInt(groupAdminWeightField.getText());
                if (groupAdminWeight <= 0) {
                    sb.append(localizationManager.get("validation.adminWeightPositive")).append("\n");
                }
            }

            if (eyeColorBox.getSelectedItem() == null) {
                sb.append(localizationManager.get("validation.eyeColorRequired")).append("\n");
            }
            if (hairColorBox.getSelectedItem() == null) {
                sb.append(localizationManager.get("validation.hairColorRequired")).append("\n");
            }
        }

        if (sb.length() == 0) {
            sb.append("TRUE");
        }
        return sb.toString();
    }

    @Override
    public void dispose() {
        networkExecutor.shutdownNow();
        super.dispose();
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

    private void applyLocalization() {
        setTitle(localizationManager.get("window.studyGroup"));
        titleLabel.setText(localizationManager.get("window.studyGroup"));
        nameLabel.setText(localizationManager.get("field.name"));
        formOfEducationLabel.setText(localizationManager.get("field.formOfEducation"));
        coordinatesLabel.setText(localizationManager.get("label.coordinates"));
        xLabel.setText(localizationManager.get("field.x") + ":");
        yLabel.setText(localizationManager.get("field.y") + ":");
        groupParamsLabel.setText(localizationManager.get("label.groupParams"));
        studentsLabel.setText(localizationManager.get("field.studentsCount"));
        expelledLabel.setText(localizationManager.get("field.expelledStudents"));
        shouldBeExpelledLabel.setText(localizationManager.get("field.shouldBeExpelled"));
        groupAdminLabel.setText(localizationManager.get("label.groupAdmin"));
        hasGroupAdmin.setText(localizationManager.get("label.hasGroupAdmin"));
        adminNameLabel.setText(localizationManager.get("field.adminName"));
        adminWeightLabel.setText(localizationManager.get("field.adminWeight"));
        eyeColorLabel.setText(localizationManager.get("field.eyeColor"));
        hairColorLabel.setText(localizationManager.get("field.hairColor"));
        buttonOK.setText(localizationManager.get("button.ok"));
        buttonCancel.setText(localizationManager.get("button.cancel"));
        pack();
    }
}
