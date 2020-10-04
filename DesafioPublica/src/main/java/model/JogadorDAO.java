package model;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import util.JPAConnectionFactory;

/**
 * Classe para realizar a persistência de dados do Jogador
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
public class JogadorDAO implements Serializable{

    /** 
     * Construtor Vazio para classe JogadorDAO
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    public JogadorDAO() {}
    
    /**
     * Método para realizar a inserção de dados do jogador no banco
     * <p>
     * Deverá ser realizada a validação dos dados do Jogador e, 
     * caso não for encontrado erros (validacao.getErros().isEmpty()), 
     * o jogador poderá ser salvo. Senão, será impedido de realizar a inserção.<br>
     * Deverá ser realizada a verificação do jogador, que impedirá que dois jogadores apresentem o mesmo login.
     * Caso a verificação for false, significa que não foi encontrado outro jogador com o mesmo login e, 
     * assim, o novo jogador poderá ser salvo. Senão, será impedido de realizar a inserção.<br>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * A inserção (em.persist()) deverá ser realizada após o início de uma transação (em.getTransaction().begin()).<br>
     * Esse método só será efetuado no banco após o comando de commit (em.getTransaction().commit()).<br>
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param jogador Jogador: objeto do tipo Jogador contendo os dados para a inserção.
     * @return String: mensagem de sucesso ao inserir ou de falha caso algum erro seja encontrado.
     */
    public String adicionar(Jogador jogador){
        String mensagem = "";
        Validacao validacao = new Validacao(Jogador.class,jogador);
        if (validacao.getErros().isEmpty()){
            if (!verificar(jogador)){
                try{
                    EntityManager em = JPAConnectionFactory.getEntityManager();
                    try{
                        em.getTransaction().begin();
                        em.persist(jogador);
                        em.getTransaction().commit();
                        mensagem = "Novo Jogador cadastrado com sucesso!";    
                    } catch(Exception e){
                        mensagem = "Erro ao adicionar o novo jogador!";
                        System.out.println("Erro! " + e);
                    }  finally{
                        em.close();
                    }
                } catch(Exception e){
                    mensagem = "Erro na conexão com o banco! ";
                    System.out.println("Erro! " + e);
                }    
            } else{
                mensagem = "Login em uso!";
            }
        } else{
            mensagem = "Valores Inválidos!";
        }
        return mensagem;
    }
    
    /**
     * Método para realizar a alteração de dados do jogador no banco
     * <p>
     * Deverá ser realizada a validação dos dados do Jogador e, 
     * caso não for encontrado erros (validacao.getErros().isEmpty()), 
     * o jogador poderá ser alterado. Senão, será impedido de realizar a alteração.<br>
     * Deverá ser realizada a consulta se o jogador já está inserido no banco, pois o processo de merge 
     * pode tanto alterar quanto inserir.
     * Caso nenhum jogador seja encontrado, o processo será finalizado.<br>
     * Caso o login do jogador não foi alterado, a alteração poderá ser realizada.
     * Senão, deverá ser realizada a verificação do jogador, que impedirá que dois jogadores apresentem o mesmo login.
     * Caso a verificação for false, significa que não foi encontrado outro jogador com o mesmo login e, 
     * assim, o novo jogador poderá ser alterado. Senão, será impedido de realizar a alteração.<br>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * A alteração (em.merge()) deverá ser realizada após o início de uma transação (em.getTransaction().begin()).<br>
     * Esse método só será efetuado no banco após o comando de commit (em.getTransaction().commit()).<br>
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param jogador Jogador: objeto do tipo Jogador contendo os dados para a alteração.
     * @return String: mensagem de sucesso ao alterar ou de falha caso algum erro seja encontrado.
     */
    public String atualizar(Jogador jogador) {
        String mensagem = "";
        Validacao validacao = new Validacao(Jogador.class,jogador);
        if (validacao.getErros().isEmpty()){
            Jogador antigo = buscarPorId(jogador.getId());
            if (antigo != null) {
                if (!antigo.getLogin().equals(jogador.getLogin())) {
                    if (verificar(jogador)){
                        return "Login em uso!";
                    }
                }
                try{
                    EntityManager em = JPAConnectionFactory.getEntityManager();
                    try{
                        em.getTransaction().begin();
                        em.merge(jogador);
                        em.getTransaction().commit();
                        mensagem = "Jogador alterado com sucesso!";
                    } catch(Exception e){
                        mensagem = "Erro ao alterar o jogador!";
                        System.out.println("Erro! " + e);
                    } finally{
                        em.close();
                    }
                } catch(Exception e){
                    mensagem = "Erro na conexão com o banco!";
                    System.out.println("Erro! " + e);
                }
            } else{
                mensagem = "Jogador não encontrado!";
            }
        } else{
            mensagem = "Valores Inválidos!";
        }
        return mensagem;
    }
    
    /**
     * Método para verificar se existe algum jogador com o mesmo login
     * <p>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * CriteriaQuery Jogador query = em.getCriteriaBuilder().createQuery(Jogador.class): cria uma consulta para a entidade Jogador.<br>
     * Root Jogador root = query.from(Jogador.class): define a raiz da consulta sendo a entidade Jogador.<br>
     * Path String  loginPath = root. String get("login"): indica sobre qual atributo da entidade será realizada uma condicional.
     * Neste caso, será sobre o login.<br>
     * Predicate verificaLogin = em.getCriteriaBuilder().equal(loginPath, jogador.getLogin()): cria uma condicional para a consulta.
     * Neste caso, será consultado os jogadores que apresentarem o login igual ao informado.<br>
     * query.where(verificaLogin): adiciona a condicional na consulta.<br>
     * TypedQuery Jogador typed = em.createQuery(query): realiza a consulta e retorna uma lista com tipo Jogador.<br>
     * Jogador teste = typed.getSingleResult(): pega um possível resultado da consulta.
     * Caso nenhum resultado for encontrado, será lançada uma exceção, finalizando o processo e retornando valor false.
     * Caso for encontrado algum resultado, finaliza o processo e retorna valor true.
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @param jogador Jogador: novo jogador que será inserido
     * @return Boolean: retorna false caso não for encontrado outro jogador 
     * com o mesmo login do jogador informado ou retorna true caso contrário
     */
    private Boolean verificar(Jogador jogador){
        Boolean loginIgual = false;
        try {
            EntityManager em = JPAConnectionFactory.getEntityManager();
            try {
                CriteriaQuery<Jogador> query = em.getCriteriaBuilder().createQuery(Jogador.class);
                Root<Jogador> root = query.from(Jogador.class);
                Path<String> loginPath = root.<String>get("login");
                Predicate verificaLogin = em.getCriteriaBuilder().equal(loginPath, jogador.getLogin());
                query.where(verificaLogin);
                TypedQuery<Jogador> typed = em.createQuery(query);
                Jogador teste = typed.getSingleResult();
                if (teste != null) {
                    loginIgual = true;
                }
            } catch (Exception e){
                System.out.println("Erro! " + e);
            } finally{
                em.close();
            }
        } catch(Exception e){
            System.out.println("Erro! " + e);
        }
        return loginIgual;
    }
    
    /**
     * Método para realizar a pesquisa de dados de um jogador específico no banco com base no Id
     * <p>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * TypedQuery Jogador query = em.createQuery(jpql, Jogador.class): cria uma consulta JPQL<br>
     * query.setParameter("pId", id): adiciona parâmetro (id) na consulta, evitando assim SQL Injection.<br>
     * jogador = typed.getSingleResult(): pega um possível resultado da consulta.<br>
     * Caso nenhum resultado for encontrado, será lançada uma exceção, finalizando o processo e retornando um objeto nulo. 
     * Caso for encontrado algum resultado, finaliza o processo e retorna o jogador encontrado.<br>
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param id Integer: Identificação do jogador que será pesquisado no banco.
     * @return Jogador: retorna o objeto do tipo jogador com os dados caso for encontrado
     * ou um objeto nulo caso não for encontrado nenhum jogador com o Id informado.
     */
    public Jogador buscarPorId(Integer id) {
        Jogador jogador = null;
        try {
            EntityManager em = JPAConnectionFactory.getEntityManager();
            String jpql = "select j from Jogador j where j.id = :pId";
            TypedQuery<Jogador> query = em.createQuery(jpql, Jogador.class);
            query.setParameter("pId", id);
            try {
                jogador =  query.getSingleResult();
            } catch (Exception e) {
                System.out.println("Erro! " + e);
            } finally{
                em.close();
            }
        } catch(Exception e){
            System.out.println("Erro! " + e);
        }
        return jogador;
    }
    
    /**
     * Método para realizar a pesquisa de dados de um jogador específico no banco com base no login e senha
     * <p>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * CriteriaQuery Jogador query = em.getCriteriaBuilder().createQuery(Jogador.class): cria uma consulta para a entidade Jogador.<br>
     * Root Jogador root = query.from(Jogador.class): define a raiz da consulta sendo a entidade Jogador.<br>
     * Path String  loginPath = root. String get("login"): indica sobre qual atributo da entidade será realizada uma condicional (login).<br>
     * Path String  senhaPath = root. String get("senha"): indica sobre qual atributo da entidade será realizada uma condicional (senha).<br>
     * Predicate verificaLogin = em.getCriteriaBuilder().equal(loginPath, jogador.getLogin()): cria uma condicional para a consulta 
     * onde será verificado os jogadores que apresentarem um login igual ao informado.<br>
     * Predicate verificaSenha = em.getCriteriaBuilder().equal(senhaPath, jogador.getSenha()): cria uma condicional para a consulta 
     * onde será verificado os jogadores que apresentarem uma senha igual à informada.<br>
     * query.where(verificaLogin, verificaSenha): adiciona as condicionais na consulta, 
     * onde será verificado os jogadores que apresentarem ambas as condicionais.<br>
     * TypedQuery Jogador typed = em.createQuery(query): realiza a consulta e retorna uma lista com tipo Jogador.<br>
     * jogador = typed.getSingleResult(): pega um possível resultado da consulta.
     * Caso nenhum resultado for encontrado, será lançada uma exceção, finalizando o processo e retornando um objeto nulo.
     * Caso for encontrado algum resultado, finaliza o processo e retorna o jogador encontrado.
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param jogador Jogador: jogador que será pesquisado no banco.
     * @return Jogador: retorna o objeto do tipo jogador com os dados caso for encontrado
     * ou um objeto nulo caso não for encontrado nenhum jogador com o login e senha informados.
     */
    public Jogador buscar(Jogador jogador){
        Jogador retorno = null;
        Validacao validacao = new Validacao(Jogador.class,jogador);
        if (validacao.getErros().isEmpty()){
            try {
                EntityManager em = JPAConnectionFactory.getEntityManager();
                try {
                    CriteriaQuery<Jogador> query = em.getCriteriaBuilder().createQuery(Jogador.class);
                    Root<Jogador> root = query.from(Jogador.class);
                    Path<String> loginPath = root.<String>get("login");
                    Path<String> senhaPath = root.<String>get("senha");
                    Predicate verificaLogin = em.getCriteriaBuilder().equal(loginPath, jogador.getLogin());
                    Predicate verificaSenha = em.getCriteriaBuilder().equal(senhaPath, jogador.getSenha());
                    query.where(verificaLogin, verificaSenha);
                    TypedQuery<Jogador> typed = em.createQuery(query);
                    retorno = typed.getSingleResult();
                } catch (Exception e){
                    System.out.println("Erro! " + e);
                } finally{
                    em.close();
                }
            } catch(Exception e){
                System.out.println("Erro! " + e);
            }
        }
        return retorno;
    }
}