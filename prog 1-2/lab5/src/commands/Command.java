package commands;

import managers.CommandManager;

public abstract class Command {
    String name;
    String description;
    public static int argsLen;
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public  String getDescription() {
        return description;
    }

    public abstract void execute(String[] args);
}
