package maks.kulykov.model;

import java.sql.*;
import java.util.Arrays;

public class JDBCDatabaseManager implements DatabaseManager {
    private final String db = "mydb";
    private final String userName = "postgres";
    private final String password = "postgres";
    private Connection conn;

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
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "%", new String[] { "TABLE" });
            String[] tableList = new String[100];
            int index = 0;
            while (rs.next()) {
                tableList[index++] = rs.getString("TABLE_NAME");
            }
            tableList = Arrays.copyOf(tableList, index, String[].class);
            rs.close();
            return tableList;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            return new String[0];
        }
    }

    @Override
    public String[] getTableHeaders(String tableName) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.columns " +
                    "WHERE table_schema = 'sqlcmd' AND table_name = '" + tableName + "'");
            String[] tables = new String[100];
            int index = 0;
            while (rs.next()) {
                tables[index++] = rs.getString("column_name");
            }
            tables = Arrays.copyOf(tables, index, String[].class);
            rs.close();
            stmt.close();
            return tables;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            return new String[0];
        }
    }
}
