package maks.kulykov.view;

import java.util.Scanner;

public class Console implements View {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }
}
