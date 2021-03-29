package maks.kulykov;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private final String db = "mydb";
    private final String userName = "postgres";
    private final String password = "postgres";

    public String checkCredentials(String cmdDb, String cmdUserName, String cmdPassword) {
        String wrongCredential = "";
        if (!cmdDb.equals(db)) {
            wrongCredential =  "db";
        } else if (!cmdUserName.equals(userName)) {
            wrongCredential = "username";
        } else if (!cmdPassword.equals(password)) {
            wrongCredential = "password";
        }
        return wrongCredential;
    }

    public String connection() {
        String responseMessage = "";
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/" + db, userName, password)) {
            if (conn != null) {
                responseMessage = "Connected to the database!";
            } else {
                responseMessage = "Failed to make connection!";
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseMessage;
    }
}
