package maks.kulykov.model;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class JDBCDatabaseManager implements DatabaseManager {
    private final String db = "mydb";
    private final String userName = "postgres";
    private final String password = "postgres";
    private Connection conn;
    private String[] columnNames;

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

    @Override
    public String connection() {
        String responseMessage = "";
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/" + db, userName, password);
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

    @Override
    public String[] getTablesList() {
        String[] tables = new String[100];
        ResultSet rs = null;
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "%", new String[] { "TABLE" });
            int index = 0;
            while (rs.next()) {
                tables[index++] = rs.getString("TABLE_NAME");
            }
            tables = Arrays.copyOf(tables, index, String[].class);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            }
        }
        return tables;
    }

    @Override
    public String[] getTableHeaders(String tableName) {
        String[] headers = new String[100];
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM information_schema.columns " +
                    "WHERE table_schema = 'sqlcmd' AND table_name = '" + tableName + "'");
            int index = 0;
            while (rs.next()) {
                headers[index++] = rs.getString("column_name");
            }
            headers = Arrays.copyOf(headers, index, String[].class);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            }
        }
        columnNames = headers;
        return headers;
    }

    @Override
    public Set<HashMap<String, String>> getTableData(String tableName) {
        Set<HashMap<String, String>> tableData = new HashSet<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM sqlcmd." + tableName);
            while (rs.next()) {
                HashMap<String, String> data = new HashMap<>();
                for (int i = 0; i < columnNames.length; i++) {
                    data.put(columnNames[i], rs.getString(i + 1));
                }
                tableData.add(data);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            }
        }
        return tableData;
    }
}
