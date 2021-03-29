package maks.kulykov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

        reader.close();

    }
}
