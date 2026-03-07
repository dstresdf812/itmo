package commands;

import managers.CommandManager;
import managers.Console;
import other.CommandStatus;
import other.Request;
import utils.CommandType;

/**
 * Команда 'help'. Выводит список доступных команд.
 * @author dmitrij
 */
public class Help extends Command {
    private final CommandManager commandManager;
    private final Console console;
    private static final CommandType commandType = CommandType.NO_ARGS;
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
    public CommandStatus execute(Request request) {
        commandManager.addToHistory(this);
        System.out.println("Доступные команды:");
        for (Command command : commandManager.getCommands().values()) {
            console.println(command.getName() + ": " + command.getDescription());
        }
        System.out.println("Команда " + this.name + " выполнена");
        return CommandStatus.OK;
    }
    /**
     * Получить кол-во аргументов команды
     * @return Кол-во аргументов команды
     */
    public CommandType getCommandType() {
        return commandType;
    }
}
