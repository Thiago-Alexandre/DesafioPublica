package model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe de testes do DAO da Temporada
 * <p>
 * Alguns métodos não foram adicionados testes, por haver necessidade de integração com métodos de outras classes!.<br>
 * O objeto temporada necessita de um objeto jogador válido, para assim poder ser adicionado com sucesso e então poder ser consultado no banco.
 * </p>
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
public class TemporadaDAOTest {
    
    private final TemporadaDAO dao;
    
    public TemporadaDAOTest() {
        dao = new TemporadaDAO();
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
     * Teste do método adicionar() da classe TemporadaDAO
     * <p>
     * Este teste não inclui o teste com valores válidos, por haver necessidade de integração com métodos de outras classes!<br>
     * Verifica a resposta obtida ao adicionar um objeto Temporada com valores inválidos.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Test
    public void testAdicionar() {
        System.out.println("Iniciando teste de adicionar...");
        System.out.println("Instanciando objeto de teste do tipo Temporada com valores inválidos...");
        Temporada temporadaTeste = new Temporada(null, "", -1, -1, null);
        System.out.println("Definindo valor experado (Valores Inválidos!)...");
        String expResult = "Valores Inválidos!";
        System.out.println("Adicionando novo Temporada...");
        String result = dao.adicionar(temporadaTeste);
        System.out.println("Verificando resposta...");
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando segundo teste de adicionar...");
    }

    /**
     * Teste do método atualizar() da classe TemporadaDAO
     * <p>
     * Este teste não inclui o teste com valores válidos, por haver necessidade de integração com métodos de outras classes!<br>
     * Verifica a resposta obtida ao atualizar um objeto Temporada com valores inválidos.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Test
    public void testAtualizar() {
        System.out.println("Iniciando teste de atualizar...");
        System.out.println("Instanciando objeto de teste do tipo Temporada com valores inválidos...");
        Temporada temporadaTeste = new Temporada(null, "", -1, -1, null);
        System.out.println("Definindo valor experado (Valores Inválidos!)...");
        String expResult = "Valores Inválidos!";
        System.out.println("Atualizando Temporada...");
        String result = dao.atualizar(temporadaTeste);
        System.out.println("Verificando resposta...");
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
    }
}