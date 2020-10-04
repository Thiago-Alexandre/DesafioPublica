package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.query.criteria.internal.OrderImpl;
import util.JPAConnectionFactory;

/**
 * Classe para realizar a persistência de dados do Jogo
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
public class JogoDAO implements Serializable{
    
    /** 
     * Construtor Vazio para JogoDAO.
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    public JogoDAO() {}

    /**
     * Método para realizar a inserção de dados do jogo no banco
     * <p>
     * Deverá ser realizada a validação dos dados do Jogo e, 
     * caso não for encontrado erros (validacao.getErros().isEmpty()), 
     * o jogo poderá ser salvo. Senão, será impedido de realizar a inserção.<br>
     * Deverá ser realizada a verificação do jogo, que impedirá que dois jogos apresentem a mesma data e hora.
     * Caso a verificação for false, significa que não foi encontrado outro jogo salvo na mesma data e hora e, 
     * assim, o novo jogo poderá ser salvo. Senão, será impedido de realizar a inserção.<br>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * A inserção (em.persist()) deverá ser realizada após o início de uma transação (em.getTransaction().begin()).<br>
     * Esse método só será efetuado no banco após o comando de commit (em.getTransaction().commit()).<br>
     * Ao inserir um novo jogo, deverá ser realizado a atualização de recordes do jogador e a atualização da pontuação da temporada.<br>
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param jogo Jogo: objeto do tipo Jogo contendo os dados para a inserção.
     * @return String: mensagem de sucesso ao inserir ou de falha caso algum erro seja encontrado.
     */
    public String adicionar(Jogo jogo) {
        String mensagem = "";
        Validacao validacao = new Validacao(Jogo.class,jogo);
        if (validacao.getErros().isEmpty()){
            if (!verificar(jogo)){
                try{
                    EntityManager em = JPAConnectionFactory.getEntityManager();
                    try{
                        em.getTransaction().begin();
                        em.persist(jogo);
                        em.getTransaction().commit();
                        mensagem = "Novo Jogo cadastrado com sucesso!";
                        atualizarRecordesJogador(jogo);
                        atualizarTemporada(jogo);
                    } catch(Exception e){
                        mensagem = "Erro ao adicionar o novo jogo!";
                        System.out.println("Erro! " + e);
                    } finally{
                        em.close();
                    }
                } catch(Exception e){
                    mensagem = "Erro na conexão com o banco!";
                    System.out.println("Erro! " + e);
                }        
            } else{
                mensagem = "Já existe um jogo salvo nesta data e hora!";
            }
        } else{
            mensagem = "Valores Inválidos!";
        }
        return mensagem;
    }
    
    /**
     * Método para verificar se existe algum jogo com a mesma data e hora
     * <p>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * CriteriaQuery Jogo query = em.getCriteriaBuilder().createQuery(Jogo.class): cria uma consulta para a entidade Jogo.<br>
     * Root Jogo root = query.from(Jogo.class): define a raiz da consulta sendo a entidade Jogo.<br>
     * Path Date  dataPath = root. Date get("data"): indica sobre qual atributo da entidade será realizada uma condicional. 
     * Neste caso, será sobre a data.<br>
     * Predicate verificaData = em.getCriteriaBuilder().equal(dataPath, jogo.getData()): cria uma condicional para a consulta. 
     * Neste caso, será consultado os jogos que apresentarem a data igual a informada.<br>
     * Join Jogo,Temporada  join = root.join("temporada"): realiza um join entre as entidades Jogo e Temporada com base 
     * no atributo "temporada".<br>
     * Path Jogador jogadorPath = join. Jogador get("jogador"): indica sobre qual atributo do join será realizada uma condicional. 
     * Neste caso, será sobre o jogador.<br>
     * Predicate verificaJogador = em.getCriteriaBuilder().equal(jogadorPath, jogo.getTemporada().getJogador()):
     * cria uma condicional para a consulta. 
     * Neste caso, será consultado todos os jogos do jogador.<br>
     * query.where(verificaData,verificaJogador): adiciona as condicionais na consulta. 
     * Neste caso, será consultado todos os jogos do jogador e verificado se algum deles apresenta a mesma data e hora<br>
     * TypedQuery Jogo typed = em.createQuery(query): realiza a consulta e retorna uma lista com tipo Jogo.<br>
     * Jogo teste = typed.getSingleResult(): pega um possível resultado da consulta.<br>
     * Caso nenhum resultado for encontrado, será lançada uma exceção, finalizando o processo e retornando valor false. 
     * Caso for encontrado algum resultado, finaliza o processo e retorna valor true.<br>
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param jogo Jogo: novo jogo que será inserido
     * @return Boolean: retorna false caso não for encontrado outro jogo do jogador 
     * com o mesma data do jogo informado ou retorna true caso contrário.
     */
    private Boolean verificar(Jogo jogo){
        Boolean dataIgual = false;
        try {
            EntityManager em = JPAConnectionFactory.getEntityManager();
            try {
                CriteriaQuery<Jogo> query = em.getCriteriaBuilder().createQuery(Jogo.class);
                Root<Jogo> root = query.from(Jogo.class);
                Path<Date> dataPath = root.<Date>get("data");
                Predicate verificaData = em.getCriteriaBuilder().equal(dataPath, jogo.getData());
                Join<Jogo,Temporada> join = root.join("temporada");
                Path<Jogador> jogadorPath = join.<Jogador>get("jogador");
                Predicate verificaJogador = em.getCriteriaBuilder().equal(jogadorPath, jogo.getTemporada().getJogador());
                query.where(verificaData,verificaJogador);
                TypedQuery<Jogo> typed = em.createQuery(query);
                Jogo teste = typed.getSingleResult();
                if (teste != null) {
                    dataIgual = true;
                }
            } catch (Exception e){
                System.out.println("Erro! " + e);
            } finally{
                em.close();
            }
        } catch(Exception e){
            System.out.println("Erro! " + e);
        }
        return dataIgual;
    }
    
    /**
     * Método para realizar a atualização do jogador caso houver alteração na quebra de recordes mínimo ou máximo do jogador
     * <p>
     * Deve ser chamado toda vez que um novo jogo for adicionado.<br> 
     * List Jogo lista = buscarJogos(jogador): retorna todos os jogos do jogador.<br>
     * Deverá ser verificado se o novo jogo salvo é o primeiro jogo do jogador. 
     * Caso for, deverá ser alterado o mínimo e o máximo do jogador e atualizar ele no banco.<br>
     * Caso não for, deverá ser verificado se o novo jogo apresenta um novo recorde para o jogador. 
     * Caso apresentar, o jogador deverá ser atualizado no banco.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param jogo Jogo: novo jogo salvo para o jogador.
     */
    private void atualizarRecordesJogador(Jogo jogo){
        Jogador jogador = jogo.getTemporada().getJogador();
        if (jogador != null) {
            List<Jogo> lista = buscarJogos(jogador);
            JogadorDAO jogadorDAO = new JogadorDAO();
            if (lista.size() > 1) {
                Boolean novoRecorde = jogador.atualizarRecordes(jogo);
                if (novoRecorde) {
                    jogadorDAO.atualizar(jogador);
                }   
            } else{
                jogador.primeiroJogo(jogo);
                jogadorDAO.atualizar(jogador);
            }    
        }
    }
    
    /**
     * Método para realizar a atualização da temporada caso houver alteração da pontuação mínima e máxima da temporada
     * <p>
     * Deve ser chamado toda vez que um novo jogo for adicionado.<br> 
     * List Jogo lista = listar(temporada): retorna todos os jogos da temporada.<br>
     * Deverá ser verificado se o novo jogo salvo é o primeiro jogo da temporada. 
     * Caso for, deverá ser alterado o mínimo e o máximo da temporada e atualizar ela no banco.<br>
     * Caso não for, deverá ser verificado se o novo jogo apresenta um novo recorde de pontuação para a temporada. 
     * Caso apresentar, a temporada deverá ser atualizada no banco.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param jogo Jogo: Jogo de referência para realizar a atualização.
     */
    private void atualizarTemporada(Jogo jogo){
        Temporada temporada = jogo.getTemporada();
        List<Jogo> lista = listar(temporada);
        TemporadaDAO temporadaDAO = new TemporadaDAO();
        if (lista.size() > 1) {
            Boolean verificaPontuacao = temporada.atualizarPontuacao(jogo);
            if (verificaPontuacao) {
                temporadaDAO.atualizar(temporada);
            }   
        } else{
            temporada.primeiroJogo(jogo);
            temporadaDAO.atualizar(temporada);
        }
    }
    
    /**
     * Método para realizar a pesquisa de jogos referentes a uma temporada específica no banco
     * <p>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * TypedQuery Jogo query = em.createQuery(jpql, Jogo.class): cria uma consulta JPQL<br>
     * query.setParameter("pTemporada", temporada): adiciona parâmetro (temporada) na consulta, evitando assim SQL Injection.<br>
     * lista =  query.getResultList(): retorna uma lista de jogos.<br>
     * Caso nenhum resultado for encontrado, será lançada uma exceção, finalizando o processo e retornando um objeto nulo. 
     * Caso for encontrado algum resultado, finaliza o processo e retorna a lista de jogos encontrada.<br>
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param temporada Temporada: Temporada em que se deseja obter os jogos no banco.
     * @return Lista Jogo: retorna uma lista de objetos do tipo Jogo referentes a temporada caso for encontrado
     * ou uma lista vazia caso não for encontrado nenhum jogo na temporada informada.
     */
    private List<Jogo> listar(Temporada temporada) {
        List<Jogo> lista = null;
        try{
            EntityManager em = JPAConnectionFactory.getEntityManager();
            String jpql = "select j from Jogo j where j.temporada = :pTemporada";
            TypedQuery<Jogo> query = em.createQuery(jpql, Jogo.class);
            query.setParameter("pTemporada", temporada);
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
     * Método para realizar a pesquisa de jogos do jogador de forma filtrada e paginada
     * <p>
     * Necessário para a paginação.<br>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * CriteriaQuery Jogo consulta = pesquisa(filtro): objeto de consulta com base nos filtros.<br>
     * TypedQuery Jogo typed = em.createQuery(consulta): realiza uma consulta e retorna uma lista do tipo Jogo<br>
     * .setFirstResult(filtro.getPrimeiroRegistro()): define o primeiro resultado da consulta para paginação.<br>
     * .setMaxResults(filtro.getQuantidadeRegistros()): define o total de registros por página.<br>
     * lista = typed.getResultList(): pega um possível resultado da consulta.
     * Caso nenhum resultado for encontrado, será lançada uma exceção, finalizando o processo e retornando um objeto nulo.
     * Caso for encontrado algum resultado, finaliza o processo e retorna a lista de jogos encontrada.
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param filtro JogoFilter: objeto contendo os filtros utilizados na consulta
     * @return Lista Jogo: lista de jogos encontrados na consulta
     */
    public List<Jogo> filtrados(JogoFilter filtro) {
        List<Jogo> lista = null;
        try{
            EntityManager em = JPAConnectionFactory.getEntityManager();
            try {
                CriteriaQuery<Jogo> consulta = pesquisa(filtro);
                TypedQuery<Jogo> typed = em.createQuery(consulta).setFirstResult(filtro.getPrimeiroRegistro()).setMaxResults(filtro.getQuantidadeRegistros());
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
     * Método para realizar a pesquisa de dados de jogos no banco com base na temporada e no jogador
     * <p>
     * Necessário para a paginação.<br>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * CriteriaQuery Jogo query = em.getCriteriaBuilder().createQuery(Jogo.class): cria uma consulta para a entidade Jogo.<br>
     * Root Jogo root = query.from(Jogo.class): define a raiz da consulta sendo a entidade Jogo.<br>
     * Path Date dataPath = root. Date get("data"): indica sobre qual atributo da entidade será realizada uma condicional (data).<br>
     * Se houver um filtro de temporada, este deverá ser definido e adicionado na consulta.<br>
     * Path Temporada temporadaPath = root. Temporada get("temporada"): indica sobre qual atributo da entidade será realizada uma condicional (temporada).<br>
     * Predicate verificaTemporada = em.getCriteriaBuilder().equal(temporadaPath, filtro.getTemporada()): cria uma condicional para a consulta 
     * onde será verificado os jogos da temporada.<br>
     * query.where(verificaTemporada): adiciona a condicional na consulta, 
     * onde será verificado os jogos da temporada.<br>
     * Se não houver um filtro de temporada, deverá ser definido um filtro de jogador.<br>
     * Join Jogo,Temporada join = root.join("temporada"): cria um join entre as entidades Jogo e Temporada com base no atributo "temporada".<br>
     * Path Jogador jogadorPath = join. Jogador get("jogador"): indica sobre qual atributo do join será realizada uma condicional (jogador).<br>
     * Predicate verificaJogador = em.getCriteriaBuilder().equal(jogadorPath, filtro.getJogador()): cria uma condicional para a consulta 
     * onde será verificado os jogos do jogador.<br>
     * query.where(verificaJogador): adiciona a condicional na consulta, 
     * onde será verificado os jogos do jogador.<br>
     * OrderImpl ordem = new OrderImpl(dataPath, filtro.isAscendente()): indica sobre qual atributo será ordenado e o tipo de ordenação.<br>
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
    private CriteriaQuery<Jogo> pesquisa (JogoFilter filtro){
        CriteriaQuery<Jogo> query = null;
        try {
            EntityManager em = JPAConnectionFactory.getEntityManager();
            try {
                query = em.getCriteriaBuilder().createQuery(Jogo.class);
                Root<Jogo> root = query.from(Jogo.class);
                Path<Date> dataPath = root.<Date>get("data");
                if (filtro.getTemporada() != null) {
                    Path<Temporada> temporadaPath = root.<Temporada>get("temporada");
                    Predicate verificaTemporada = em.getCriteriaBuilder().equal(temporadaPath, filtro.getTemporada());
                    query.where(verificaTemporada);
                } else{
                    Join<Jogo,Temporada> join = root.join("temporada");
                    Path<Jogador> jogadorPath = join.<Jogador>get("jogador");
                    Predicate verificaJogador = em.getCriteriaBuilder().equal(jogadorPath, filtro.getJogador());    
                    query.where(verificaJogador);
                }
                OrderImpl ordem = new OrderImpl(dataPath, filtro.isAscendente());
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
     * Método para realizar a contagem de possíveis resultados de uma consulta filtrada de jogos
     * <p>
     * Necessário para a paginação.<br>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * TypedQuery Jogo typed = em.createQuery(pesquisa(filtro)): realiza uma consulta e retorna uma lista do tipo Jogo<br>
     * qtd = typed.getResultList().size(): retorna o tamanho da lista.
     * Caso nenhum resultado for encontrado, será lançada uma exceção, finalizando o processo e retornando valor 0.
     * Caso for encontrado algum resultado, finaliza o processo e retorna o tamanho da lista de jogos encontrada.
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param filtro JogoFilter: filtro utilizado para realizar a consulta
     * @return int: quantidades de resultados encontrados pela consulta
     */
    public int quantidadeFiltrados(JogoFilter filtro) {
        int qtd = 0;
        try {
            EntityManager em = JPAConnectionFactory.getEntityManager();
            try {
                TypedQuery<Jogo> typed = em.createQuery(pesquisa(filtro));
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
    
    /**
     * Método para realizar a pesquisa de dados de jogos no banco com base no jogador
     * <p>
     * EntityManager em = JPAConnectionFactory.getEntityManager(): objeto de conexão com o banco.<br>
     * CriteriaQuery Jogo query = em.getCriteriaBuilder().createQuery(Jogo.class): cria uma consulta para a entidade Jogo.<br>
     * Root Jogo root = query.from(Jogo.class): define a raiz da consulta sendo a entidade Jogo.<br>
     * Join Jogo,Temporada join = root.join("temporada"): cria um join entre as entidades Jogo e Temporada com base no atributo "temporada".<br>
     * Path Jogador jogadorPath = join. Jogador get("jogador"): indica sobre qual atributo do join será realizada uma condicional (jogador).<br>
     * Predicate verificaJogador = em.getCriteriaBuilder().equal(jogadorPath, filtro.getJogador()): cria uma condicional para a consulta 
     * onde será verificado os jogos do jogador.<br>
     * query.where(verificaJogador): adiciona a condicional na consulta, 
     * onde será verificado os jogos do jogador.<br>
     * TypedQuery Jogo typed = em.createQuery(query): cria a consulta e retorna uma lista do tipo Jogo.<br>
     * lista =  query.getResultList(): retorna uma lista de jogos.<br>
     * Caso nenhum resultado for encontrado, será lançada uma exceção, finalizando o processo e retornando um objeto nulo. 
     * Caso for encontrado algum resultado, finaliza o processo e retorna a lista de jogos encontrada.<br>
     * A conexão criada deverá ser fechada (em.close()).
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param jogador Jogador: jogador em que se deseja obter os jogos no banco.
     * @return Lista Jogo: retorna uma lista de objetos do tipo Jogo referentes ao jogador caso for encontrado
     * ou uma lista vazia caso não for encontrado nenhum jogo do jogador informado
     */
    private List<Jogo> buscarJogos(Jogador jogador){
        List<Jogo> lista = null;
        try {
            EntityManager em = JPAConnectionFactory.getEntityManager();
            try {
                CriteriaQuery<Jogo> query = em.getCriteriaBuilder().createQuery(Jogo.class);
                Root<Jogo> root = query.from(Jogo.class);
                Join<Jogo,Temporada> join = root.join("temporada");
                Path<Jogador> jogadorPath = join.<Jogador>get("jogador");
                Predicate verificaJogador = em.getCriteriaBuilder().equal(jogadorPath, jogador);
                query.where(verificaJogador);
                TypedQuery<Jogo> typed = em.createQuery(query);
                lista = typed.getResultList();
            } catch(Exception e){
                System.out.println("Erro! " + e);
            } finally{
                em.close();
            }
        } catch (Exception e){
            System.out.println("Erro! " + e);
        }
        return lista;
    }
}