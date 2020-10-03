package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.query.criteria.internal.OrderImpl;
import util.JPAConnectionFactory;

/**
 * Classe para realizar a persistência de dados da Temporada
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
public class TemporadaDAO implements Serializable{
    
    /** 
     * Construtor Vazio para TemporadaDAO
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    public TemporadaDAO() {}

    /**
     * Método para realizar a inserção de dados da Temporada no banco
     * <p>
     * Deverá ser realizada a validação dos dados da Temporada e, 
     * caso não for encontrado erros (validacao.getErros().isEmpty()), 
     * a temporada poderá ser salva. Senão, será impedido de realizar a inserção.<br>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * A inserção (em.persist()) deverá ser realizada após o início de uma transação (em.getTransaction().begin()).<br>
     * Esse método só será efetuado no banco após o comando de commit (em.getTransaction().commit()).<br>
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param temporada Temporada: objeto do tipo Temporada contendo os dados para a inserção.
     * @return String: mensagem de sucesso ao inserir ou de falha caso algum erro seja encontrado.
     */
    public String adicionar(Temporada temporada) {
        String mensagem = "";
        Validacao validacao = new Validacao(Temporada.class,temporada);
        if (validacao.getErros().isEmpty()){
            try{
                EntityManager em = JPAConnectionFactory.getEntityManager();
                try{
                    em.getTransaction().begin();
                    em.persist(temporada);
                    em.getTransaction().commit();
                    mensagem = "Nova Temporada cadastrada com sucesso!";
                } catch(Exception e){
                    mensagem = "Erro ao adicionar a nova temporada!";
                    System.out.println("Erro! " + e);
                } finally{
                    em.close();
                }
            } catch(Exception e){
                mensagem = "Erro na conexão com o banco!";
                System.out.println("Erro! " + e);
            }    
        } else{
            mensagem = "Valores Inválidos!";
        }
        return mensagem;
    }
    
    /**
     * Método para realizar a alteração de dados da temporada no banco
     * <p>
     * Deverá ser realizada a validação dos dados da Temporada e, 
     * caso não for encontrado erros (validacao.getErros().isEmpty()), 
     * a temporada poderá ser alterada. Senão, será impedido de realizar a alteração.<br>
     * Deverá ser realizada a consulta se a temporada já está inserida no banco, pois o processo de merge 
     * pode tanto alterar quanto inserir.
     * Caso nenhuma temporada seja encontrada, o processo será finalizado.<br>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * A alteração (em.merge()) deverá ser realizada após o início de uma transação (em.getTransaction().begin()).<br>
     * Esse método só será efetuado no banco após o comando de commit (em.getTransaction().commit()).<br>
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param temporada Temporada: objeto do tipo Temporada contendo os dados para a alteração.
     * @return String: mensagem de sucesso ao alterar ou de falha caso algum erro seja encontrado.
     */
    public String atualizar(Temporada temporada) {
        String mensagem = "";
        Validacao validacao = new Validacao(Temporada.class,temporada);
        if (validacao.getErros().isEmpty()){
            Temporada antiga = buscarPorId(temporada.getId());
            if (antiga != null) {
                try{
                    EntityManager em = JPAConnectionFactory.getEntityManager();
                    try{
                        em.getTransaction().begin();
                        em.merge(temporada);
                        em.getTransaction().commit();
                        mensagem = "Temporada alterada com sucesso!";
                    } catch(Exception e){
                        mensagem = "Erro ao alterar a temporada!";
                        System.out.println("Erro! " + e);
                    } finally{
                        em.close();
                    }
                } catch(Exception e){
                    mensagem = "Erro na conexão com o banco!";
                    System.out.println("Erro! " + e);
                }    
            } else{
                mensagem = "Temporada não encontrada!";
            }   
        } else{
            mensagem = "Valores Inválidos!";
        }
        return mensagem;
    }
    
    /**
     * Método para realizar a pesquisa de dados de uma temporada específica no banco com base no id
     * <p>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * TypedQuery Temporada query = em.createQuery(jpql, Temporada.class): cria uma consulta JPQL<br>
     * query.setParameter("pId", id): adiciona parâmetro (id) na consulta, evitando assim SQL Injection.<br>
     * temporada = typed.getSingleResult(): pega um possível resultado da consulta.
     * Caso nenhum resultado for encontrado, será lançada uma exceção, finalizando o processo e retornando um objeto nulo.
     * Caso for encontrado algum resultado, finaliza o processo e retorna a temporada encontrada.
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param id Integer: Identificação do temporada que será pesquisado no banco.
     * @return Temporada: retorna o objeto do tipo Temporada com os dados caso for encontrado
     * ou um objeto nulo caso não for encontrado nenhuma temporada com o Id informado.
     */
    public Temporada buscarPorId(Integer id) {
        Temporada temporada = null;
        try{
            EntityManager em = JPAConnectionFactory.getEntityManager();
            String jpql = "select t from Temporada t where t.id = :pId";
            TypedQuery<Temporada> query = em.createQuery(jpql, Temporada.class);
            query.setParameter("pId", id);
            try {
                temporada =  query.getSingleResult();
            } catch (Exception e) {
                System.out.println("Erro! " + e);
            } finally{
                em.close();
            }
        } catch(Exception e){
            System.out.println("Erro! " + e);
        }
        return temporada;
    }
    
    /**
     * Método para realizar a pesquisa de temporadas referentes a um jogador específico no banco
     * <p>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * TypedQuery Temporada query = em.createQuery(jpql, Temporada.class): cria uma consulta JPQL<br>
     * query.setParameter("pJogador", jogador): adiciona parâmetro (jogador) na consulta, evitando assim SQL Injection.<br>
     * lista =  query.getResultList(): retorna uma lista de temporadas do jogador.
     * Caso nenhum resultado for encontrado, será lançada uma exceção, finalizando o processo e retornando um objeto nulo.
     * Caso for encontrado algum resultado, finaliza o processo e retorna a lista encontrada.
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param jogador Jogador: jogador com o qual se deseja obter as temporadas no banco.
     * @return Lista Temporada: retorna uma lista de objetos do tipo Temporada referentes ao jogador caso for encontrado
     * ou uma lista vazia caso não for encontrado nenhuma temporada pelo jogador informado.
     */
    public List<Temporada> listar(Jogador jogador) {
        List<Temporada> lista = null;
        try{
            EntityManager em = JPAConnectionFactory.getEntityManager();
            String jpql = "select t from Temporada t where t.jogador = :pJogador";
            TypedQuery<Temporada> query = em.createQuery(jpql, Temporada.class);
            query.setParameter("pJogador", jogador);
            try {
                lista =  query.getResultList();
            } catch (Exception e) {
                System.out.println("Erro! " + e);
            } finally{
                em.close();
            }
        } catch(Exception e){
            System.out.println("Erro! " + e);
        }
        return lista;
    }
    
    /**
     * Método para realizar a pesquisa das temporadas do jogador de forma filtrada e paginada
     * <p>
     * Necessário para a paginação.<br>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * CriteriaQuery Temporada consulta = pesquisa(filtro): objeto de consulta com base nos filtros.<br>
     * TypedQuery Temporada typed = em.createQuery(consulta): realiza uma consulta e retorna uma lista do tipo Temporada<br>
     * .setFirstResult(filtro.getPrimeiroRegistro()): define o primeiro resultado da consulta para paginação.<br>
     * .setMaxResults(filtro.getQuantidadeRegistros()): define o total de registros por página.<br>
     * lista = typed.getResultList(): pega um possível resultado da consulta.
     * Caso nenhum resultado for encontrado, será lançada uma exceção, finalizando o processo e retornando um objeto nulo.
     * Caso for encontrado algum resultado, finaliza o processo e retorna a lista de temporadas encontrada.
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param filtro TemporadaFilter: objeto contendo os filtros utilizados na consulta
     * @return Lista Temporada: lista de temporadas encontradas na consulta
     */
    public List<Temporada> filtrados(TemporadaFilter filtro) {
        List<Temporada> lista = null;
        try{
            EntityManager em = JPAConnectionFactory.getEntityManager();
            try {
                CriteriaQuery<Temporada> consulta = pesquisa(filtro);
                TypedQuery<Temporada> typed = em.createQuery(consulta).setFirstResult(filtro.getPrimeiroRegistro()).setMaxResults(filtro.getQuantidadeRegistros());
                lista = typed.getResultList();
            } catch (Exception e){
                System.out.println("Erro! " + e);
            } finally{
                em.close();
            }
        } catch (Exception e){
            System.out.println("Erro! " + e);
        }
        return lista;
    }
    
    
    /**
     * Método para realizar a pesquisa de dados de temporadas no banco com base no nome e no jogador
     * <p>
     * Necessário para a paginação.<br>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * CriteriaQuery Temporada query = em.getCriteriaBuilder().createQuery(Temporada.class): cria uma consulta para a entidade Temporada.<br>
     * Root Temporada root = query.from(Temporada.class): define a raiz da consulta sendo a entidade Temporada.<br>
     * Path String  nomePath = root. String get("nome"): indica sobre qual atributo da entidade será realizada uma condicional (nome).<br>
     * Path String  jogadorPath = root. String get("jogador"): indica sobre qual atributo da entidade será realizada uma condicional (jogador).<br>
     * Predicate verificaNome = em.getCriteriaBuilder().equal(nomePath, temporada.getNome()): cria uma condicional para a consulta 
     * onde será verificada as temporadas que apresentarem um nome igual ou semelhante ao informado.<br>
     * Predicate verificaJogador = em.getCriteriaBuilder().equal(jogadorPath, temporada.getJogador()): cria uma condicional para a consulta 
     * onde será verificado as temporadas do jogador.<br>
     * query.where(verificaLogin, verificaJogador): adiciona as condicionais na consulta, 
     * onde será verificado as temporadas que apresentarem ambas as condicionais.<br>
     * OrderImpl ordem = new OrderImpl(nomePath, filtro.isAscendente()): indica sobre qual atributo será ordenado e o tipo de ordenação.<br>
     * query.orderBy(ordem): aplica a ordenação na consulta.<br>
     * Caso alguma exceção seja lançada, será finalizado o processo e retorna um objeto nulo.
     * Senão, finaliza o processo e retorna a consulta.<br>
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param filtro TemporadaFilter: filtro utilizado pela consulta
     * @return CriteriaQuery Temporada: retorna uma consulta totalmente configurada com base nos filtros
     */
    private CriteriaQuery<Temporada> pesquisa(TemporadaFilter filtro){
        CriteriaQuery<Temporada> query = null;
        try {
            EntityManager em = JPAConnectionFactory.getEntityManager();
            try {
                query = em.getCriteriaBuilder().createQuery(Temporada.class);
                Root<Temporada> root = query.from(Temporada.class);
                Path<String> nomePath = root.<String>get("nome");
                Path<Jogador> jogadorPath = root.<Jogador>get("jogador");
                Predicate verificaJogador = em.getCriteriaBuilder().equal(jogadorPath, filtro.getJogador());
                if (filtro.getNome() != null) {
                    Predicate verificaNome = em.getCriteriaBuilder().like(nomePath, "%" + filtro.getNome() + "%");
                    query.where(verificaNome,verificaJogador);
                } else{
                    query.where(verificaJogador);
                }
                OrderImpl ordem = new OrderImpl(nomePath, filtro.isAscendente());
                query.orderBy(ordem);
            } catch(Exception e){
                System.out.println("Erro! " + e);
            } finally{
                em.close();
            }
        } catch (Exception e){
            System.out.println("Erro! " + e);
        }
        return query;
    }
    
    /**
     * Método para realizar a contagem de possíveis resultados de uma consulta filtrada de temporadas
     * <p>
     * Necessário para a paginação.<br>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * TypedQuery Temporada typed = em.createQuery(pesquisa(filtro)): realiza uma consulta e retorna uma lista do tipo Temporada<br>
     * qtd = typed.getResultList().size(): retorna o tamanho da lista.
     * Caso nenhum resultado for encontrado, será lançada uma exceção, finalizando o processo e retornando valor 0.
     * Caso for encontrado algum resultado, finaliza o processo e retorna o tamanho da lista de temporadas encontrada.
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param filtro TemporadaFilter: filtro utilizado para realizar a consulta
     * @return int: quantidades de resultados encontrados pela consulta
     */
    public int quantidadeFiltrados(TemporadaFilter filtro) {
        int qtd = 0;
        try {
            EntityManager em = JPAConnectionFactory.getEntityManager();
            try {
                TypedQuery<Temporada> typed = em.createQuery(pesquisa(filtro));
                qtd = typed.getResultList().size();
            } catch(Exception e){
                System.out.println("Erro! " + e);
            } finally{
                em.close();
            }
        } catch (Exception e){
            System.out.println("Erro! " + e);
        }
        return qtd;
    }
}