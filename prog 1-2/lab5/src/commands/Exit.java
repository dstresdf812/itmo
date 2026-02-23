package commands;

public class Exit extends Command{
    static final int argsLen = 0;
    public Exit() {
        super("exit","завершить программу (без сохранения в файл)");
    }

    public  void execute(String[] args) {
        System.exit(1);
    }
}
