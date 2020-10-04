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
 * Classe de testes da classe Jogo
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
public class JogoTest {
    
    public JogoTest() {
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
     * Teste de validação da classe Jogo
     * <p>
     * Primeiro teste: verifica a validação do objeto Jogo com valores válidos.<br>
     * Segundo teste: verifica a validação do objeto Jogo com valores inferiores.<br>
     * Terceiro teste: verifica a validação do objeto Jogo com valores nulos.<br>
     * Quarto teste: verifica a validação do objeto Jogo com descrição maior que 100 caracteres.<br>
     * Quinto teste: verifica a validação do objeto Jogo com valores vazios.<br>
     * Sexto teste: verifica a validação do objeto Jogo com placar superior.<br>
     * Sétimo teste: verifica a validação do objeto Jogo com data superior.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Test
    public void testeValidacaoJogo(){
        Date data = new Date();
        Temporada temporada = new Temporada();
        System.out.println("Iniciando primeiro teste de validação...");
        System.out.println("Instanciando objeto de teste do tipo Jogo com valores válidos...");
        Jogo jogoTeste = new Jogo(null, data, "descrição de teste", 0, temporada);
        System.out.println("Definindo valor experado (false)...");
        Boolean expResult = false;
        System.out.println("Instanciando objeto de validação para o tipo Jogo...");
        Validacao validacao = new Validacao(Jogo.class, jogoTeste);
        System.out.println("Verificando a quantidade de erros na validação...");
        Boolean result = true;
        if (validacao.getErros().isEmpty()) {
            result = false;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando segundo teste de validação...");
        System.out.println("Instanciando objeto de teste do tipo Jogo com valores inferiores...");
        jogoTeste = new Jogo(0, data, "descrição de teste", -1, temporada);
        System.out.println("Definindo valor experado (true)...");
        expResult = true;
        System.out.println("Instanciando objeto de validação para o tipo Jogo...");
        validacao = new Validacao(Jogo.class, jogoTeste);
        System.out.println("Verificando a quantidade de erros na validação...");
        result = true;
        if (validacao.getErros().isEmpty()) {
            result = false;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando terceiro teste de validação...");
        System.out.println("Instanciando objeto de teste do tipo Jogo com valores nulos...");
        jogoTeste = new Jogo(null, null, null, null, null);
        System.out.println("Definindo valor experado (true)...");
        expResult = true;
        System.out.println("Instanciando objeto de validação para o tipo Jogo...");
        validacao = new Validacao(Jogo.class, jogoTeste);
        System.out.println("Verificando a quantidade de erros na validação...");
        result = true;
        if (validacao.getErros().isEmpty()) {
            result = false;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando quarto teste de validação...");
        System.out.println("Instanciando objeto de teste do tipo Jogo com descrição maior que 250 caracteres...");
        String texto251Caracteres = "||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||";
        texto251Caracteres += "||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||";
        texto251Caracteres += "|||||||||||||||||||||||||||||||||||||||||||||||||||";
        jogoTeste = new Jogo(null, data, texto251Caracteres, 1, temporada);
        System.out.println("Definindo valor experado (true)...");
        expResult = true;
        System.out.println("Instanciando objeto de validação para o tipo Jogo...");
        validacao = new Validacao(Jogo.class, jogoTeste);
        System.out.println("Verificando a quantidade de erros na validação...");
        result = true;
        if (validacao.getErros().isEmpty()) {
            result = false;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando quinto teste de validação...");
        System.out.println("Instanciando objeto de teste do tipo Jogo com valores vazios...");
        jogoTeste = new Jogo(null, data, "", 1, temporada);
        System.out.println("Definindo valor experado (true)...");
        expResult = true;
        System.out.println("Instanciando objeto de validação para o tipo Jogo...");
        validacao = new Validacao(Jogo.class, jogoTeste);
        System.out.println("Verificando a quantidade de erros na validação...");
        result = true;
        if (validacao.getErros().isEmpty()) {
            result = false;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando sexto teste de validação...");
        System.out.println("Instanciando objeto de teste do tipo Jogo com placar superior...");
        jogoTeste = new Jogo(null, data, "teste", 1001, temporada);
        System.out.println("Definindo valor experado (true)...");
        expResult = true;
        System.out.println("Instanciando objeto de validação para o tipo Jogo...");
        validacao = new Validacao(Jogo.class, jogoTeste);
        System.out.println("Verificando a quantidade de erros na validação...");
        result = true;
        if (validacao.getErros().isEmpty()) {
            result = false;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando sétimo teste de validação...");
        System.out.println("Instanciando objeto de teste do tipo Jogo com data superior...");
        Calendar cal = Calendar.getInstance(); 
        cal.add(Calendar.DATE, 1);
        data = cal.getTime();
        jogoTeste = new Jogo(null, data, "teste", 1, temporada);
        System.out.println("Definindo valor experado (true)...");
        expResult = true;
        System.out.println("Instanciando objeto de validação para o tipo Jogo...");
        validacao = new Validacao(Jogo.class, jogoTeste);
        System.out.println("Verificando a quantidade de erros na validação...");
        result = true;
        if (validacao.getErros().isEmpty()) {
            result = false;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
    }

    /**
     * Teste do método sobrescrito compareTo da classe Jogo
     * <p>
     * Primeiro teste: verifica se um jogo apresenta placar menor que o outro.<br>
     * Segundo teste: verifica se um jogo apresenta placar maior que o outro.<br>
     * Terceiro teste: verifica se um jogo apresenta placar igual ao outro.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Test
    public void testeCompareTo() {
        System.out.println("Iniciando primeiro teste do método compareTo()...");
        System.out.println("Instanciando o primeiro objeto de teste do tipo Jogo atribuindo valor fictício de placar (5)...");
        Jogo jogo1 = new Jogo();
        jogo1.setPlacar(5);
        System.out.println("Instanciando o segundo objeto de teste do tipo Jogo atribuindo valor fictício de placar (10)...");
        Jogo jogo2 = new Jogo();
        jogo2.setPlacar(10);
        System.out.println("Definindo valor experado (-1)...");
        int expResult = -1;
        System.out.println("Obtendo resultado...");
        int result = jogo1.compareTo(jogo2);
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando segundo teste do método compareTo()...");
        System.out.println("Instanciando o primeiro objeto de teste do tipo Jogo atribuindo valor fictício de placar (10)...");
        jogo1 = new Jogo();
        jogo1.setPlacar(10);
        System.out.println("Instanciando o segundo objeto de teste do tipo Jogo atribuindo valor fictício de placar (5)...");
        jogo2 = new Jogo();
        jogo2.setPlacar(5);
        System.out.println("Definindo valor experado (1)...");
        expResult = 1;
        System.out.println("Obtendo resultado...");
        result = jogo1.compareTo(jogo2);
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando terceiro teste do método compareTo()...");
        System.out.println("Instanciando o primeiro objeto de teste do tipo Jogo atribuindo valor fictício de placar (5)...");
        jogo1 = new Jogo();
        jogo1.setPlacar(5);
        System.out.println("Instanciando o segundo objeto de teste do tipo Jogo atribuindo valor fictício de placar (5)...");
        jogo2 = new Jogo();
        jogo2.setPlacar(5);
        System.out.println("Definindo valor experado (0)...");
        expResult = 0;
        System.out.println("Obtendo resultado...");
        result = jogo1.compareTo(jogo2);
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
    }
}