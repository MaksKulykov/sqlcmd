package maks.kulykov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello!");
        System.out.println("Enter the name of the database you want to work with:");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String dbName = reader.readLine();
        System.out.println("Enter your user name:");
        String userName = reader.readLine();
        System.out.println("Enter your password:");
        String password = reader.readLine();

        DatabaseManager manager = new DatabaseManager();

        boolean isConnection = false;

        while (!isConnection) {
            switch (manager.checkCredentials(dbName, userName, password)) {
                case "db" -> {
                    System.out.println("There is no database with this name. Please enter correct database:");
                    dbName = reader.readLine();
                }
                case "username" -> {
                    System.out.println("Wrong user name. Please enter correct user name:");
                    userName = reader.readLine();
                }
                case "password" -> {
                    System.out.println("Wrong password. Please enter correct password:");
                    password = reader.readLine();
                }
                default -> {
                    isConnection = true;
                }
            }
        }

        System.out.println(manager.connection());

        System.out.println("Enter a command or enter \"help\" to view a list of commands");

        String command = reader.readLine();

        boolean isExit = false;

        while (!isExit) {
            switch (command) {
                case "help" -> {
                    System.out.println("Command list:");
                    System.out.println("list - show list of all tables");
                    System.out.println("help - show list of all command");
                    System.out.println("exit - exit program");
                    command = reader.readLine();
                }
                case "list" -> {
                    System.out.println(manager.getTablesList());
                    command = reader.readLine();
                }
                case "exit" -> {
                    System.out.println("See you!");
                    isExit = true;
                }
                default -> {
                    System.out.println("There is no such command. Try to enter command again or use 'help' for a hint.");
                    command = reader.readLine();
                }
            }
        }

        reader.close();

    }
}
