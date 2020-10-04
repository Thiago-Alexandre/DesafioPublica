package model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe de testes da classe Jogador
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
public class JogadorTest {
    
    public JogadorTest() {
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
     * Teste de validação da classe Jogador
     * <p>
     * Primeiro teste: verifica a validação do objeto Jogador com valores válidos.<br>
     * Segundo teste: verifica a validação do objeto Jogador com valores inferiores.<br>
     * Terceiro teste: verifica a validação do objeto Jogador com valores nulos.<br>
     * Quarto teste: verifica a validação do objeto Jogador com valores superiores.<br>
     * Quinto teste: verifica a validação do objeto Jogador com valores vazios.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Test
    public void testeValidacaoJogador(){
        System.out.println("Iniciando primeiro teste de validação...");
        System.out.println("Instanciando objeto de teste do tipo Jogador com valores válidos...");
        Jogador jogadorTeste = new Jogador(null, "nome de teste", 0, 0, 0, 0, "login de teste", "senha de teste");
        System.out.println("Definindo valor experado (false)...");
        Boolean expResult = false;
        System.out.println("Instanciando objeto de validação para o tipo Jogador...");
        Validacao validacao = new Validacao(Jogador.class, jogadorTeste);
        System.out.println("Verificando a quantidade de erros na validação...");
        Boolean result = true;
        if (validacao.getErros().isEmpty()) {
            result = false;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando segundo teste de validação...");
        System.out.println("Instanciando objeto de teste do tipo Jogador com valores inferiores...");
        jogadorTeste = new Jogador(0, "nome", -1, -1, -1, -1, "login", "senha");
        System.out.println("Definindo valor experado (true)...");
        expResult = true;
        System.out.println("Instanciando objeto de validação para o tipo Jogador...");
        validacao = new Validacao(Jogador.class, jogadorTeste);
        System.out.println("Verificando a quantidade de erros na validação...");
        result = true;
        if (validacao.getErros().isEmpty()) {
            result = false;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando terceiro teste de validação...");
        System.out.println("Instanciando objeto de teste do tipo Jogador com valores nulos...");
        jogadorTeste = new Jogador(null, null, null, null, null, null, null, null);
        System.out.println("Definindo valor experado (true)...");
        expResult = true;
        System.out.println("Instanciando objeto de validação para o tipo Jogador...");
        validacao = new Validacao(Jogador.class, jogadorTeste);
        System.out.println("Verificando a quantidade de erros na validação...");
        result = true;
        if (validacao.getErros().isEmpty()) {
            result = false;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando quarto teste de validação...");
        System.out.println("Instanciando objeto de teste do tipo Jogador com valores superiores...");
        String texto51Caracteres = "|||||||||||||||||||||||||||||||||||||||||||||||||||";
        jogadorTeste = new Jogador(null, texto51Caracteres, 0, 0, 0, 0, texto51Caracteres, texto51Caracteres);
        System.out.println("Definindo valor experado (true)...");
        expResult = true;
        System.out.println("Instanciando objeto de validação para o tipo Jogador...");
        validacao = new Validacao(Jogador.class, jogadorTeste);
        System.out.println("Verificando a quantidade de erros na validação...");
        result = true;
        if (validacao.getErros().isEmpty()) {
            result = false;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando quinto teste de validação...");
        System.out.println("Instanciando objeto de teste do tipo Jogador com valores vazios...");
        jogadorTeste = new Jogador(null, "", 0, 0, 0, 0, "", "");
        System.out.println("Definindo valor experado (true)...");
        expResult = true;
        System.out.println("Instanciando objeto de validação para o tipo Jogador...");
        validacao = new Validacao(Jogador.class, jogadorTeste);
        System.out.println("Verificando a quantidade de erros na validação...");
        result = true;
        if (validacao.getErros().isEmpty()) {
            result = false;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
    }

    /**
     * Teste do método atualizarRecordes da classe Jogador
     * <p>
     * Primeiro teste: verifica se há um novo recorde de pontuação máxima.<br>
     * Segundo teste: verifica se há um novo recorde de pontuação mínima.<br>
     * Terceiro teste: verifica se não há um novo recorde de pontuação.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Test
    public void testeAtualizarRecordes() {
        System.out.println("Iniciando primeiro teste do método atualizarRecordes()...");
        System.out.println("Instanciando objeto de teste do tipo Jogador atribuindo valores fictícios de pontuação mínima (10) e máxima (10)...");
        Jogador jogadorTeste = new Jogador(null,"nome",10,10,0,0,"login","senha");
        System.out.println("Instanciando objeto de teste do tipo Jogo...");
        Jogo jogoTeste = new Jogo();
        System.out.println("Atribuindo valor fictício de placar (15)...");
        jogoTeste.setPlacar(15);
        System.out.println("Definindo valor experado (true)...");
        Boolean expResult = true;
        System.out.println("Obtendo resultado...");
        Boolean result = jogadorTeste.atualizarRecordes(jogoTeste);
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando segundo teste do método atualizarRecordes()...");
        System.out.println("Instanciando objeto de teste do tipo Jogador atribuindo valores fictícios de pontuação mínima (10) e máxima (10)...");
        jogadorTeste = new Jogador(null,"nome",10,10,0,0,"login","senha");
        System.out.println("Instanciando novo objeto de teste do tipo Jogo...");
        jogoTeste = new Jogo();
        System.out.println("Atribuindo valor fictício de placar (5)...");
        jogoTeste.setPlacar(5);
        System.out.println("Definindo valor experado (true)...");
        expResult = true;
        System.out.println("Obtendo resultado...");
        result = jogadorTeste.atualizarRecordes(jogoTeste);
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando terceiro teste do método atualizarRecordes()...");
        System.out.println("Instanciando objeto de teste do tipo Jogador atribuindo valores fictícios de pontuação mínima (10) e máxima (10)...");
        jogadorTeste = new Jogador(null,"nome",10,10,0,0,"login","senha");
        System.out.println("Instanciando objeto de teste do tipo Jogo...");
        jogoTeste = new Jogo();
        System.out.println("Atribuindo valor fictício de placar (10)...");
        jogoTeste.setPlacar(10);
        System.out.println("Definindo valor experado (false)...");
        expResult = false;
        System.out.println("Obtendo resultado...");
        result = jogadorTeste.atualizarRecordes(jogoTeste);
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
    }
}