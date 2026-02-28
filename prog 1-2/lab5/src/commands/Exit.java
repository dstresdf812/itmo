package commands;

/**
 * Команда 'exit'. Завершает программу без сохранения в файл.
 * @author dmitrij
 */
public class Exit extends Command{
    static final int argsLen = 0;
    public Exit() {
        super("exit","завершить программу (без сохранения в файл)");
    }

    /**
     * Выполняет команду
     * @param args
     * @return Выполнена ли комаинда
     */
    public boolean execute(String[] args) {
        System.exit(1);
        return true;
    }

    /**
     * Получить кол-во аргументов комманды
     * @return Кол-во аргументов комманды
     */
    public int getArgsLen() {
        return argsLen;
    }
}
