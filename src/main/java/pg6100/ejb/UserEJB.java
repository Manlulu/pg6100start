package pg6100.ejb;

import org.apache.commons.codec.digest.DigestUtils;
import pg6100.entity.UserData;
import pg6100.utils.DbConfig;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

@Stateless
public class UserEJB {

    @PersistenceContext(unitName = DbConfig.dbName)
    private EntityManager entityManager;

    public UserEJB() {

    }

    public UserData createUser(String username, String password) {

        if (password == null || password.isEmpty()) {
            return null;
        }
        if (username == null || username.isEmpty()) {
            return null;
        }
        if (password.length() > 64) {
            return null;
        }
        if (getUserByUserName(username) != null) {
            return null;
        }

        UserData userData = new UserData();

        String hash = getHash(userData, password);

        userData.setHash(hash);
        userData.setUsername(username);
        try {
            entityManager.persist(userData);
            return userData;
        } catch (Exception e) {
            return null;
        }
    }


    public String getHash(UserData userData, String password) {
        String salt = getSalt();
        userData.setSalt(salt);

        return computeHash(password, userData.getSalt());
    }

    @NotNull
    protected String getSalt() {
        SecureRandom random = new SecureRandom();
        int bitsPerChar = 5;
        int twoPowerOfBits = 32; // 2^5
        int n = 26;
        assert n * bitsPerChar >= 128;

        String salt = new BigInteger(n * bitsPerChar, random).toString(twoPowerOfBits);
        return salt;
    }

    public UserData getUserByUserName(String username) {
        try {
            Query findByUsername = entityManager.createNamedQuery(UserData.getUserByUsername, UserData.class).setParameter("username", username);
            return (UserData) findByUsername.getResultList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public UserData getUserById(long id) {
        try {
            Query findById = entityManager.createNamedQuery(UserData.getUserById, UserData.class).setParameter("id", id);
            return (UserData) findById.getResultList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public int deleteAll() {
        return entityManager.createNamedQuery(UserData.deleteAll).executeUpdate();
    }

    public List<UserData> getAllUsers() {
        try {
            Query getAllUsers = entityManager.createNamedQuery(UserData.getAllUsers, UserData.class);
            return getAllUsers.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @NotNull
    public String computeHash(String password, String salt) {
        String combined = password + salt;
        String hash = DigestUtils.sha256Hex(combined);
        return hash;
    }
}
