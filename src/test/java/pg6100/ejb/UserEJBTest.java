package pg6100.ejb;

import org.junit.Before;
import org.junit.Test;
import pg6100.entity.UserData;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.*;


public class UserEJBTest extends TestBase {

    private static UserData userData;

    private UserEJB userEJB;

    @Before
    public void createTestUser() {
        String username = "usernamename";
        String password = "asdf";
        userEJB = getEJB(UserEJB.class);
        userData = userEJB.createUser(username, password);
    }


    @Test
    public void createUser() throws Exception {
        String username = "createdName";
        String password = "createdPass";

        UserData userData = userEJB.createUser(username, password);

        assertNotNull(userData);
    }

    @Test
    public void userSamePasswordIsDifferentHash() throws Exception {
        String username1 = "createdName1";
        String username2 = "createdName2";
        String password = "pass";

        UserData userData1 = userEJB.createUser(username1, password);
        UserData userData2 = userEJB.createUser(username2, password);

        assertNotEquals(userData1.getHash(), userData2.getHash());
    }

    @Test
    public void getHash() throws Exception {
        String hash = userEJB.getHash(userData, "lol");
        assertTrue(hash != null && !hash.isEmpty());
    }

    @Test
    public void getUserByUsername() throws Exception {
        UserData returnedData = userEJB.getUserByUserName(userData.getUsername());
        assertEquals(returnedData.getId(), 1);
        assertTrue(returnedData.getUsername().equals(userData.getUsername()));
    }

    @Test
    public void getUserById() throws Exception {
        UserData returnedData = userEJB.getUserById(userData.getId());
        assertTrue(returnedData.getUsername().equals(userData.getUsername()));
    }

    @Test
    public void deleteAll() throws Exception {
        userEJB.createUser("one more", "time");
        assertEquals(userEJB.getAllUsers().size(), 2);
        userEJB.deleteAll();
        assertEquals(userEJB.getAllUsers().size(), 0);

    }

    @Test
    public void getAllUsers() throws Exception {
        userEJB.createUser("one more", "time");
        assertEquals(userEJB.getAllUsers().size(), 2);
    }

    @Test
    public void computeHash() throws Exception {
        String computedHash = userEJB.computeHash("Some string", "more string");
        assertTrue(computedHash != null && !computedHash.isEmpty());
    }

    @Test
    public void testUsernameCanNotBeEmpty() throws Exception {
        UserData returned = userEJB.createUser("", "pass");

        assertNull(returned);
    }

    @Test
    public void testPasswordCanNotBeNull() throws Exception {
        UserData returned = userEJB.createUser("User", null);

        assertNull(returned);
    }

    @Test
    public void testPasswordCanNotBeEmpty() throws Exception {
        UserData returned = userEJB.createUser("User", "");

        assertNull(returned);
    }
}