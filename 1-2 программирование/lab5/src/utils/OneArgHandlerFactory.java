package utils;

import managers.Console;

public class OneArgHandlerFactory extends HandlerFactory {
    @Override
    public CommandHandler createHandler(Console console) {
        return new OneArgHandler(console);
    }
}
