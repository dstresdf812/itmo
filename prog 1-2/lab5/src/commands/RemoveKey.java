package commands;

import managers.CommandManager;

import java.util.Arrays;

public class RemoveKey extends Command {
    private final CommandManager commandManager;
    public RemoveKey(CommandManager commandManager) {
        super("remove_key", "удалить элемент из коллекции по его ключу");
        this.commandManager = commandManager;
    }

    public void execute(String[] args) {
        System.out.println("asdasdasdasd");
        System.out.println(Arrays.stream(args).toList());
    }
}
