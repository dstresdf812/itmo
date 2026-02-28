package managers;

import commands.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Управление командами.
 * @author dmitrij
 */
public class CommandManager {
    private final Map<String,Command> commands = new HashMap<>();
    private ArrayList<String> commandHistory = new ArrayList<>();
    public static int stack = 0;
    public static final int max_stack = 5;
    /**
     * Добавление новой команды.
     * @param CommandName
     * @param command
     */
    public void add(String CommandName, Command command) {
        commands.put(CommandName, command);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public void addToHistory(Command command) {
        commandHistory.add(command.getName());
    }
    public List<String> getCommandHistory() {
        return commandHistory.subList((commandHistory.toArray().length - 8) < 0 ? 0 : commandHistory.toArray().length - 8, commandHistory.toArray().length);
    }


}
