package commands;

import managers.CommandManager;
import managers.Console;

public class History extends Command {
    private final Console console;
    private final CommandManager commandManager;
    static final int argsLen = 0;
    public History(Console console, CommandManager commandManager) {
        super("history","вывести последние 8 команд (без их аргументов)");
        this.commandManager = commandManager;
        this.console = console;
    }

    public boolean execute(String[] args) {
        System.out.println(commandManager.getCommandHistory());
        commandManager.addToHistory(this);
        return true;
    }

    public int getArgsLen() {
        return argsLen;
    }
}
