package commands;

import managers.CommandManager;
import managers.Console;

public class History extends Command {
    private final Console console;
    private final CommandManager commandManager;

    public History(Console console, CommandManager commandManager) {
        super("history","вывести последние 8 команд (без их аргументов)");
        this.commandManager = commandManager;
        this.console = console;
    }

    public void execute(String[] args) {
        System.out.println(commandManager.getCommandHistory());
        commandManager.addToHistory(this);
    }
}
