package maks.kulykov.controller;

import maks.kulykov.model.DatabaseManager;
import maks.kulykov.view.Console;

public class Controller {
    private DatabaseManager manager;
    private Console view;

    Controller(DatabaseManager manager, Console view) {
        this.manager = manager;
        this.view = view;
    }

    public void run() {
        view.write("Hello!");
        view.write("Enter the name of the database you want to work with:");

        String dbName = view.read();
        view.write("Enter your user name:");
        String userName = view.read();
        view.write("Enter your password:");
        String password = view.read();

        boolean isConnection = false;

        while (!isConnection) {
            switch (manager.checkCredentials(dbName, userName, password)) {
                case "db" -> {
                    view.write("There is no database with this name. Please enter correct database:");
                    dbName = view.read();
                }
                case "username" -> {
                    view.write("Wrong user name. Please enter correct user name:");
                    userName = view.read();
                }
                case "password" -> {
                    view.write("Wrong password. Please enter correct password:");
                    password = view.read();
                }
                default -> isConnection = true;
            }
        }

        view.write(manager.connection());

        view.write("Enter a command or enter \"help\" to view a list of commands");

        String command = view.read();

        boolean isExit = false;

        while (!isExit) {
            switch (command) {
                case "help" -> {
                    view.write("Command list:");
                    view.write("list - show list of all tables");
                    view.write("help - show list of all command");
                    view.write("exit - exit program");
                    command = view.read();
                }
                case "list" -> {
                    view.write(manager.getTablesList());
                    command = view.read();
                }
                case "exit" -> {
                    view.write("See you!");
                    isExit = true;
                }
                default -> {
                    view.write("There is no such command. Try to enter command again or use 'help' for a hint.");
                    command = view.read();
                }
            }
        }
    }
}
