package commands;

import managers.CommandManager;
import managers.Console;

/**
 * Команда 'help'. Выводит список доступных команд.
 * @author dmitrij
 */
public class Help extends Command {
    private final CommandManager commandManager;
    private final Console console;
    static final int argsLen = 0;
    public Help(CommandManager commandManager, Console console) {
        super("help", "вывести справку по доступным командам");
        this.commandManager = commandManager;
        this.console = console;
    }

    /**
     * Выполняет команду
     * @param args
     * @return Выполнена ли команда
     */
    @Override
    public boolean execute(String[] args) {
        commandManager.addToHistory(this);
        System.out.println("Доступные команды:");
        for (Command command : commandManager.getCommands().values()) {
            console.println(command.getName() + ": " + command.getDescription());
        }
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
