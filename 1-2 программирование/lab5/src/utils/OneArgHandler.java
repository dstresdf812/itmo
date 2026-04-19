package utils;

import commands.Command;
import managers.Console;
import other.CommandStatus;
import other.Request;

public class OneArgHandler extends CommandHandler {
    public OneArgHandler(Console console) {
        super(console);
    }

    @Override
    public CommandStatus handle(Command command, String[] inputArgs) {
        CommandStatus status = CommandStatus.ERROR;
        if (inputArgs.length != 1) {
            System.out.println("Invalid command arguments!");
        } else {
            Integer key = null;
            try {
                key = Integer.parseInt(inputArgs[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid command arguments!");
                return CommandStatus.ERROR;
            }
            if (key == null) {
                return CommandStatus.ERROR;
            }
            status = command.execute(new Request(Integer.parseInt(inputArgs[0]),null,null,null));
        }

        return status;
    }
}
