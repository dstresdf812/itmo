package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;

public class ExecuteScript extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    public ExecuteScript(Console console, CollectionManager collectionManager,  CommandManager commandManager) {
        super("execute_script", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        this.console = console;
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    public void execute(String[] args) {
        commandManager.addToHistory(this);
    }
}
