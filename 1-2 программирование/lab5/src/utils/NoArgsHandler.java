package utils;

import commands.Command;
import managers.Console;
import other.CommandStatus;
import other.Request;

public class NoArgsHandler extends CommandHandler {
    public NoArgsHandler(Console console) {
        super(console);
    }

    @Override
    public CommandStatus handle(Command command, String[] inputArgs) {
        CommandStatus status = CommandStatus.ERROR;
        if (inputArgs.length != 0) {
            System.out.println("Invalid command arguments!");
        } else {
            status = command.execute(new Request(-1,null,null,null));
        }
        return status;
    }
}
