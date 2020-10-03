package controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Jogador;
import model.JogadorDAO;

@ManagedBean
@ViewScoped
public class BeanJogador implements Serializable{
    
    private Jogador jogador;
    private JogadorDAO dao;
    private String nome;
    private String login;
    private String senha;

    public BeanJogador() {
        dao = new JogadorDAO();
        pegarJogadorSessao();
        senha = "";
    }
    
    public String cadastrar(){
        Jogador novo = new Jogador(null,nome,0,0,0,0,login,senha);
        mostrarMensagem(dao.adicionar(novo));
        return "login?faces-redirect=true";
    }
    
    public String salvar(){
        jogador.setNome(nome);
        jogador.setLogin(login);
        mostrarMensagem(dao.atualizar(jogador));
        return "jogador?faces-redirect=true";
    }
    
    public String alterarSenha(){
        jogador.setSenha(senha);
        senha = "";
        mostrarMensagem(dao.atualizar(jogador));
        return "jogador?faces-redirect=true";
    }
    
    public String efetuarLogin(){
        Jogador verificacao = new Jogador(null,"verificação",0,0,0,0,login,senha);
        jogador = dao.buscar(verificacao);
        if (jogador != null) {
            adicionarJogadorSessao();
            mostrarMensagem("Bem vindo " + jogador.getNome());
            return "index?faces-redirect=true";
        }
        jogador = new Jogador();
        mostrarMensagem("Erro ao logar: Login ou Senha incorretos!");
        return "login?faces-redirect=true";
    }
    
    public String nomeJogador(){
        String nomeReduzido = jogador.getNome();
        if (nomeReduzido.length() > 10) {
            nomeReduzido = nomeReduzido.substring(0, 10);
        }
        return nomeReduzido;
    }
    
    public String deslogar() {
        removerJogadorSessao();
        removerMensagem();
        return "login?faces-redirect=true";
    }
    
    public String verDetalhes() {
        removerMensagem();
        return "jogador?faces-redirect=true";
    }
    
    private void mostrarMensagem(String mensagem){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("mensagem", mensagem);
    }
    
    private void removerMensagem(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("mensagem");
    }
    
    private void adicionarJogadorSessao(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("jogadorLogado", jogador);
    }
    
    private void removerJogadorSessao(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("jogadorLogado");
    }
    
    private void pegarJogadorSessao(){
        FacesContext context = FacesContext.getCurrentInstance();
        jogador = (Jogador) context.getExternalContext().getSessionMap().get("jogadorLogado");
        if (jogador == null) {
            jogador = new Jogador();
            login = "";
            nome = "";
        } else{
            jogador = dao.buscarPorId(jogador.getId());
            login = jogador.getLogin();
            nome = jogador.getNome();
        }
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}