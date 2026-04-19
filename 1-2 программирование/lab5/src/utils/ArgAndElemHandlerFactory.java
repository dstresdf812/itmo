package utils;

import managers.Console;

public class ArgAndElemHandlerFactory extends HandlerFactory {
    @Override
    public CommandHandler createHandler(Console console) {
        return new ArgAndElemHandler(console);
    }
}
