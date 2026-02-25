package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import other.StudyGroup;

public class AverageOfStudentsCount extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    static final int argsLen = 0;
    public AverageOfStudentsCount(Console console, CollectionManager collectionManager, CommandManager commandManager) {
        super("average_of_students_count","вывести среднее значение поля studentsCount для всех элементов коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    public boolean execute(String[] args) {
        commandManager.addToHistory(this);
        float totalStudentsCount = 0;
        for (StudyGroup studyGroup : collectionManager.collection.values()) {
            int curStudentsCount = studyGroup.getStudentsCount();
            totalStudentsCount += curStudentsCount;
        }
        System.out.println(totalStudentsCount/collectionManager.collection.size());
        return true;
    }

    public int getArgsLen() {
        return argsLen;
    }
}
