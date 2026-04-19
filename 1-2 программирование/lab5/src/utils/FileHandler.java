package utils;

import commands.Command;
import managers.Console;
import other.CommandStatus;
import other.Request;

import java.util.Scanner;

public class FileHandler extends CommandHandler {
    public FileHandler(Console console) {
        super(console);
    }

    @Override
    public CommandStatus handle(Command command, String[] inputArgs) {
        CommandStatus status = CommandStatus.ERROR;

        if (inputArgs.length != 1) {
            System.out.println("Invalid command arguments!");
        } else {
            String fileName = inputArgs[0];
            Scanner scanner = new Scanner(System.in);
            Request request = new Request(0,fileName, scanner, null);
            status = command.execute(request);
        }
        return status;
    }
}
