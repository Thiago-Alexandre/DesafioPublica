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

@ManagedBean
@ViewScoped
public class BeanTemporada implements Serializable{
    
    private Temporada temporada;
    private TemporadaFilter filtro;
    private LazyDataModel<Temporada> temporadas;
    private final TemporadaDAO dao;
    private String nome;
    private final Jogador jogador;
    
    public BeanTemporada(){
        nome = "";
        jogador = pegarJogadorSessao();
        filtro = new TemporadaFilter();
        filtro.setJogador(jogador);
        dao = new TemporadaDAO();
        temporada = new Temporada();
        temporada.setId(null);
        temporadas = new TemporadaPaginator(filtro);
    }
    
    public String salvar(){
        if (temporada.getId() == null) {
            System.out.println("Salvando");
            Temporada nova = new Temporada(null,nome,0,0,jogador);
            mostrarMensagem(dao.adicionar(nova));
        } else{
            System.out.println("Atualizando");
            temporada.setNome(nome);
            mostrarMensagem(dao.atualizar(temporada));
        }
        nome = "";
        temporadas = new TemporadaPaginator(filtro);
        return "temporada?faces-redirect=true";
    }
    
    public void pesquisar(){
        removerMensagem();
        temporadas = new TemporadaPaginator(filtro);
    }
    
    public void novo(){
        removerMensagem();
        nome = "";
        temporada = new Temporada();
    }
    
    public String voltar() {
        removerMensagem();
        return "index?faces-redirect=true";
    }
    
    public String verTemporadas(){
        removerMensagem();
        return "temporada?faces-redirect=true";
    }
    
    public void alterar(Temporada temporadaSelecionada){
        removerMensagem();
        temporada = temporadaSelecionada;
        nome = temporadaSelecionada.getNome();
    }
    
    public List<Temporada> todasTemporadas(){
        return dao.listar(jogador);
    }
    private void mostrarMensagem(String mensagem){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("mensagem", mensagem);
    }
    
    private void removerMensagem(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("mensagem");
    }
    
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