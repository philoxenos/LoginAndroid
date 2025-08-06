package com.psatraining.loginandroid;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void userModel_useStringId() {
        UserModel user = new UserModel();
        String testId = "abc123";
        
        user.setId(testId);
        assertEquals(testId, user.getId());
        
        // Verify that the ID field is a String, not an int
        assertNotNull(user.getId());
        assertTrue(user.getId() instanceof String);
    }

    @Test
    public void userModel_constructorWithoutId() {
        String userName = "testuser";
        String userEmail = "test@example.com";
        String userPassword = "password123";
        String birthDate = "1990-01-01";
        String description = "Test description";
        long createdAt = System.currentTimeMillis();
        long lastUpdatedAt = System.currentTimeMillis();

        UserModel user = new UserModel(userName, userEmail, userPassword, birthDate, description, createdAt, lastUpdatedAt);
        
        assertEquals(userName, user.getUserName());
        assertEquals(userEmail, user.getUserEmail());
        assertEquals(userPassword, user.getUserPassword());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals(description, user.getDescription());
        assertEquals(createdAt, user.getCreatedAt());
        assertEquals(lastUpdatedAt, user.getLastUpdatedAt());
        
        // ID should be null initially (will be set by json-server)
        assertNull(user.getId());
    }
}