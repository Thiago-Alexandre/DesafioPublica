package controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Jogador;
import model.Jogo;
import model.JogoDAO;
import model.JogoFilter;
import model.JogoPaginator;
import model.TemporadaDAO;
import org.primefaces.model.LazyDataModel;

/**
 * Classe bean para objetos do tipo Jogo
 * <p>
 * Anotação @ManagedBean: esta classe será responsável pela ligação entre a view (páginas JSF) e as regras de negócios (Jogo).<br>
 * Anotação @ViewScoped: tempo de vida do bean será definido para o tempo da visualização da página.
 * </p>
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class BeanJogo implements Serializable{
    
    /**
     * Objeto do tipo Jogo que será manipulado pelo bean
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private Jogo jogo;
    
    /**
     * DAO do tipo Jogo que será utilizado pelo bean
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private final JogoDAO dao;
    
    /**
      * Objeto utilizado pelo dataTable para carregar uma lista de jogos filtrada e paginada
      * @author Thiago Alexandre Buerger
      * @version 1.0
      */
    private LazyDataModel<Jogo> jogos;
    
    /**
      * Filtro para objeto Jogo utilizado para pesquisas e paginação
      * @author Thiago Alexandre Buerger
      * @version 1.0
      */
    private JogoFilter filtro;
    
    /**
      * Valor retornado pelo selectOneMenu indicando qual a temporada selecionada pelo usuário
      * @author Thiago Alexandre Buerger
      * @version 1.0
      */
    private Integer idTemporada;
    
    /**
      * Construtor do bean
      * <p>
      * Deverá instanciar o DAO e o Filtro.<br>
      * O filtro de temporada deverá ser iniciado como null, retornando todos os jogos do jogador.<br>
      * O filtro de jogador deverá ser iniciado com o jogador da sessão.<br>
      * A lista de jogos deverá ser carregada utilizando o filtro configurado.<br>
      * O jogo e o idTemporada deverão ser iniciados com valores vazios.
      * </p>
      * @author Thiago Alexandre Buerger
      * @version 1.0
      */
    public BeanJogo(){
        dao = new JogoDAO();
        filtro = new JogoFilter();
        filtro.setTemporada(null);
        filtro.setJogador(pegarJogadorSessao());
        idTemporada = 0;
        jogos = new JogoPaginator(filtro);
        jogo = new Jogo();
    }
    
    /**
     * Método chamado pela página para adicionar novos jogos
     * <p>
     * Deverá ser buscado a temporada selecionada pelo usuário no banco e 
     * definida no jogo antes de inserir.<br>
     * Uma mensagem será exibida ao usuário e ele será redirecionado para a página inicial.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return String: retorna um redirecionamento
     */
    public String salvar(){
        jogo.setId(null);
        if (idTemporada > 0) {
            TemporadaDAO temporadaDAO = new TemporadaDAO();
            jogo.setTemporada(temporadaDAO.buscarPorId(idTemporada));    
        } else{
            jogo.setTemporada(null);
        }
        mostrarMensagem(dao.adicionar(jogo));
        return "index?faces-redirect=true";
    }
    
    /**
     * Método chamado pela página para atualizar a lista de jogos
     * <p>
     * Remove as mensagens exibidas ao jogador<br>
     * Deverá ser verificado se o usuário adicionou um filtro por temporada.
     * Caso sim, a temporada deverá ser buscada do banco e adicionado no filtro<br>
     * A lista de jogos deverá ser atualizada utilizando o filtro.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    public void pesquisar(){
        removerMensagem();
        TemporadaDAO temporadaDAO = new TemporadaDAO();
        if (idTemporada > 0) {
            filtro.setTemporada(temporadaDAO.buscarPorId(idTemporada));
        } else{
            filtro.setTemporada(null);
        }
        jogos = new JogoPaginator(filtro);
    }
    
    /**
     * Método para pegar o jogador da sessão
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return Jogador: retorna o jogador da sessão
     */
    private Jogador pegarJogadorSessao(){
        FacesContext context = FacesContext.getCurrentInstance();
        return (Jogador) context.getExternalContext().getSessionMap().get("jogadorLogado");
    }
    
    /**
     * Método para adicionar uma mensagem na sessão e possibilitar ser exibida para o usuário
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param mensagem String: mensagem que será exibida ao usuário
     */
    private void mostrarMensagem(String mensagem){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("mensagem", mensagem);
    }
    
    /**
     * Método para remover a mensagem da sessão
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private void removerMensagem(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("mensagem");
    }
    
    /**
     * Método para pegar a data atual
     * <p>
     * Utilizado para configurar o calendar e impedir que o usuário selecione uma data no futuro.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return String: retorna a data atual no formato utilizado pelo calendar
     */
    public String dataAtual(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	Date date = new Date(); 
	return dateFormat.format(date);
    }
    
    /**
     * Método chamado pela página para adicionar um novo jogo
     * <p>
     * Remove as mensagens exibidas ao jogador<br>
     * O usuário será redirecionado para página de novo jogo.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return String: retorna um redirecionamento
     */
    public String novoJogo(){
        removerMensagem();
        return "jogo?faces-redirect=true";
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public LazyDataModel<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(LazyDataModel<Jogo> jogos) {
        this.jogos = jogos;
    }

    public JogoFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(JogoFilter filtro) {
        this.filtro = filtro;
    }

    public Integer getIdTemporada() {
        return idTemporada;
    }

    public void setIdTemporada(Integer idTemporada) {
        this.idTemporada = idTemporada;
    }
}