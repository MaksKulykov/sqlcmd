package maks.kulykov;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private String db = "mydb";
    private String userName = "postgres";
    private String password = "postgres";

    private String cmdDB;
    private String cmdUserName;
    private String cmdPassword;

    public DatabaseManager(String db, String userName, String password) {
        this.cmdDB = db;
        this.cmdUserName = userName;
        this.cmdPassword = password;
    }

    public String checkCredentials() {
        String resp = "";
        if (!cmdDB.equals(db)) {
            resp =  "db";
        } else if (!cmdUserName.equals(userName)) {
            resp = "username";
        } else if (!cmdPassword.equals(password)) {
            resp = "password";
        }
        return resp;
    }

    public void connection() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/" + cmdDB, cmdUserName, cmdPassword)) {
            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
