package utils;

import managers.Console;

public class FileHandlerFactory extends HandlerFactory {
    @Override
    public CommandHandler createHandler(Console console) {
        return new FileHandler(console);
    }
}
