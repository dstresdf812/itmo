package managers;
import other.*;

import java.time.ZonedDateTime;
import java.util.Scanner;
public class Console {
    private final Scanner scanner = new Scanner(System.in);
    private final CollectionManager collectionManager = new CollectionManager();
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

    public String[] readArgs() {
        print("Введите аргументы еще раз: ");
        String s = scanner.nextLine();
        String[] input = s.split(" ");
        return input;
    }

    public StudyGroup readElement() {
        StudyGroup elem = new StudyGroup();

        int id = collectionManager.getIncId();

        print("Введите имя: ");
        String name = scanner.nextLine();
        while (name == null || name.equals("")) {
            println("Некорректный ввод) Поле не может быть null, Строка не может быть пустой.");
            print("Новая попытка: ");
            name = scanner.nextLine();
        }

        print("Введите координату x: ");
        while (scanner.hasNextLong() == false) {println("Неверный тип данных)"); scanner.next();print("Новая попытка: ");}
        Long x = scanner.nextLong();
        while (x <= -23 || x == null) {
            println("Некорректный ввод) Значение поля должно быть больше -23, Поле не может быть null");
            print("Новая попытка: ");
            while (scanner.hasNextLong() == false) {println("Неверный тип данных)"); scanner.next();print("Новая попытка: ");}
            x = scanner.nextLong();
        }

        print("Введите координату y: ");
        while (scanner.hasNextLong() == false) {println("Неверный тип данных)"); scanner.next();print("Новая попытка: ");}
        long y = scanner.nextLong();
        while (y > 316) {
            println("Некорректный ввод) Максимальное значение поля: 316");
            print("Новая попытка: ");
            while (scanner.hasNextLong() == false) {println("Неверный тип данных)"); scanner.next();print("Новая попытка: ");}
            y = scanner.nextLong();
        }

        ZonedDateTime creationDate = ZonedDateTime.now();

        print("Введите кол-во студентов: ");
        while (scanner.hasNextInt() == false) {println("Неверный тип данных)"); scanner.next();print("Новая попытка: ");}
        Integer studentsCount = scanner.nextInt();
        while (studentsCount <= 0 || studentsCount == null) {
            println("Некорректный ввод) Значение поля должно быть больше 0, Поле не может быть null");
            print("Новая попытка: ");
            while (scanner.hasNextInt() == false) {println("Неверный тип данных)"); scanner.next();print("Новая попытка: ");}
            studentsCount = scanner.nextInt();
        }

        print("Введите кол-во отчисленных студентов: ");
        while (scanner.hasNextLong() == false) {println("Неверный тип данных)"); scanner.next();print("Новая попытка: ");}
        long expelledStudents  = scanner.nextLong();
        while (expelledStudents <= 0) {
            println("Некорректный ввод) Значение поля должно быть больше 0");
            print("Новая попытка: ");
            while (scanner.hasNextLong() == false) {println("Неверный тип данных)"); scanner.next();print("Новая попытка: ");}
            expelledStudents = scanner.nextLong();
        }

        print("Введите кол-во студентов, которые должны быть отчислены: ");
        while (scanner.hasNextLong() == false) {println("Неверный тип данных)"); scanner.next();print("Новая попытка: ");}
        Long shouldBeExpelled  = scanner.nextLong();
        while (shouldBeExpelled <= 0) {
            println("Некорректный ввод) Значение поля должно быть больше 0");
            print("Новая попытка: ");
            while (scanner.hasNextLong() == false) {println("Неверный тип данных)"); scanner.next();print("Новая попытка: ");}
            shouldBeExpelled = scanner.nextLong();
        }
        println("Введите форму обучения (0-3): ");
        println("0 - ПРОПУСТИТЬ ВВОД ФОРМЫ ОБУЧЕНИЯ");
        println("1 - ЗАОЧНОЕ");
        println("2 - ОЧНОЕ");
        println("3 - ВЕЧЕРНЕЕ");
        FormOfEducation formOfEducation;
        while (scanner.hasNextInt() == false) {println("Неверный тип данных)"); scanner.next();print("Новая попытка: ");}
        int choice = scanner.nextInt();
        while (choice < 0 || choice > 3) {
            println("0-3 :)");
            choice = scanner.nextInt();
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
        }
        print("Будете ли вводить старосту группы (Y/N): ");
        scanner.nextLine();
        String groupAdminYN = scanner.nextLine();
        while (!groupAdminYN.equalsIgnoreCase("Y") & !groupAdminYN.equalsIgnoreCase("N") ) {
            print("Будете ли вводить старосту группы (Y/N): ");
            groupAdminYN = scanner.nextLine();
        }
        Person groupAdmin = new Person();
        if  (groupAdminYN.equalsIgnoreCase("y")) {
            print("Введите имя старосты: ");
            String nameOfGroupAdmin = scanner.nextLine();
            while (nameOfGroupAdmin == null || nameOfGroupAdmin.equals("")) {
                println("Некорректный ввод) Поле не может быть null, Строка не может быть пустой.");
                print("Новая попытка: ");
                nameOfGroupAdmin = scanner.nextLine();
            }

            print("Введите вес старосты: ");
            while (scanner.hasNextDouble() == false) {println("Неверный тип данных)"); scanner.next();print("Новая попытка: ");}
            double weightOfGroupAdmin = scanner.nextDouble();
            scanner.nextLine();
            while (weightOfGroupAdmin <= 0) {
                println("Некорректный ввод) Значение поля должно быть больше 0");
                print("Новая попытка: ");
                while (scanner.hasNextDouble() == false) {println("Неверный тип данных)"); scanner.next();print("Новая попытка: ");}
                weightOfGroupAdmin = scanner.nextDouble();
            }

            println("Выберите цвет глаз старосты (0-3): ");
            println("0 - ПРОПУСТИТЬ ВВОД ЦВЕТА ГЛАЗ");
            println("1 - ЗЕЛЕНЫЕ");
            println("2 - ЖЕЛТЫЕ");
            println("3 - КАРИЕ");
            EyeColor eyeColor = null;
            while (scanner.hasNextInt() == false) {println("Неверный тип данных)"); scanner.next();print("Новая попытка: ");}
            choice = scanner.nextInt();
            scanner.nextLine();
            while (choice < 0 || choice >3) {
                println("0-3 :)");
                choice = scanner.nextInt();
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
            }
            println("Выберите цвет глаз старосты (0-4): ");
            println("0 - ПРОПУСТИТЬ ВВОД ЦВЕТА ВОЛОС");
            println("1 - ЧЕРНЫЕ");
            println("2 - СИНИЕ");
            println("3 - БЕЛЫЕ");
            println("4 - РУСЫЕ");
            HairColor hairColor = null;
            while (scanner.hasNextInt() == false) {println("Неверный тип данных)"); scanner.next();print("Новая попытка: ");}
            choice = scanner.nextInt();
            scanner.nextLine();
            while (choice < 0 || choice > 4) {
                println("0-4 :)");
                choice = scanner.nextInt();
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
        elem.setGroupAdmin(groupAdmin);
        elem.check();
        return elem;

    }
}
