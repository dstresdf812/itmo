package com.dstresdf.server.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Управление командами.
 * @author dmitrij
 */
public class CommandManager {
    private final Map<String,Command> commands = new ConcurrentHashMap<>();
    private Map<String, List<String>> commandHistory = new ConcurrentHashMap<>();
    public static final int max_stack = 5;
    public Map<String, Integer> stack = new ConcurrentHashMap<>();
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

    public void addToHistory(Command command, String client) {
        List<String> tmp = commandHistory.getOrDefault(client, new ArrayList<>());
        tmp.add(command.getName());
        commandHistory.put(client, tmp);
    }
    public List<String> getCommandHistory(String client) {
        List<String> commands = commandHistory.getOrDefault(client, new ArrayList<>());
        return commands.subList((commands.toArray().length - 8) < 0 ? 0 : commands.toArray().length - 8, commands.toArray().length);
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
