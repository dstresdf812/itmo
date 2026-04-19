package com.dstresdf.client.commands;

public enum CommandList {
    AverageOfStudentsCount("average_of_students_count", ArgumentType.NO_ARGS),
    Clear("clear",ArgumentType.NO_ARGS),
    Help("help",ArgumentType.NO_ARGS),
    Info("info",ArgumentType.NO_ARGS),
    PrintFieldAscendingExpelledStudents("print_field_ascending_expelled_students",ArgumentType.NO_ARGS),
    PrintUniqueStudentsCount("print_unique_students_count",ArgumentType.NO_ARGS),
    Show("show",ArgumentType.NO_ARGS),
    Exit("exit",ArgumentType.NO_ARGS),
    History("history",ArgumentType.NO_ARGS),

    Insert("insert",ArgumentType.ARG_AND_ELEM),
    Update("update",ArgumentType.ARG_AND_ELEM),
    ReplaceIfGreater("replace_if_greater",ArgumentType.ARG_AND_ELEM),

    RemoveGreaterKey("remove_greater_key",ArgumentType.ONE_ARG),
    Execute_script("execute_script",ArgumentType.ONE_ARG);

    private final String commandName;
    private final ArgumentType arg;
    CommandList(String commandName, ArgumentType arg) {
        this.commandName = commandName;
        this.arg = arg;
    }

    public String getCommandName() {
        return commandName;
    }
    public ArgumentType getArgumentType() {
        return arg;
    }

    public static CommandList getCommand(String string) {
        for (CommandList command : CommandList.values()) {
            if (command.getCommandName().equals(string)) {
                return command;
            }
        }
        return null;
    }
}
