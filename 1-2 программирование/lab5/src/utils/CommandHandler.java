package utils;

import commands.Command;
import managers.Console;
import other.CommandStatus;

abstract public class CommandHandler {
    protected Console console;

    public CommandHandler(Console console) {
        this.console = console;
    }

    abstract public CommandStatus handle(Command command, String[] inputArgs);
}
