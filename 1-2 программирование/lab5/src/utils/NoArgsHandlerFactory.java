package utils;

import managers.Console;

public class NoArgsHandlerFactory extends HandlerFactory {
    @Override
    public CommandHandler createHandler(Console console) {
        return new NoArgsHandler(console);
    }
}
