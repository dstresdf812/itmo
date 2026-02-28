package commands;

import managers.CommandManager;
import managers.Console;

/**
 * Команда 'history'. Выводит последние 8 команд.
 */
public class History extends Command {
    private final Console console;
    private final CommandManager commandManager;
    static final int argsLen = 0;
    public History(Console console, CommandManager commandManager) {
        super("history","вывести последние 8 команд (без их аргументов)");
        this.commandManager = commandManager;
        this.console = console;
    }

    /**
     * Выполняет команду
     * @param args
     * @return Выполнена ли команда
     */
    public boolean execute(String[] args) {
        System.out.println(commandManager.getCommandHistory());
        commandManager.addToHistory(this);
        System.out.println("Команда " + this.name + " выполнена");
        return true;
    }
    /**
     * Получить кол-во аргументов команды
     * @return Кол-во аргументов команды
     */
    public int getArgsLen() {
        return argsLen;
    }
}
