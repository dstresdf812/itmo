package managers;

import commands.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CommandManager {
    private final Map<String,Command> commands = new HashMap<>();
    private ArrayList<Command> commandHistory = new ArrayList<>();
    public void add(String CommandName, Command command) {
        commands.put(CommandName, command);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public void addToHistory(Command command) {
        commandHistory.add(command);
    }
    public List<Command> getCommandHistory() {
        return commandHistory.subList(commandHistory.toArray().length - 8, commandHistory.toArray().length);
    }
}
