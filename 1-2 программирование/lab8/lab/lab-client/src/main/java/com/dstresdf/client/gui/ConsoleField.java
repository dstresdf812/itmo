package com.dstresdf.client.gui;

import com.dstresdf.client.console.ScriptExecutor;
import com.dstresdf.client.console.StudyGroupReader;
import com.dstresdf.client.network.GuiHelper;
import com.dstresdf.common.commands.ArgumentType;
import com.dstresdf.common.commands.CommandType;
import com.dstresdf.common.model.*;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsoleField extends JTextArea {
    GuiHelper guiHelper;
    String login;
    String password;
    ExecutorService networkExecutor;

    private CommandType pendingCommand;
    private Integer pendingKey;
    private ElementInputState inputState = ElementInputState.NONE;
    private StudyGroup pendingGroup = new StudyGroup();
    private Coordinates pendingCoordinates = new Coordinates();
    private Person pendingGroupAdmin = new Person();

    public ConsoleField(GuiHelper guiHelper, String login, String password, ExecutorService networkExecutor) {
        this.guiHelper = guiHelper;
        this.login = login;
        this.password = password;
        this.networkExecutor = networkExecutor;
        setFont(new Font("Monospaced", Font.PLAIN, 14));
        setEditable(true);
        setEnabled(true);
        setFocusable(true);
        setAutoscrolls(true);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        onEnter();
                    } catch (BadLocationException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    public void onEnter() throws BadLocationException {
        String text = getLastLine().trim();

        if (inputState != ElementInputState.NONE) {
            processElementInput(text);
        } else {
            setEditable(false);
            execute(text);
        }
    }

    public void execute(String text) {
        String[] input = text.trim().split(" ");

        if (input.length == 0 || input[0].isEmpty()) {
            setEditable(true);
            return;
        }

        CommandType command = CommandType.getCommand(input[0]);

        if (command == null) {
            println("Invalid command");
            setEditable(true);
            return;
        }

        Request request = null;

        try {
            if (command.getArg() == ArgumentType.NO_ARGS) {
                if (input.length != 1) {
                    println("Эта команда не принимает аргументы");
                    return;
                }

                request = guiHelper.buildRequest(command, login, password);

            } else if (command.getArg() == ArgumentType.ONE_ARG) {
                if (input.length != 2) {
                    println("Команда должна иметь один аргумент");
                    return;
                }


                int argument = Integer.parseInt(input[1]);
                request = guiHelper.buildRequest(
                        command,
                        login,
                        password,
                        argument
                );

            } else if (command.getArg() == ArgumentType.ARG_AND_ELEM) {
                if (input.length != 2) {
                    println("Команда должна иметь ключ и элемент");
                    return;
                }

                int key = Integer.parseInt(input[1]);

                inputState = ElementInputState.NAME;
                pendingKey = key;
                pendingCommand = command;
                setEditable(true);
                println("Введите имя:");
                return;

            }
        } catch (NumberFormatException e) {
            println("Аргумент команды должен быть числом");
            return;
        }
        if (request == null) {
            println("(AS(DASD((DAS");
            return;
        }
        final Request requestToSend = request;

        networkExecutor.submit(() -> {
            Response response = guiHelper.sendRequest(requestToSend);
            SwingUtilities.invokeLater(() -> {
                println(response.getMessage());

                if (response.getStudyGroups() != null) {
                    for (StudyGroup group : response.getStudyGroups()) {
                        println(group.toString());
                    }
                }

                println("");
                setEditable(true);
                requestFocusInWindow();
            });
        });
    }

    private void processElementInput(String text) {
        try {
            if (inputState == ElementInputState.NAME) {
                if (text.isEmpty()) {
                    println("Имя не может быть пустым");
                    return;
                }

                pendingGroup.setName(text);
                inputState = ElementInputState.X;
                println("Введите X:");
                return;
            }
            if (inputState == ElementInputState.X) {
                long x = Long.parseLong(text);

                if (x <= -23) {
                    println("X должен быть больше -23");
                    return;
                }

                pendingCoordinates.setX(x);
                inputState = ElementInputState.Y;
                println("Введите Y:");
                return;
            }
            if (inputState == ElementInputState.Y) {
                long y = Long.parseLong(text);

                if (y > 316) {
                    println("Y должен быть не больше 316");
                    return;
                }

                pendingCoordinates.setY(y);
                pendingGroup.setCoordinates(pendingCoordinates);
                inputState = ElementInputState.STUDENTS_COUNT;
                println("Введите количество студентов:");
                return;
            }
            if (inputState == ElementInputState.STUDENTS_COUNT) {
                int studentsCount = Integer.parseInt(text);
                if (studentsCount <= 0) {
                    println("Кол-во студентов должно быть >0");
                    return;
                }
                pendingGroup.setStudentsCount(studentsCount);
                inputState = ElementInputState.EXPELLED_STUDENTS;
                println("Введите кол-во отчисленных студентов:");
                return;
            }
            if (inputState == ElementInputState.EXPELLED_STUDENTS) {
                long expelledStudent = Long.parseLong(text);
                if (expelledStudent <= 0) {
                    println("Кол-во отчисленных студентов должно быть >0");
                    return;
                }
                pendingGroup.setExpelledStudents(expelledStudent);
                inputState = ElementInputState.SHOULD_BE_EXPELLED;
                println("Введите кол-во студентов, которые должны быть отчислены:");
                return;
            }
            if (inputState == ElementInputState.SHOULD_BE_EXPELLED) {
                long shouldBeExpelled = Long.parseLong(text);
                if (shouldBeExpelled <= 0) {
                    println("Кол-во отчисленных студентов, которые должны быть отчислены должно быть >0");
                    return;
                }
                pendingGroup.setShouldBeExpelled(shouldBeExpelled);
                inputState = ElementInputState.FORM_OF_EDUCATION;
                println("Введите форму обучения:");
                println("Введите форму обучения (0-3): ");
                println("0 - ПРОПУСТИТЬ ВВОД ФОРМЫ ОБУЧЕНИЯ");
                println("1 - ЗАОЧНОЕ");
                println("2 - ОЧНОЕ");
                println("3 - ВЕЧЕРНЕЕ");
                return;
            }
            if (inputState == ElementInputState.FORM_OF_EDUCATION) {
                FormOfEducation formOfEducation = null;
                int choice = Integer.parseInt(text);
                if (choice < 0 || choice > 3) {
                    println("0-3 :(");
                    return;
                }
                if (choice == 0) {
                    formOfEducation = null;
                }
                else if (choice == 1) {
                    formOfEducation = FormOfEducation.DISTANCE_EDUCATION;
                } else if (choice == 2) {
                    formOfEducation = FormOfEducation.FULL_TIME_EDUCATION;
                } else if (choice == 3) {
                    formOfEducation = FormOfEducation.EVENING_CLASSES;
                }
                pendingGroup.setFormOfEducation(formOfEducation);
                inputState = ElementInputState.HAS_ADMIN;
                println("Будете ли вводить старосту группы? (Y/N)");
                return;
            }
            if (inputState == ElementInputState.HAS_ADMIN) {
                if (text.toLowerCase().equals("y")) {
                    inputState = ElementInputState.ADMIN_NAME;
                    println("Введите имя старосты:");
                    return;
                } if (text.toLowerCase().equals("n")) {
                    pendingGroup.setGroupAdmin(null);
                    System.out.println("1");
                    finishElementInput();
                } else {
                    println("Y/N");
                    return;
                }
            }
            if (inputState == ElementInputState.ADMIN_NAME) {
                if (text.isEmpty()) {
                    println("Имя не может быть пустым");
                    return;
                }

                pendingGroupAdmin.setName(text);
                inputState = ElementInputState.ADMIN_WEIGHT;
                println("Введите вес старосты:");
                return;
            }
            if (inputState == ElementInputState.ADMIN_WEIGHT) {
                double weight = Double.parseDouble(text);
                if (weight <= 0) {
                    println("Вес >0");
                    return;
                }
                pendingGroupAdmin.setWeight(weight);
                inputState = ElementInputState.ADMIN_EYE_COLOR;
                println("Введите цвет глаз старосты:");
                println("0 - ПРОПУСТИТЬ");
                println("1 - ЗЕЛЕНЫЕ");
                println("2 - ЖЕЛТЫЕ");
                println("3 - КАРИЕ");
            }
            if (inputState == ElementInputState.ADMIN_EYE_COLOR) {
                int choice = Integer.parseInt(text);
                EyeColor eyeColor = null;
                if (choice < 0 || choice > 3) {
                    println("0-3 :(");
                }
                if (choice == 0) {
                    eyeColor = null;
                }
                else if (choice == 1) {
                    eyeColor = EyeColor.GREEN;
                }
                else if (choice == 2) {
                    eyeColor = EyeColor.YELLOW;
                } else if (choice == 3) {
                    eyeColor = EyeColor.BROWN;
                }
                pendingGroupAdmin.setEyeColor(eyeColor);
                inputState = ElementInputState.ADMIN_HAIR_COLOR;
                println("Введите цвет волос старосты:");
                println("0 - ПРОПУСТИТЬ");
                println("1 - ЧЕРНЫЕ");
                println("2 - СИНИЕ");
                println("3 - БЕЛЫЕ");
                println("4 - РУСЫЕ");
                return;
            }
            if (inputState == ElementInputState.ADMIN_HAIR_COLOR) {
                int choice = Integer.parseInt(text);
                HairColor hairColor = null;
                if (choice < 0 || choice > 4) {
                    println("0-4 :(");
                }
                if (choice == 0) {
                    hairColor = null;
                }
                else if (choice == 1) {
                    hairColor = HairColor.BLACK;
                }
                else if (choice == 2) {
                    hairColor = HairColor.BLUE;
                } else if (choice == 3) {
                    hairColor = HairColor.WHITE;
                } else if (choice == 4) {
                    hairColor = HairColor.BROWN;
                }
                pendingGroupAdmin.setHairColor(hairColor);
                pendingGroup.setGroupAdmin(pendingGroupAdmin);
                finishElementInput();
            }
        } catch (NumberFormatException e) {
            println("Введите число");
        }
    }

    private void finishElementInput() {
        inputState = ElementInputState.NONE;
        System.out.println("2");
        networkExecutor.submit(() -> {
            System.out.println("3");
            Request request = guiHelper.buildRequest(
                    pendingCommand,
                    login,
                    password,
                    pendingKey,
                    pendingGroup
            );
            System.out.println("4");
            Response response = guiHelper.sendRequest(request);
            System.out.println("5");
            SwingUtilities.invokeLater(() -> {
                System.out.println("6");
                println(response.getMessage());
                setEditable(true);
            });
            pendingCommand = null;
            pendingKey = null;
            pendingGroup = new StudyGroup();
            pendingCoordinates = new Coordinates();
            pendingGroupAdmin = new Person();
        });
        System.out.println("7");
    }
    public void print(String text) {
        setText(getText() + text);
    }

    public void println(String text) {
        setText(getText() + "\n" + text);
    }

    private String getLastLine() throws BadLocationException {
        int n = getLineCount();
        String text = getText().substring(getLineStartOffset(n - 1));
        return text;
    }


    private enum ElementInputState {
        NONE,
        NAME,
        X,
        Y,
        STUDENTS_COUNT,
        EXPELLED_STUDENTS,
        SHOULD_BE_EXPELLED,
        FORM_OF_EDUCATION,
        HAS_ADMIN,
        ADMIN_NAME,
        ADMIN_WEIGHT,
        ADMIN_EYE_COLOR,
        ADMIN_HAIR_COLOR
    }
}