package com.dstresdf.common.commands;

import java.io.Serializable;

public enum CommandType implements Serializable {
    AVERAGE_OF_STUDENTS_COUNT("average_of_students_count", ArgumentType.NO_ARGS),
    CLEAR("clear",ArgumentType.NO_ARGS),
    HELP("help",ArgumentType.NO_ARGS),
    INFO("info",ArgumentType.NO_ARGS),
    PRINT_FIELD_ASCENDING_EXPELLED_STUDENTS("print_field_ascending_expelled_students",ArgumentType.NO_ARGS),
    PRINT_UNIQUE_STUDENTS_COUNT("print_unique_students_count",ArgumentType.NO_ARGS),
    SHOW("show",ArgumentType.NO_ARGS),
    EXIT("exit",ArgumentType.NO_ARGS),
    History("history",ArgumentType.NO_ARGS),

    INSERT("insert",ArgumentType.ARG_AND_ELEM),
    UPDATE("update",ArgumentType.ARG_AND_ELEM),
    REPLACE_IF_GREATER("replace_if_greater",ArgumentType.ARG_AND_ELEM),

    REMOVE_GREATER_KEY("remove_greater_key",ArgumentType.ONE_ARG),
    EXECUTE_SCRIPT("execute_script",ArgumentType.ONE_ARG),
    REMOVE_KEY("remove_key", ArgumentType.ONE_ARG),

    LOGIN("login", ArgumentType.ONE_ARG),
    REGISTER("register", ArgumentType.ONE_ARG),
    BUY("buy", ArgumentType.ONE_ARG);

    private final String name;
    private final ArgumentType arg;
    CommandType(String name, ArgumentType arg) {
        this.name = name;
        this.arg = arg;
    }

    public String getName() {
        return name;
    }

    public ArgumentType getArg() {
        return arg;
    }

    public static CommandType getCommand(String string) {
        for (CommandType command : CommandType.values()) {
            if (command.getName().equals(string)) {
                return command;
            }
        }
        return null;
    }

}
