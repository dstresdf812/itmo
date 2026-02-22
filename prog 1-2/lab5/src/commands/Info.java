package commands;

import managers.Console;
import managers.CollectionManager;

import java.util.Date;

public class Info extends Command {
    private final Console console;
    private final  CollectionManager collectionManager;
    public Info(Console console, CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public  void execute(String[] args) {
        int size = collectionManager.getLength();
        Date initDate = collectionManager.getInitDate();
        String type = collectionManager.getType();
        console.println("Тип: " + type + "\nДата" +
                " инициализации: " + initDate + "\nРазмер: " + size);
    }
}
