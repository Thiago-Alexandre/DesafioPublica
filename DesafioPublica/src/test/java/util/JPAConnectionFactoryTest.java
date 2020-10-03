package util;

import javax.persistence.EntityManager;
import junit.framework.TestCase;

/**
 * Classe de teste da classe JPAConnectionFactory.
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
public class JPAConnectionFactoryTest extends TestCase {
    
    public JPAConnectionFactoryTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getEntityManager method, of class JPAConnectionFactory.
     */
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        EntityManager em = JPAConnectionFactory.getEntityManager();
        Boolean expectativa = false;
        Boolean realidade = false;
        if (em == null) {
            realidade = true;
        }
        assertEquals(expectativa, realidade);
    }
}