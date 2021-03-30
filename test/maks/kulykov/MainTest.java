package maks.kulykov;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainTest {

    DatabaseManager manager;

    @Before
    public void createDbManager() {
        manager = new DatabaseManager();
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
        String response = manager.getTablesList();

        assertEquals("[users]", response);
    }
}
