package controller;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Jogador;
import model.Temporada;
import model.TemporadaDAO;
import model.TemporadaFilter;
import model.TemporadaPaginator;
import org.primefaces.model.LazyDataModel;

/**
 * Classe bean para objetos do tipo Temporada
 * <p>
 * Anotação @ManagedBean: esta classe será responsável pela ligação entre a view (páginas JSF) e as regras de negócios (Temporada).<br>
 * Anotação @ViewScoped: tempo de vida do bean será definido para o tempo da visualização da página.
 * </p>
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class BeanTemporada implements Serializable{
    
    /**
     * Objeto do tipo Temporada que será manipulado pelo bean
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private Temporada temporada;
    
    /**
      * Filtro para objeto Temporada utilizado para pesquisas e paginação
      * @author Thiago Alexandre Buerger
      * @version 1.0
      */
    private TemporadaFilter filtro;
    
    /**
      * Objeto utilizado pelo dataTable para carregar uma lista de temporadas filtrada e paginada
      * @author Thiago Alexandre Buerger
      * @version 1.0
      */
    private LazyDataModel<Temporada> temporadas;
    
    /**
     * DAO do tipo Temporada que será utilizado pelo bean
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private final TemporadaDAO dao;
    
    /**
      * Campo String utilizado para adicionar e alterar temporadas, correspondente ao nome da temporada
      * @author Thiago Alexandre Buerger
      * @version 1.0
      */
    private String nome;
    
    /**
      * Campo Jogador utilizado para adicionar novas temporadas, correspondente ao jogador da temporada
      * @author Thiago Alexandre Buerger
      * @version 1.0
      */
    private final Jogador jogador;
    
    /**
      * Construtor do bean
      * <p>
      * Deverá instanciar o DAO e o Filtro.<br>
      * O filtro de jogador deverá ser iniciado com o jogador da sessão.<br>
      * A lista de temporadas deverá ser carregada utilizando o filtro configurado.<br>
      * a temporada, o nome e o id da Temporada deverão ser iniciados com valores vazios.
      * </p>
      * @author Thiago Alexandre Buerger
      * @version 1.0
      */
    public BeanTemporada(){
        dao = new TemporadaDAO();
        filtro = new TemporadaFilter();
        jogador = pegarJogadorSessao();
        filtro.setJogador(jogador);
        temporadas = new TemporadaPaginator(filtro);
        nome = "";
        temporada = new Temporada();
        temporada.setId(null);
    }
    
    /**
     * Método chamado pela página para adicionar nova temporada ou alterar uma temporada selecionada
     * <p>
     * Deverá ser verificado se há uma temporada alterada ou uma temporada vazia.<br>
     * Caso o id da temporada for nulo, significa que é uma nova temporada.<br>
     * Somente o campo nome deverá ser definido pelo usuário e a temporada deverá ser adicionada no banco.<br>
     * Caso já exista um id de temporada, somente o campo nome deverá ser definido pelo usuário 
     * e a temporada deverá ser atualizada no banco.<br>
     * Uma mensagem será exibida ao usuário e ele será redirecionado para a página de temporada.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return String: retorna um redirecionamento
     */
    public String salvar(){
        if (temporada.getId() == null) {
            Temporada nova = new Temporada(null,nome,0,0,jogador);
            mostrarMensagem(dao.adicionar(nova));
        } else{
            temporada.setNome(nome);
            mostrarMensagem(dao.atualizar(temporada));
        }
        nome = "";
        temporadas = new TemporadaPaginator(filtro);
        return "temporada?faces-redirect=true";
    }
    
    /**
     * Método chamado pela página para atualizar a lista de temporadas
     * <p>
     * Remove as mensagens exibidas ao jogador<br>
     * a lista de temporadas deverá ser atualizada com o filtro.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    public void pesquisar(){
        removerMensagem();
        temporadas = new TemporadaPaginator(filtro);
    }
    
    /**
     * Método chamado pela página limpar os campos e possibilitar adicionar uma nova temporada
     * <p>
     * Remove as mensagens exibidas ao jogador<br>
     * O campo de nome e a temporada deverão ser definidos com valores vazios.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    public void novo(){
        removerMensagem();
        nome = "";
        temporada = new Temporada();
    }
    
    /**
     * Método chamado pela página para voltar para a página inicial
     * <p>
     * Remove as mensagens exibidas ao jogador<br>
     * O usuário será redirecionado para página inicial.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return String: retorna um redirecionamento
     */
    public String voltar() {
        removerMensagem();
        return "index?faces-redirect=true";
    }
    
    /**
     * Método chamado pela página para adicionar e alterar temporadas
     * <p>
     * Remove as mensagens exibidas ao jogador<br>
     * O usuário será redirecionado para página de temporada.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return String: retorna um redirecionamento
     */
    public String verTemporadas(){
        removerMensagem();
        return "temporada?faces-redirect=true";
    }
    
    /**
     * Método chamado pela página para carregar os dados de uma temporada selecionada pelo usuário
     * <p>
     * Remove as mensagens exibidas ao jogador<br>
     * O campo nome e a temporada deverão ser atualizados com os dados da temporada selecionada pelo usuário.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param temporadaSelecionada Temporada: temporada selecionada pelo usuário
     */
    public void alterar(Temporada temporadaSelecionada){
        removerMensagem();
        temporada = temporadaSelecionada;
        nome = temporadaSelecionada.getNome();
    }
    
    /**
     * Método para buscar todas as temporadas do jogador
     * <p>
     * Utilizado para carregar o selectOneMenu.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return List Temporada: retorna a lista de todas as temporadas do jogador
     */
    public List<Temporada> todasTemporadas(){
        return dao.listar(jogador);
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
     * Método para pegar o jogador da sessão
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return Jogador: retorna o jogador da sessão
     */
    private Jogador pegarJogadorSessao(){
        FacesContext context = FacesContext.getCurrentInstance();
        return (Jogador) context.getExternalContext().getSessionMap().get("jogadorLogado");
    }

    public Temporada getTemporada() {
        return temporada;
    }

    public void setTemporada(Temporada temporada) {
        this.temporada = temporada;
    }

    public TemporadaFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(TemporadaFilter filtro) {
        this.filtro = filtro;
    }

    public LazyDataModel<Temporada> getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(LazyDataModel<Temporada> temporadas) {
        this.temporadas = temporadas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}