package model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe de testes da classe Temporada
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
public class TemporadaTest {
    
    public TemporadaTest() {
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
     * Teste do método atualizarPontuacao da classe Temporada
     * <p>
     * Primeiro teste: verifica se há uma nova pontuação máxima.<br>
     * Segundo teste: verifica se há uma nova pontuação mínima.<br>
     * Terceiro teste: verifica se não há mudança na pontuação.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Test
    public void testeAtualizarPontuacao() {
        Jogador jogador = new Jogador();
        System.out.println("Iniciando primeiro teste do método atualizarPontuacao()...");
        System.out.println("Instanciando objeto de teste do tipo Temporada atribuindo valores fictícios de pontuação mínima (10) e máxima (10)...");
        Temporada temporadaTeste = new Temporada(null,"nome",10,10,jogador);
        System.out.println("Instanciando objeto de teste do tipo Jogo...");
        Jogo jogoTeste = new Jogo();
        System.out.println("Atribuindo valor fictício de placar (15)...");
        jogoTeste.setPlacar(15);
        System.out.println("Definindo valor experado (true)...");
        Boolean expResult = true;
        System.out.println("Obtendo resultado...");
        Boolean result = temporadaTeste.atualizarPontuacao(jogoTeste);
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando segundo teste do método atualizarPontuacao()...");
        System.out.println("Instanciando objeto de teste do tipo Temporada atribuindo valores fictícios de pontuação mínima (10) e máxima (10)...");
        temporadaTeste = new Temporada(null,"nome",10,10,jogador);
        System.out.println("Instanciando novo objeto de teste do tipo Jogo...");
        jogoTeste = new Jogo();
        System.out.println("Atribuindo valor fictício de placar (5)...");
        jogoTeste.setPlacar(5);
        System.out.println("Definindo valor experado (true)...");
        expResult = true;
        System.out.println("Obtendo resultado...");
        result = temporadaTeste.atualizarPontuacao(jogoTeste);
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando terceiro teste do método atualizarPontuacao()...");
        System.out.println("Instanciando objeto de teste do tipo Temporada atribuindo valores fictícios de pontuação mínima (10) e máxima (10)...");
        temporadaTeste = new Temporada(null,"nome",10,10,jogador);
        System.out.println("Instanciando objeto de teste do tipo Jogo...");
        jogoTeste = new Jogo();
        System.out.println("Atribuindo valor fictício de placar (10)...");
        jogoTeste.setPlacar(10);
        System.out.println("Definindo valor experado (false)...");
        expResult = false;
        System.out.println("Obtendo resultado...");
        result = temporadaTeste.atualizarPontuacao(jogoTeste);
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
    }
}