package pg6100.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({
        @NamedQuery(name = UserData.getUserByUsername, query = "SELECT u FROM UserData u WHERE u.username = :username"),
        @NamedQuery(name = UserData.getUserById, query = "SELECT u FROM UserData u WHERE u.id = :id"),
        @NamedQuery(name = UserData.deleteAll, query = "DELETE FROM UserData"),
        @NamedQuery(name = UserData.getAllUsers, query = "SELECT u FROM UserData u")
})
public class UserData {

    public static final String getUserByUsername = "UserData.getUserByUserName";
    public static final String getUserById = "UserData.getUserById";
    public static final String deleteAll = "UserData.deleteAll";
    public static final String getAllUsers = "UserData.getAllUsers";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String username;
    @NotNull
    private String hash;
    @NotNull
    @Size(max = 26)
    private String salt;
    @Size(max = 32)
    private String firstName;
    @Size(max = 32)
    private String lastName;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
