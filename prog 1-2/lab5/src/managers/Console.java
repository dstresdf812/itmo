package managers;
import java.util.Arrays;
import java.util.Scanner;
public class Console {
    private final Scanner scanner = new Scanner(System.in);
    public void print(Object o) {
        System.out.print(o);
    }

    public void println(Object o) {
        System.out.println(o);
    }

    public String[] read() {
        print("Введите команду: ");
        String s = scanner.nextLine();
        String[] input = s.split(" ");
        return input;
    }
}
