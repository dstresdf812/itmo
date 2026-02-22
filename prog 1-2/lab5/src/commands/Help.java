package commands;

import managers.CommandManager;
import managers.Console;
public class Help extends Command {
    private final CommandManager commandManager;
    private final Console console;

    public Help(CommandManager commandManager, Console console) {
        super("help", "вывести справку по доступным командам");
        this.commandManager = commandManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        commandManager.addToHistory(this);
        System.out.println("Доступные команды:");
        for (Command command : commandManager.getCommands().values()) {
            console.println(command.getName() + ": " + command.getDescription());
        }
    }
}
