package maks.kulykov;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainTest {

    @Test
    public void testWrongDBName() {
        DatabaseManager manager;
        manager = new DatabaseManager("wrongDBName", "postgres", "postgres");

        String response = manager.checkCredentials();

        assertEquals("db", response);
    }

    @Test
    public void testWrongUserName() {
        DatabaseManager manager;
        manager = new DatabaseManager("mydb", "wrongUserName", "postgres");

        String response = manager.checkCredentials();

        assertEquals("username", response);
    }

    @Test
    public void testWrongPassword() {
        DatabaseManager manager;
        manager = new DatabaseManager("mydb", "postgres", "wrongPassword");

        String response = manager.checkCredentials();

        assertEquals("password", response);
    }
}
