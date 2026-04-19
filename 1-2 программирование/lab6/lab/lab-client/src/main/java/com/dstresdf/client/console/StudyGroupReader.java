package com.dstresdf.client.console;

import com.dstresdf.common.model.*;
import java.util.Scanner;

public class StudyGroupReader {
    private boolean isScriptMode = false;
    private final Console console;

    public StudyGroupReader(Console console) {
        this.console = console;
        setScriptMode(false);
    }
    public StudyGroup readElement(Scanner scanner) {
        StudyGroup elem = new StudyGroup();

        String name;
        if (!isScriptMode) {
            console.print("Введите имя: ");
            name = scanner.nextLine();
            while (name == null || name.equals("")) {
                console.println("Некорректный ввод) Поле не может быть null, Строка не может быть пустой.");
                console.print("Новая попытка: ");
                name = scanner.nextLine();
            }
        } else {
            name = scanner.nextLine();
        }

        Long x;
        if (!isScriptMode) {
            console.print("Введите координату x: ");
            while (scanner.hasNextLong() == false) {
                console.println("Неверный тип данных)");
                scanner.next();
                console.print("Новая попытка: ");
            }
            x = scanner.nextLong();
            while (x <= -23 || x == null) {
                console.println("Некорректный ввод) Значение поля должно быть больше -23, Поле не может быть null");
                console.print("Новая попытка: ");
                while (scanner.hasNextLong() == false) {
                    console.println("Неверный тип данных)");
                    scanner.next();
                    console.print("Новая попытка: ");
                }
                x = scanner.nextLong();
            }
        } else {
            if (!scanner.hasNextLong()) {
                return null;
            }
            x = scanner.nextLong();
        }

        long y;

        if (!isScriptMode) {
            console.print("Введите координату y: ");
            while (scanner.hasNextLong() == false) {
                console.println("Неверный тип данных)");
                scanner.next();
                console.print("Новая попытка: ");
            }
            y = scanner.nextLong();
            while (y > 316) {
                console.println("Некорректный ввод) Максимальное значение поля: 316");
                console.print("Новая попытка: ");
                while (scanner.hasNextLong() == false) {
                    console.println("Неверный тип данных)");
                    scanner.next();
                    console.print("Новая попытка: ");
                }
                y = scanner.nextLong();
            }
        } else {
            if (!scanner.hasNextLong()) {
                return null;
            }
            y = scanner.nextLong();
        }

        Integer studentsCount;
        if (!isScriptMode) {
            console.print("Введите кол-во студентов: ");
            while (scanner.hasNextInt() == false) {
                console.println("Неверный тип данных)");
                scanner.next();
                console.print("Новая попытка: ");
            }
            studentsCount = scanner.nextInt();
            while (studentsCount <= 0 || studentsCount == null) {
                console.println("Некорректный ввод) Значение поля должно быть больше 0, Поле не может быть null");
                console.print("Новая попытка: ");
                while (scanner.hasNextInt() == false) {
                    console.println("Неверный тип данных)");
                    scanner.next();
                    console.print("Новая попытка: ");
                }
                studentsCount = scanner.nextInt();
            }
        } else {
            if (!scanner.hasNextInt()) {
                return null;
            }
            studentsCount = scanner.nextInt();
        }

        long expelledStudents;
        if (!isScriptMode) {
            console.print("Введите кол-во отчисленных студентов: ");
            while (scanner.hasNextLong() == false) {
                console.println("Неверный тип данных)");
                scanner.next();
                console.print("Новая попытка: ");
            }
            expelledStudents = scanner.nextLong();
            while (expelledStudents <= 0) {
                console.println("Некорректный ввод) Значение поля должно быть больше 0");
                console.print("Новая попытка: ");
                while (scanner.hasNextLong() == false) {
                    console.println("Неверный тип данных)");
                    scanner.next();
                    console.print("Новая попытка: ");
                }
                expelledStudents = scanner.nextLong();
            }
        } else {
            if (!scanner.hasNextLong()) {
                return null;
            }
            expelledStudents = scanner.nextLong();
        }

        Long shouldBeExpelled;
        if (!isScriptMode) {
            console.print("Введите кол-во студентов, которые должны быть отчислены: ");
            while (scanner.hasNextLong() == false) {
                console.println("Неверный тип данных)");
                scanner.next();
                console.print("Новая попытка: ");
            }
            shouldBeExpelled = scanner.nextLong();
            while (shouldBeExpelled <= 0) {
                console.println("Некорректный ввод) Значение поля должно быть больше 0");
                console.print("Новая попытка: ");
                while (scanner.hasNextLong() == false) {
                    console.println("Неверный тип данных)");
                    scanner.next();
                    console.print("Новая попытка: ");
                }
                shouldBeExpelled = scanner.nextLong();
            }
        } else {
            if (!scanner.hasNextLong()) {
                return null;
            }
            shouldBeExpelled = scanner.nextLong();
        }

        int choice = -1;
        FormOfEducation formOfEducation = null;
        boolean isChoiseCorrect = false;
        if (!isScriptMode) {
            console.println("Введите форму обучения (0-3): ");
            console.println("0 - ПРОПУСТИТЬ ВВОД ФОРМЫ ОБУЧЕНИЯ");
            console.println("1 - ЗАОЧНОЕ");
            console.println("2 - ОЧНОЕ");
            console.println("3 - ВЕЧЕРНЕЕ");

            while (isChoiseCorrect == false) {
                if (!scanner.hasNextInt()) {
                    console.println("Неверный тип данных");
                    scanner.next();
                } else {
                    choice = scanner.nextInt();
                    if (choice < 0 || choice > 3) {
                        console.println("0-3 :)");
                    } else {
                        isChoiseCorrect = true;
                    }
                }
            }
        } else {
            if (!scanner.hasNextInt()) {
                return null;
            }
            choice = scanner.nextInt();
        }

        switch (choice) {
            case 0:
                formOfEducation = null;
                break;
            case 1:
                formOfEducation = FormOfEducation.DISTANCE_EDUCATION;
                break;
            case 2:
                formOfEducation = FormOfEducation.FULL_TIME_EDUCATION;
                break;
            case 3:
                formOfEducation = FormOfEducation.EVENING_CLASSES;
                break;
        }
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        String groupAdminYN;
        if (!isScriptMode) {
            console.print("Будете ли вводить старосту группы (Y/N): ");
            groupAdminYN = scanner.nextLine();
            while (!groupAdminYN.equalsIgnoreCase("Y") & !groupAdminYN.equalsIgnoreCase("N")) {
                console.print("Будете ли вводить старосту группы (Y/N): ");
                groupAdminYN = scanner.nextLine();
            }
        } else {
            if (!scanner.hasNextLine()) {
                return null;
            }
            groupAdminYN = scanner.nextLine();
        }

        Person groupAdmin = new Person();

        if (groupAdminYN.equalsIgnoreCase("y")) {
            String nameOfGroupAdmin;
            if (!isScriptMode) {
                console.print("Введите имя старосты: ");
                nameOfGroupAdmin = scanner.nextLine();
                while (nameOfGroupAdmin == null || nameOfGroupAdmin.equals("")) {
                    console.println("Некорректный ввод) Поле не может быть null, Строка не может быть пустой.");
                    console.print("Новая попытка: ");
                    nameOfGroupAdmin = scanner.nextLine();
                }
            } else {
                if (!scanner.hasNextLine()) {
                    return null;
                }
                nameOfGroupAdmin = scanner.nextLine();
            }

            double weightOfGroupAdmin;
            if (!isScriptMode) {
                console.print("Введите вес старосты: ");
                while (scanner.hasNextDouble() == false) {
                    console.println("Неверный тип данных)");
                    scanner.next();
                    console.print("Новая попытка: ");
                }
                weightOfGroupAdmin = scanner.nextDouble();
                scanner.nextLine();
                while (weightOfGroupAdmin <= 0) {
                    console.println("Некорректный ввод) Значение поля должно быть больше 0");
                    console.print("Новая попытка: ");
                    while (scanner.hasNextDouble() == false) {
                        console.println("Неверный тип данных)");
                        scanner.next();
                        console.print("Новая попытка: ");
                    }
                    weightOfGroupAdmin = scanner.nextDouble();
                }
            } else {
                if (!scanner.hasNextDouble()) {
                    return null;
                }
                weightOfGroupAdmin = scanner.nextDouble();
            }

            EyeColor eyeColor = null;
            isChoiseCorrect = false;
            if (!isScriptMode) {
                console.println("Выберите цвет глаз старосты (0-3): ");
                console.println("0 - ПРОПУСТИТЬ ВВОД ЦВЕТА ГЛАЗ");
                console.println("1 - ЗЕЛЕНЫЕ");
                console.println("2 - ЖЕЛТЫЕ");
                console.println("3 - КАРИЕ");
                while (isChoiseCorrect == false) {
                    if (!scanner.hasNextInt()) {
                        console.println("Неверный тип данных");
                        scanner.next();
                    } else {
                        choice = scanner.nextInt();
                        if (choice < 0 || choice > 3) {
                            console.println("0-3 :)");
                        } else {
                            isChoiseCorrect = true;
                        }
                    }
                }
            } else {
                if (!scanner.hasNextInt()) {
                    return null;
                }
                choice = scanner.nextInt();
            }
            switch (choice) {
                case 0:
                    eyeColor = null;
                    break;
                case 1:
                    eyeColor = EyeColor.GREEN;
                    break;
                case 2:
                    eyeColor = EyeColor.YELLOW;
                    break;
                case 3:
                    eyeColor = EyeColor.BROWN;
                    break;
            }

            HairColor hairColor = null;
            isChoiseCorrect = false;
            if (!isScriptMode) {
                console.println("Выберите цвет волос старосты (0-4): ");
                console.println("0 - ПРОПУСТИТЬ ВВОД ЦВЕТА ВОЛОС");
                console.println("1 - ЧЕРНЫЕ");
                console.println("2 - СИНИЕ");
                console.println("3 - БЕЛЫЕ");
                console.println("4 - РУСЫЕ");

                while (isChoiseCorrect == false) {
                    if (!scanner.hasNextInt()) {
                        console.println("Неверный тип данных");
                        scanner.next();
                    } else {
                        choice = scanner.nextInt();
                        if (choice < 0 || choice > 4) {
                            console.println("0-4 :)");
                        } else {
                            isChoiseCorrect = true;
                        }
                    }
                }
            } else {
                if (!scanner.hasNextInt()) {
                    return null;
                }
                choice = scanner.nextInt();
            }
            switch (choice) {
                case 0:
                    hairColor = null;
                    break;
                case 1:
                    hairColor = HairColor.BLACK;
                    break;
                case 2:
                    hairColor = HairColor.BLUE;
                    break;
                case 3:
                    hairColor = HairColor.WHITE;
                    break;
                case 4:
                    hairColor = HairColor.BROWN;
                    break;
            }
            groupAdmin.setName(nameOfGroupAdmin);
            groupAdmin.setWeight(weightOfGroupAdmin);
            groupAdmin.setEyeColor(eyeColor);
            groupAdmin.setHairColor(hairColor);
        } else {
            groupAdmin = null;
        }

        Coordinates coordinates = new Coordinates();
        coordinates.setX(x);
        coordinates.setY(y);

        elem.setName(name);
        elem.setCoordinates(coordinates);
        elem.setStudentsCount(studentsCount);
        elem.setExpelledStudents(expelledStudents);
        elem.setShouldBeExpelled(shouldBeExpelled);
        elem.setFormOfEducation(formOfEducation);
        elem.setGroupAdmin(groupAdmin);
        elem.check();
        isScriptMode = false;
        System.out.println(elem.toString());
        return elem;

    }

    public void setScriptMode(boolean isScriptMode) {
        this.isScriptMode = isScriptMode;
    }
}
