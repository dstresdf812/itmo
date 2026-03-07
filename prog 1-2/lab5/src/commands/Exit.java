package commands;

import other.CommandStatus;
import other.Request;
import utils.CommandType;

/**
 * Команда 'exit'. Завершает программу без сохранения в файл.
 * @author dmitrij
 */
public class Exit extends Command{
    private static final CommandType commandType = CommandType.NO_ARGS;
    public Exit() {
        super("exit","завершить программу (без сохранения в файл)");
    }

    /**
     * Выполняет команду
     * @param args
     * @return Выполнена ли комаинда
     */
    public CommandStatus execute(Request request) {
        return CommandStatus.EXIT;
    }

    /**
     * Получить кол-во аргументов комманды
     * @return Кол-во аргументов комманды
     */
    public CommandType getCommandType() {
        return commandType;
    }
}
