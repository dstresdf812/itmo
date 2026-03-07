package managers;

import commands.Command;
import other.*;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Ввод/вывод данных.
 * @author dmitrij
 */
public class Console {
    private final Scanner scanner = new Scanner(System.in);
    private final CollectionManager collectionManager = new CollectionManager();
    public static boolean isScriptMode = false;

    public void print(Object o) {
        System.out.print(o);
    }

    public void println(Object o) {
        System.out.println(o);
    }

    public String[] read() {
        print("Введите команду: ");
        String s = scanner.nextLine();
        String[] input = s.split(" ");
        return input;
    }

    /**
     * Считывание элемента. Каждое поле в отдельной строке.
     * @param scanner
     * @return
     */
    public StudyGroup readElement(Scanner scanner) {
        StudyGroup elem = new StudyGroup();
        int id = collectionManager.getIncId();

        String name;
        if (!isScriptMode) {
            print("Введите имя: ");
            name = scanner.nextLine();
            while (name == null || name.equals("")) {
                println("Некорректный ввод) Поле не может быть null, Строка не может быть пустой.");
                print("Новая попытка: ");
                name = scanner.nextLine();
            }
        } else {
            name = scanner.nextLine();
        }

        Long x;
        if (!isScriptMode) {
            print("Введите координату x: ");
            while (scanner.hasNextLong() == false) {
                println("Неверный тип данных)");
                scanner.next();
                print("Новая попытка: ");
            }
            x = scanner.nextLong();
            while (x <= -23 || x == null) {
                println("Некорректный ввод) Значение поля должно быть больше -23, Поле не может быть null");
                print("Новая попытка: ");
                while (scanner.hasNextLong() == false) {
                    println("Неверный тип данных)");
                    scanner.next();
                    print("Новая попытка: ");
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
            print("Введите координату y: ");
            while (scanner.hasNextLong() == false) {
                println("Неверный тип данных)");
                scanner.next();
                print("Новая попытка: ");
            }
            y = scanner.nextLong();
            while (y > 316) {
                println("Некорректный ввод) Максимальное значение поля: 316");
                print("Новая попытка: ");
                while (scanner.hasNextLong() == false) {
                    println("Неверный тип данных)");
                    scanner.next();
                    print("Новая попытка: ");
                }
                y = scanner.nextLong();
            }
        } else {
            if (!scanner.hasNextLong()) {
                return null;
            }
            y = scanner.nextLong();
        }

        ZonedDateTime creationDate = ZonedDateTime.now();

        Integer studentsCount;
        if (!isScriptMode) {
            print("Введите кол-во студентов: ");
            while (scanner.hasNextInt() == false) {
                println("Неверный тип данных)");
                scanner.next();
                print("Новая попытка: ");
            }
            studentsCount = scanner.nextInt();
            while (studentsCount <= 0 || studentsCount == null) {
                println("Некорректный ввод) Значение поля должно быть больше 0, Поле не может быть null");
                print("Новая попытка: ");
                while (scanner.hasNextInt() == false) {
                    println("Неверный тип данных)");
                    scanner.next();
                    print("Новая попытка: ");
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
            print("Введите кол-во отчисленных студентов: ");
            while (scanner.hasNextLong() == false) {
                println("Неверный тип данных)");
                scanner.next();
                print("Новая попытка: ");
            }
            expelledStudents = scanner.nextLong();
            while (expelledStudents <= 0) {
                println("Некорректный ввод) Значение поля должно быть больше 0");
                print("Новая попытка: ");
                while (scanner.hasNextLong() == false) {
                    println("Неверный тип данных)");
                    scanner.next();
                    print("Новая попытка: ");
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
            print("Введите кол-во студентов, которые должны быть отчислены: ");
            while (scanner.hasNextLong() == false) {
                println("Неверный тип данных)");
                scanner.next();
                print("Новая попытка: ");
            }
            shouldBeExpelled = scanner.nextLong();
            while (shouldBeExpelled <= 0) {
                println("Некорректный ввод) Значение поля должно быть больше 0");
                print("Новая попытка: ");
                while (scanner.hasNextLong() == false) {
                    println("Неверный тип данных)");
                    scanner.next();
                    print("Новая попытка: ");
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
            println("Введите форму обучения (0-3): ");
            println("0 - ПРОПУСТИТЬ ВВОД ФОРМЫ ОБУЧЕНИЯ");
            println("1 - ЗАОЧНОЕ");
            println("2 - ОЧНОЕ");
            println("3 - ВЕЧЕРНЕЕ");

            while (isChoiseCorrect == false) {
                if (!scanner.hasNextInt()) {
                    println("Неверный тип данных");
                    scanner.next();
                } else {
                    choice = scanner.nextInt();
                    if (choice < 0 || choice > 3) {
                        println("0-3 :)");
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
            print("Будете ли вводить старосту группы (Y/N): ");
            groupAdminYN = scanner.nextLine();
            while (!groupAdminYN.equalsIgnoreCase("Y") & !groupAdminYN.equalsIgnoreCase("N")) {
                print("Будете ли вводить старосту группы (Y/N): ");
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
                print("Введите имя старосты: ");
                nameOfGroupAdmin = scanner.nextLine();
                while (nameOfGroupAdmin == null || nameOfGroupAdmin.equals("")) {
                    println("Некорректный ввод) Поле не может быть null, Строка не может быть пустой.");
                    print("Новая попытка: ");
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
                print("Введите вес старосты: ");
                while (scanner.hasNextDouble() == false) {
                    println("Неверный тип данных)");
                    scanner.next();
                    print("Новая попытка: ");
                }
                weightOfGroupAdmin = scanner.nextDouble();
                scanner.nextLine();
                while (weightOfGroupAdmin <= 0) {
                    println("Некорректный ввод) Значение поля должно быть больше 0");
                    print("Новая попытка: ");
                    while (scanner.hasNextDouble() == false) {
                        println("Неверный тип данных)");
                        scanner.next();
                        print("Новая попытка: ");
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
                println("Выберите цвет глаз старосты (0-3): ");
                println("0 - ПРОПУСТИТЬ ВВОД ЦВЕТА ГЛАЗ");
                println("1 - ЗЕЛЕНЫЕ");
                println("2 - ЖЕЛТЫЕ");
                println("3 - КАРИЕ");
                while (isChoiseCorrect == false) {
                    if (!scanner.hasNextInt()) {
                        println("Неверный тип данных");
                        scanner.next();
                    } else {
                        choice = scanner.nextInt();
                        if (choice < 0 || choice > 3) {
                            println("0-3 :)");
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
                println("Выберите цвет волос старосты (0-4): ");
                println("0 - ПРОПУСТИТЬ ВВОД ЦВЕТА ВОЛОС");
                println("1 - ЧЕРНЫЕ");
                println("2 - СИНИЕ");
                println("3 - БЕЛЫЕ");
                println("4 - РУСЫЕ");

                while (isChoiseCorrect == false) {
                    if (!scanner.hasNextInt()) {
                        println("Неверный тип данных");
                        scanner.next();
                    } else {
                        choice = scanner.nextInt();
                        if (choice < 0 || choice > 4) {
                            println("0-4 :)");
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

        elem.setId(id);
        elem.setName(name);
        elem.setCoordinates(coordinates);
        elem.setCreationDate(creationDate);
        elem.setStudentsCount(studentsCount);
        elem.setExpelledStudents(expelledStudents);
        elem.setShouldBeExpelled(shouldBeExpelled);
        elem.setFormOfEducation(formOfEducation);
        elem.setGroupAdmin(groupAdmin);
        elem.check();
        isScriptMode = false;
        return elem;

    }

    public void start(Console console, CommandManager commandManager) {
        CommandStatus status = CommandStatus.OK;
        while (status != CommandStatus.EXIT) {
            String[] input = console.read();
            String currentCommand = input[0];
            String[] inputArgs = Arrays.copyOfRange(input, 1, input.length);

//                // TODO
//                //  перенести ввод в main (ввод вывод на клиенте)
//                //  client main 1 строка
//                //  валидация отдельно от команд (шаблоны проектирования или solid)
//                //  переписать под maven

            if (commandManager.getCommands().containsKey(currentCommand)) {
                Command command = commandManager.getCommands().get(currentCommand);
                System.out.println(command.getName());
                switch (command.getCommandType()) {
                    // TODO
                    // WHILE TRUE убрать
                    // фабрика, абстрактная фабрика, попробовать применить :))

                    case NO_ARGS: {
                        if (inputArgs.length != 0) {
                            System.out.println("Invalid command arguments!");
                        } else {
                            status = command.execute(new Request(-1,null,null,null));
                            // FIX
                        }
                        break;
                    }
                    case ONE_ARG: {
                        if (inputArgs.length != 1) {
                            System.out.println("Invalid command arguments!");
                        } else {
                            command.execute(new Request(Integer.parseInt(inputArgs[0]),null,null,null));
                        }
                        System.out.println("1");
                        break;
                    }
                    case ARG_AND_ELEM: {
                        if (inputArgs.length != 1) {
                            System.out.println("Invalid command arguments!");
                        } else {
                            Integer key = null;
                            while (key == null) {
                                try {
                                    key = Integer.parseInt(inputArgs[0]);
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid command arguments!");
                                    break;
                                }
                            }
                            if (key == null) {
                                break;
                            }
                            Scanner scanner = new Scanner(System.in);
                            StudyGroup elem = console.readElement(scanner);
                            Request request = new Request(key,null, scanner, elem);
                            command.execute(request);
                        }
                        System.out.println("2");
                        break;
                    }
                    case FILE: {
                        if (inputArgs.length != 1) {
                            System.out.println("Invalid command arguments!");
                        } else {
                            String fileName = inputArgs[0];
                            Scanner scanner = new Scanner(System.in);
                            Request request = new Request(0,fileName, scanner, null);
                            command.execute(request);
                        }
                    }
                }
            } else {
                console.println("Command " + currentCommand + " not found. Use help for list of commands. :)");
            }
        }
    }
}
