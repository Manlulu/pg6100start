package pg6100.entity;

import org.junit.Before;
import org.junit.Test;
import pg6100.ejb.TestBase;
import pg6100.ejb.UserEJB;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;


public class UserDataTest extends TestBase {

    private Validator validator;

    @Before
    public void init() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void testFirstNameMaxLetters() throws Exception {
        String firstName = "akakakaka akakakaka akakakaka aka";
        assertEquals(firstName.length(), 33);

        UserData userData = new UserData();
        userData.setUsername("Username");
        userData.setHash("Hash");
        userData.setSalt("Salt");
        userData.setFirstName(firstName);
        Set<ConstraintViolation<UserData>> violations = validator.validate(userData);
        assertEquals(1, violations.size());


        userData.setFirstName("first");
        violations = validator.validate(userData);
        assertEquals(0, violations.size());
    }

    @Test
    public void testLastNameMaxLetters() throws Exception {
        String lastName = "akakakaka akakakaka akakakaka aka";
        assertEquals(lastName.length(), 33);

        UserData userData = new UserData();
        userData.setUsername("Username");
        userData.setHash("Hash");
        userData.setSalt("Salt");
        userData.setLastName(lastName);
        Set<ConstraintViolation<UserData>> violations = validator.validate(userData);

        assertEquals(1, violations.size());

        userData.setLastName("last");
        violations = validator.validate(userData);
        assertEquals(0, violations.size());
    }

    @Test
    public void testUsernameCanNotBeNull() throws Exception {
        UserData userData = new UserData();
        userData.setUsername(null);
        userData.setHash("Hash");
        userData.setSalt("Salt");

        Set<ConstraintViolation<UserData>> violations = validator.validate(userData);

        assertEquals(1, violations.size());
    }



    private void printViolations(Set<ConstraintViolation<UserData>> violations) throws Exception {
        System.out.println("Violations: ");
        for (ConstraintViolation<UserData> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}