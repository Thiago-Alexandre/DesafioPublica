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

@ManagedBean
@ViewScoped
public class BeanJogo implements Serializable{
    
    private Jogo jogo;
    private final JogoDAO dao;
    private LazyDataModel<Jogo> jogos;
    private JogoFilter filtro;
    private Integer idTemporada;
    
    public BeanJogo(){
        dao = new JogoDAO();
        filtro = new JogoFilter();
        filtro.setTemporada(null);
        filtro.setJogador(pegarJogadorSessao());
        idTemporada = 0;
        jogos = new JogoPaginator(filtro);
        jogo = new Jogo();
    }
    
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
    
    private Jogador pegarJogadorSessao(){
        FacesContext context = FacesContext.getCurrentInstance();
        return (Jogador) context.getExternalContext().getSessionMap().get("jogadorLogado");
    }
    
    private void mostrarMensagem(String mensagem){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("mensagem", mensagem);
    }
    
    private void removerMensagem(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("mensagem");
    }
    
    public String dataAtual(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	Date date = new Date(); 
	return dateFormat.format(date);
    }
    
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