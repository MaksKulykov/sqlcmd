package maks.kulykov.controller;

import maks.kulykov.model.DatabaseManager;
import maks.kulykov.view.Console;

import java.util.*;

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
            String[] commandData = new String[2];
            if (command.startsWith("find|")) {
                commandData = command.split("\\|");
                command = commandData[0];
            }

            switch (command) {
                case "help" -> {
                    view.write("Command list:");
                    view.write("list - show list of all tables");
                    view.write("find|tableName - show table data");
                    view.write("help - show list of all command");
                    view.write("exit - exit program");
                    command = view.read();
                }
                case "list" -> {
                    printTablesList(manager.getTablesList());
                    command = view.read();
                }
                case "exit" -> {
                    view.write("See you!");
                    isExit = true;
                }
                case "find" -> {
                    ArrayList<String> columnNames = manager.getTableHeaders(commandData[1]);
                    int columns = columnNames.size();
                    printLine(columns);
                    printHeader(columnNames);
                    printLine(columns);

                    Set<HashMap<String, String>> tableData = manager.getTableData(commandData[1]);
                    for (HashMap<String, String> data : tableData) {
                        printTableData(data, columnNames);
                    }
                    printLine(columns);
                    command = view.read();
                }
                default -> {
                    view.write("There is no such command. Try to enter command again or use 'help' for a hint.");
                    command = view.read();
                }
            }
        }
    }

    private void printTablesList(ArrayList<String> tablesList) {
        String result = "[";

        if (tablesList.size() > 0) {
            for (String table : tablesList) {
                result += table + ", ";
            }
            result = result.substring(0, result.length() - 2);
        }
        result += "]";

        view.write(result);
    }

    private void printHeader(ArrayList<String> tableColumns) {
        String result = "| ";

        for (String tableColumn : tableColumns) {
            result = result + formatString(tableColumn);
        }

        view.write(result);
    }

    private void printTableData(HashMap<String, String> data, ArrayList<String> columnNames) {
        String result = "| ";

        for (int i = 0; i < columnNames.size(); i++) {
            result += formatString(data.get(columnNames.get(i)));
        }

        view.write(result);
    }

    private String formatString(String string) {
        String result = "";

        if (string.length() > 10) {
            result = string.substring(0,7) + "... | ";
        } else if (string.length() < 10) {
            result = String.format("%-10s", string) + " | ";
        } else {
            result = string + " | ";
        }

        return result;
    }

    private void printLine(int columns) {
        String line = "+";

        for (int i = 0; i < columns; i++) {
            line = line + "------------+";
        }

        view.write(line);
    }
}
