package pg6100.ejb;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class TestBase {

    private static EJBContainer ec;
    private static Context ctx;

    private static UserEJB queriesEJB;


    @BeforeClass
    public static void setUp() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put(EJBContainer.MODULES, new File("target/classes"));
        ec = EJBContainer.createEJBContainer(properties);
        ctx = ec.getContext();

        queriesEJB = getEJB(UserEJB.class);
    }

    protected static <T> T getEJB(Class<T> klass) {
        try {
            return (T) ctx.lookup("java:global/classes/" + klass.getSimpleName() + "!" + klass.getName());
        } catch (NamingException e) {
            return null;
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        ec.close();
        ctx.close();
    }

    @After
    public void emptyDatabase() {
        //this is quicker than re-initialize the whole DB / EJB container
        queriesEJB.deleteAll();
    }
}