package maks.kulykov.controller;

import maks.kulykov.model.JDBCDatabaseManager;
import maks.kulykov.view.Console;

public class Main {
    public static void main(String[] args) {
        JDBCDatabaseManager manager = new JDBCDatabaseManager();
        Console view = new Console();

        Controller controller = new Controller(manager, view);
        controller.run();
    }
}
