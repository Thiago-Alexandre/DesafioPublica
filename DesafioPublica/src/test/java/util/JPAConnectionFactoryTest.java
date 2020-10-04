package util;

import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe de teste da classe JPAConnectionFactory
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
public class JPAConnectionFactoryTest{
    
    public JPAConnectionFactoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Teste do Método de retorno de conexão com o banco
     * <p>
     * Verifica se o objeto de conexão com o banco é retornado nulo ou não.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Test
    public void testGetEntityManager() {
        System.out.println("Iniciando teste de retorno de conexão com o banco...");
        System.out.println("Instanciando objeto de conexão com o banco...");
        EntityManager em = JPAConnectionFactory.getEntityManager();
        System.out.println("Definindo valor experado (true)...");
        Boolean expResult = true;
        System.out.println("Verificando o objeto de conexão...");
        Boolean result = false;
        if (em != null) {
            result = true;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
    }
}