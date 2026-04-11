package com.dstresdf.common.commands;

import java.io.Serializable;

public enum CommandType implements Serializable {
    HELP("help"),
    INFO("info"),
    SHOW("show"),
    INSERT("insert"),
    UPDATE("update"),
    REMOVE_KEY("remove_key"),
    CLEAR("clear"),
    REPLACE_IF_GREATER("replace_if_greater"),
    REMOVE_GREATER_KEY("remove_greater_key"),
    AVERAGE_OF_STUDENTS_COUNT("average_of_students_count"),
    PRINT_UNIQUE_STUDENTS_COUNT("print_unique_students_count"),
    PRINT_FIELD_ASCENDING_EXPELLED_STUDENTS("print_field_ascending_students"),;

    private final String name;
    CommandType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
