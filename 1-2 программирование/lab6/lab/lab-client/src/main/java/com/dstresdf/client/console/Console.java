package com.dstresdf.client.console;

import java.util.Scanner;

/**
 * Ввод/вывод данных.
 *
 * @author dmitrij
 */
public class Console {
    private Scanner scanner = new Scanner(System.in);

    public void print(Object o) {
        System.out.print(o);
    }

    public void println(Object o) {
        System.out.println(o);
    }

    public String[] read() {
        print("Введите команду: ");
        String s;
        try {
            s = scanner.nextLine().trim();
        } catch (Exception e) {
            scanner = new Scanner(System.in);
            return null;
        }
        if (s.isEmpty()) {
            return null;
        }
        return s.split(" ");
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
