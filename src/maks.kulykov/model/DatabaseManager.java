package maks.kulykov.model;

import java.util.*;

public interface DatabaseManager {
    String openConnection();

    void closeConnection();

    List<String> getTablesList();

    List<String> getTableHeaders(String tableName);

    Set<Map<String, String>> getTableData(String tableName);

    String checkCredentials(String dbName, String userName, String password);
}
