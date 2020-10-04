package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe de testes do DAO do Jogador
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
public class JogadorDAOTest {
    
    private final JogadorDAO dao;
    private Jogador jogadorValido;
    private String login;
    
    public JogadorDAOTest() {
        dao = new JogadorDAO();
        jogadorValido = new Jogador();
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
     * Teste da classe JogadorDAO
     * <p>
     * Os testes deverão ocorrer em ordem para que exista valores válidos no banco.<br>
     * Primeiro teste: testa o método adicionar, criando um objeto válido para testar o método buscar.<br>
     * Segundo teste: testa o método buscar, obtendo um objeto com id válido para testar o método buscarPorId.<br>
     * Terceiro teste: testa o método buscarPorId.<br>
     * Quarto teste: testa o método atualizar.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Test
    public void testeJogadorDAO(){
        System.out.println("Gerando um login único...");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	Date date = new Date(); 
	login = dateFormat.format(date);
        testeAdicionar();
        testeBuscar(jogadorValido);
        testeBuscarPorId(jogadorValido.getId());
        testeAtualizar();
    }

    /**
     * Teste do método adicionar() da classe JogadorDAO
     * <p>
     * Primeiro teste: verifica a resposta obtida ao adicionar um objeto Jogador com valores válidos.<br>
     * Segundo teste: verifica a resposta obtida ao adicionar um objeto Jogador com valores inválidos.<br>
     * Terceiro teste: verifica a resposta obtida ao adicionar um objeto Jogador com login igual.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    public void testeAdicionar() {
        System.out.println("Iniciando primeiro teste de adicionar...");
        System.out.println("Instanciando objeto de teste do tipo Jogador com valores válidos...");
        Jogador jogadorTeste = new Jogador(null, "nome de teste", 0, 0, 0, 0, login, "senha de teste");
        System.out.println("Definindo valor experado (Novo Jogador cadastrado com sucesso!)...");
        String expResult = "Novo Jogador cadastrado com sucesso!";
        System.out.println("Adicionando novo Jogador...");
        String result = dao.adicionar(jogadorTeste);
        System.out.println("Verificando resposta...");
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        jogadorValido = jogadorTeste;
        System.out.println("Iniciando segundo teste de adicionar...");
        System.out.println("Instanciando objeto de teste do tipo Jogador com valores inválidos...");
        jogadorTeste = new Jogador(null, "", -1, -1, -1, -1, "", "");
        System.out.println("Definindo valor experado (Valores Inválidos!)...");
        expResult = "Valores Inválidos!";
        System.out.println("Adicionando novo Jogador...");
        result = dao.adicionar(jogadorTeste);
        System.out.println("Verificando resposta...");
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando terceiro teste de adicionar...");
        System.out.println("Instanciando objeto de teste do tipo Jogador com login igual...");
        jogadorTeste = new Jogador(null, "nome de teste", 0, 0, 0, 0, login, "senha de teste");
        System.out.println("Definindo valor experado (Login em uso!)...");
        expResult = "Login em uso!";
        System.out.println("Adicionando novo Jogador...");
        result = dao.adicionar(jogadorTeste);
        System.out.println("Verificando resposta...");
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
    }

    /**
     * Teste do método atualizar() da classe JogadorDAO
     * <p>
     * Primeiro teste: verifica a resposta obtida ao atualizar um objeto Jogador com valores válidos.<br>
     * Segundo teste: verifica a resposta obtida ao atualizar um objeto Jogador com valores inválidos.<br>
     * Terceiro teste: verifica a resposta obtida ao atualizar um objeto Jogador com id não cadastrado.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    public void testeAtualizar() {
        System.out.println("Iniciando primeiro teste de atualizar...");
        System.out.println("Instanciando objeto de teste do tipo Jogador com valores válidos...");
        jogadorValido.setNome("nome alterado");
        System.out.println("Definindo valor experado (Jogador alterado com sucesso!)...");
        String expResult = "Jogador alterado com sucesso!";
        System.out.println("Atualizando Jogador...");
        String result = dao.atualizar(jogadorValido);
        System.out.println("Verificando resposta...");
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando segundo teste de atualizar...");
        System.out.println("Instanciando objeto de teste do tipo Jogador com valores inválidos...");
        Integer id = jogadorValido.getId();
        Jogador jogadorTeste = new Jogador(id, "", -1, -1, -1, -1, "", "");
        System.out.println("Definindo valor experado (Valores Inválidos!)...");
        expResult = "Valores Inválidos!";
        System.out.println("Atualizando Jogador...");
        result = dao.atualizar(jogadorTeste);
        System.out.println("Verificando resposta...");
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando terceiro teste de atualizar...");
        System.out.println("Instanciando objeto de teste do tipo Jogador com id não cadastrado...");
        id++;
        jogadorTeste = new Jogador(id, "nome de teste", 0, 0, 0, 0, login + "a", "senha de teste");
        System.out.println("Definindo valor experado (Jogador não encontrado!)...");
        expResult = "Jogador não encontrado!";
        System.out.println("Atualizando Jogador...");
        result = dao.atualizar(jogadorTeste);
        System.out.println("Verificando resposta...");
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
    }

    /**
     * Teste do método buscarPorId() da classe JogadorDAO
     * <p>
     * Primeiro teste: verifica o objeto obtido ao buscar um objeto Jogador com id válido.<br>
     * Segundo teste: verifica o objeto obtido ao buscar um objeto Jogador com id inválido.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param idValido Integer: id válido para realizar o teste
     */
    public void testeBuscarPorId(Integer idValido) {
        System.out.println("Iniciando primeiro teste de buscarPorId...");
        System.out.println("Buscando objeto de teste do tipo Jogador com id válido...");
        Jogador jogadorTeste = dao.buscarPorId(idValido);
        System.out.println("Definindo valor experado (true)...");
        Boolean expResult = true;
        System.out.println("Verificando Jogador...");
        Boolean result = true;
        if (jogadorTeste == null) {
            result = false;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando segundo teste de buscarPorId...");
        System.out.println("Buscando objeto de teste do tipo Jogador com id inválido...");
        jogadorTeste = dao.buscarPorId(0);
        System.out.println("Definindo valor experado (false)...");
        expResult = false;
        System.out.println("Verificando Jogador...");
        result = true;
        if (jogadorTeste == null) {
            result = false;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
    }

    /**
     * Teste do método buscar() da classe JogadorDAO
     * <p>
     * Primeiro teste: verifica o objeto obtido ao buscar um objeto Jogador com valores válido.<br>
     * Segundo teste: verifica o objeto obtido ao buscar um objeto Jogador com valores inválido.<br>
     * Terceiro teste: verifica o objeto obtido ao buscar um objeto Jogador com login e senha incorretos.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param jogadorTeste Jogador: objeto jogador válido para realizar o teste
     */
    public void testeBuscar(Jogador jogadorTeste) {
        System.out.println("Iniciando primeiro teste de buscar...");
        System.out.println("Buscando objeto de teste do tipo Jogador com valores válidos...");
        Jogador jogadorEncontrado = dao.buscar(jogadorTeste);
        System.out.println("Definindo valor experado (true)...");
        Boolean expResult = true;
        System.out.println("Verificando Jogador...");
        Boolean result = true;
        if (jogadorEncontrado == null) {
            result = false;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        jogadorValido = jogadorEncontrado;
        System.out.println("Iniciando segundo teste de buscar...");
        System.out.println("Buscando objeto de teste do tipo Jogador com valores inválidos...");
        Jogador novoJogadorTeste = new Jogador(null, "", -1, -1, -1, -1, "", "");
        jogadorEncontrado = dao.buscar(novoJogadorTeste);
        System.out.println("Definindo valor experado (false)...");
        expResult = false;
        System.out.println("Verificando Jogador...");
        result = true;
        if (jogadorEncontrado == null) {
            result = false;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
        System.out.println("Iniciando terceiro teste de buscar...");
        System.out.println("Buscando objeto de teste do tipo Jogador com login e senha incorretos...");
        jogadorTeste.setLogin("login desconhecido");
        jogadorTeste.setSenha("senha incorreta");
        jogadorEncontrado = dao.buscar(jogadorTeste);
        System.out.println("Definindo valor experado (false)...");
        expResult = false;
        System.out.println("Verificando Jogador...");
        result = true;
        if (jogadorEncontrado == null) {
            result = false;
        }
        assertEquals(expResult, result);
        System.out.println("Resultado: " + result);
    }
}