package org.example;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    private UserManager userManager;
    private bankClient client;
    private String testUsername = "testUser";
    private String testPassword = "testPassword";

    @Before
    public void setUp() {
        userManager = new UserManager();
        client = new bankClient(testUsername, testPassword, 1000.0, 200.0);
    }

    @Test
    public void testRegisterUser() {
        userManager.registerUser(testUsername, testPassword);
        assertNotNull(userManager.loginUser(testUsername, testPassword));
    }

    @Test
    public void testLoginUser() {
        userManager.registerUser(testUsername, testPassword);
        bankClient loggedInUser = userManager.loginUser(testUsername, testPassword);
        assertNotNull(loggedInUser);
        assertEquals(testUsername, loggedInUser.getUsername());
    }

    @Test
    public void testLoginUserWithInvalidCredentials() {
        userManager.registerUser(testUsername, testPassword);
        bankClient loggedInUser = userManager.loginUser(testUsername, "wrongPassword");
        assertNull(loggedInUser);
    }

    @Test
    public void testDeleteUser() {
        userManager.registerUser(testUsername, testPassword);
        boolean isDeleted = userManager.deleteUser(testUsername, testPassword);
        assertTrue(isDeleted);
        assertNull(userManager.loginUser(testUsername, testPassword));
    }

    @Test
    public void testDeleteUserWithInvalidPassword() {
        userManager.registerUser(testUsername, testPassword);
        boolean isDeleted = userManager.deleteUser(testUsername, "wrongPassword");
        assertFalse(isDeleted);
    }

    @Test
    public void testWithdraw() {
        client.withdraw(300.0);
        assertEquals(700.0, client.getBalance(), 0.01);
        assertEquals(500.0, client.getWallet(), 0.01);
    }

    @Test
    public void testAddTransaction() {
        client.deposit(100.0);
        assertEquals(1, client.transactionHistory.size());
    }

    @Test
    public void testCheckPassword() {
        assertTrue(client.checkPassword("testPassword"));
        assertFalse(client.checkPassword("wrongPassword"));
    }
}

