package maks.kulykov.model;

public interface DatabaseManager {
    String connection();

    String getTablesList();

    String checkCredentials(String dbName, String userName, String password);
}
