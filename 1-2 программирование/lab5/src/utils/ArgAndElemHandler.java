package utils;

import commands.Command;
import managers.Console;
import other.CommandStatus;
import other.Request;
import other.StudyGroup;

public class ArgAndElemHandler extends CommandHandler {

    public ArgAndElemHandler(Console console) {
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

            StudyGroup elem = console.readElement(console.getScanner());
            Request request = new Request(key,null, console.getScanner(), elem);
            status = command.execute(request);
        }
        return status;
    }
}
