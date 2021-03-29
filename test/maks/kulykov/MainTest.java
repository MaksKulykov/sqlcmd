package maks.kulykov;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainTest {

    @Test
    public void testWrongDbName() {
        DatabaseManager manager;
        manager = new DatabaseManager();

        String response = manager.checkCredentials("wrongDBName", "postgres", "postgres");

        assertEquals("db", response);
    }

    @Test
    public void testWrongUserName() {
        DatabaseManager manager;
        manager = new DatabaseManager();

        String response = manager.checkCredentials("mydb", "wrongUserName", "postgres");

        assertEquals("username", response);
    }

    @Test
    public void testWrongPassword() {
        DatabaseManager manager;
        manager = new DatabaseManager();

        String response = manager.checkCredentials("mydb", "postgres", "wrongPassword");

        assertEquals("password", response);
    }
}
