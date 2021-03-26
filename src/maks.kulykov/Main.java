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

        DatabaseManager manager = new DatabaseManager(dbName, userName, password);

        boolean isConnection = false;

        while (!isConnection) {
            switch (manager.checkCredentials()) {
                case "db" -> {
                    System.out.println("There is no database with this name. Please enter correct database:");
                    dbName = reader.readLine();
                    manager = new DatabaseManager(dbName, userName, password);
                }
                case "username" -> {
                    System.out.println("Wrong user name. Please enter correct user name:");
                    userName = reader.readLine();
                    manager = new DatabaseManager(dbName, userName, password);
                }
                case "password" -> {
                    System.out.println("Wrong password. Please enter correct password:");
                    password = reader.readLine();
                    manager = new DatabaseManager(dbName, userName, password);
                }
                default -> {
                    isConnection = true;
                    System.out.println("Connection success!");
                }
            }
        }

        reader.close();

    }
}

 /*   CREATE TABLE sqlcmd.users (
        user_id serial PRIMARY KEY,
        username VARCHAR ( 50 ) UNIQUE NOT NULL,
    password VARCHAR ( 50 ) NOT NULL
);
  */

/*    CREATE SEQUENCE user_id_seq
        START WITH 1
        INCREMENT BY 1
        MINVALUE 1
        NO MAXVALUE
        CACHE 1;
 */