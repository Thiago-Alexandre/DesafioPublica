package model;

import java.util.Calendar;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe de testes do DAO do Jogo
 * <p>
 * Alguns métodos não foram adicionados testes, por haver necessidade de integração com métodos de outras classes!.<br>
 * O objeto Jogo necessita de um objeto Temporada válido, para assim poder ser adicionado com sucesso e então poder ser consultado no banco.
 * </p>
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
public class JogoDAOTest {
    
    private final JogoDAO dao;
    
    public JogoDAOTest() {
        dao = new JogoDAO();
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
     * Teste do método adicionar() da classe JogoDAO
     * <p>
     * Este teste não inclui o teste com valores válidos e com datas iguais, por haver necessidade de integração com métodos de outras classes!<br>
     * Este teste não inclui o teste dos métodos atualizarRecordesJogador e atualizarTemporada!<br>
     * Primeiro teste: verifica a resposta obtida ao adicionar um objeto Jogo com valores inválidos.<br>
     * Segundo teste: verifica a resposta obtida ao adicionar um objeto Jogo com data no futuro.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Test
    public void testeAdicionar() {
        Temporada temporada = new Temporada();
        Date date = new Date();
        System.out.println("Iniciando primeiro teste de adicionar...");
        System.out.println("Instanciando objeto de teste do tipo Jogo com valores inválidos...");
        Jogo jogoTeste = new Jogo(null, null, "", -1, null);
        System.out.println("Definindo valor experado (Valores Inválidos!)...");
        String expResult = "Valores Inválidos!";
        System.out.println("Adicionando novo Jogo...");
        String result = dao.adicionar(jogoTeste);
        System.out.println("Verificando resposta...");
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando segundo teste de adicionar...");
        System.out.println("Instanciando objeto de teste do tipo Jogo com data no futuro...");
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(date); 
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        jogoTeste = new Jogo(null, date, "descrição de teste", 0, temporada);
        System.out.println("Definindo valor experado (Valores Inválidos!)...");
        expResult = "Valores Inválidos!";
        System.out.println("Adicionando novo Jogo...");
        result = dao.adicionar(jogoTeste);
        System.out.println("Verificando resposta...");
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
    }
}