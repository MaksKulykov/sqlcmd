package maks.kulykov.model;

import java.sql.*;
import java.util.*;

public class JDBCDatabaseManager implements DatabaseManager {
    private final String db = "mydb";
    private final String userName = "postgres";
    private final String password = "postgres";
    private Connection conn;
    private List<String> columnNames;

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
    public String openConnection() {
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
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    @Override
    public List<String> getTablesList() {
        List<String> tables = new ArrayList<>();
        ResultSet rs = null;
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "%", new String[] { "TABLE" });
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
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
    public List<String> getTableHeaders(String tableName) {
        List<String> headers = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM information_schema.columns " +
                    "WHERE table_schema = 'sqlcmd' AND table_name = '" + tableName + "'");
            while (rs.next()) {
                headers.add(rs.getString("column_name"));
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
        columnNames = headers;
        return headers;
    }

    @Override
    public Set<Map<String, String>> getTableData(String tableName) {
        Set<Map<String, String>> tableData = new HashSet<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM sqlcmd." + tableName);
            while (rs.next()) {
                Map<String, String> data = new HashMap<>();
                for (String columnName : columnNames) {
                    data.put(columnName, rs.getString(columnName));
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
