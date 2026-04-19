package com.dstresdf.server.commands;

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
    public static final int max_stack = 5;
    public Map<String, Integer> stack = new HashMap<>();
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

    public void incStack(String scriptName) {
        stack.put(scriptName, stack.getOrDefault(scriptName, 0) + 1);
    }

    public void decStack(String scriptName) {
        stack.put(scriptName, stack.getOrDefault(scriptName, 0) - 1);
    }

    public boolean checkStack(String scriptName) {
        if (stack.containsKey(scriptName)) {
            return stack.get(scriptName) >= max_stack;
        }
        return false;

    }
}
