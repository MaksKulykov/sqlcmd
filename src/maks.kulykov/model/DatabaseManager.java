package maks.kulykov.model;

import java.util.HashMap;
import java.util.Set;

public interface DatabaseManager {
    String connection();

    String[] getTablesList();

    String[] getTableHeaders(String tableName);

    Set<HashMap<String, String>> getTableData(String tableName);

    String checkCredentials(String dbName, String userName, String password);
}
