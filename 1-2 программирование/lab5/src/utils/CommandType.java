package utils;

public enum CommandType {
    NO_ARGS(new NoArgsHandlerFactory()),
    ONE_ARG(new OneArgHandlerFactory()),
    ARG_AND_ELEM(new ArgAndElemHandlerFactory()),
    FILE(new FileHandlerFactory());

    private final HandlerFactory factory;

    CommandType(HandlerFactory factory) {
        this.factory = factory;
    }

    public HandlerFactory getFactory() {
        return factory;
    }
}
