package utils;

import managers.Console;

public abstract class HandlerFactory {
    public abstract CommandHandler createHandler(Console console);
}
