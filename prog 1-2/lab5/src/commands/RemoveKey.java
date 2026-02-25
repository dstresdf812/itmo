package commands;

import managers.CommandManager;

import java.util.Arrays;

public class RemoveKey extends Command {
    private final CommandManager commandManager;
    static final int argsLen = 1;
    public RemoveKey(CommandManager commandManager) {
        super("remove_key", "удалить элемент из коллекции по его ключу");
        this.commandManager = commandManager;
    }

    public boolean execute(String[] args) {
        System.out.println("asdasdasdasd");
        System.out.println(Arrays.stream(args).toList());
        return true;
    }

    public int getArgsLen() {
        return argsLen;
    }
}
