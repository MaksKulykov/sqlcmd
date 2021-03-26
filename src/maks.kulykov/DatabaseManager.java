package maks.kulykov;

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
}
