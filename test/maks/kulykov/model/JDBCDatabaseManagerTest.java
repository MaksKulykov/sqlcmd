package maks.kulykov.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JDBCDatabaseManagerTest {

    JDBCDatabaseManager manager;

    @Before
    public void createDbManager() {
        manager = new JDBCDatabaseManager();
    }

    @Test
    public void testWrongDbName() {
        String response = manager.checkCredentials("wrongDBName", "postgres", "postgres");

        assertEquals("db", response);
    }

    @Test
    public void testWrongUserName() {
        String response = manager.checkCredentials("mydb", "wrongUserName", "postgres");

        assertEquals("username", response);
    }

    @Test
    public void testWrongPassword() {
        String response = manager.checkCredentials("mydb", "postgres", "wrongPassword");

        assertEquals("password", response);
    }

    @Test
    public void testGetTablesList() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("users");

        manager.openConnection();
        List<String> response = manager.getTablesList();

        assertEquals(expectedList, response);
    }
}
