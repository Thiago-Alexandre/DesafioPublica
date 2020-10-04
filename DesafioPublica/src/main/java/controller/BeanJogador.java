package controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Jogador;
import model.JogadorDAO;

/**
 * Classe bean para objetos do tipo Jogador
 * <p>
 * Anotação @ManagedBean: esta classe será responsável pela ligação entre a view (páginas JSF) e as regras de negócios (Jogador).<br>
 * Anotação @ViewScoped: tempo de vida do bean será definido para o tempo da visualização da página.
 * </p>
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class BeanJogador implements Serializable{
    
    /**
     * Objeto do tipo Jogador que será manipulado pelo bean
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private Jogador jogador;
    
    /**
     * DAO do tipo Jogador que será utilizado pelo bean
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private JogadorDAO dao;
    
    /**
     * Campo String utilizado para adicionar e alterar jogadores, correspondente ao nome do jogador
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private String nome;
    
    /**
     * Campo String utilizado para adicionar e alterar jogadores, correspondente ao login do jogador
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private String login;
    
    /**
     * Campo String utilizado para adicionar e alterar jogadores, correspondente a senha do jogador
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private String senha;

    /**
      * Construtor do bean
      * @author Thiago Alexandre Buerger
      * @version 1.0
      */
    public BeanJogador() {
        dao = new JogadorDAO();
        pegarJogadorSessao();
        senha = "";
    }
    
    /**
     * Método chamado pela página para adicionar novos jogadores
     * <p>
     * Somente os campos nome, login e senha poderão ser definidos pelo usuário.<br>
     * Uma mensagem será exibida ao usuário e ele será redirecionado para a página de login.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return String: retorna um redirecionamento
     */
    public String cadastrar(){
        Jogador novo = new Jogador(null,nome,0,0,0,0,login,senha);
        mostrarMensagem(dao.adicionar(novo));
        return "login?faces-redirect=true";
    }
    
    /**
     * Método chamado pela página para alterar jogadores
     * <p>
     * Somente os campos nome e login poderão ser definidos pelo usuário (neste método).<br>
     * Uma mensagem será exibida ao usuário e ele será redirecionado para a página do jogador.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return String: retorna um redirecionamento
     */
    public String salvar(){
        jogador.setNome(nome);
        jogador.setLogin(login);
        mostrarMensagem(dao.atualizar(jogador));
        return "jogador?faces-redirect=true";
    }
    
    /**
     * Método chamado pela página para alterar a senha dos jogadores
     * <p>
     * Somente o campo senha poderá ser definidos pelo usuário (neste método).<br>
     * Uma mensagem será exibida ao usuário e ele será redirecionado para a página do jogador.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return String: retorna um redirecionamento
     */
    public String alterarSenha(){
        jogador.setSenha(senha);
        senha = "";
        mostrarMensagem(dao.atualizar(jogador));
        return "jogador?faces-redirect=true";
    }
    
    /**
     * Método chamado pela página para realizar login de jogadores
     * <p>
     * Somente os campos login e senha poderão ser definidos pelo usuário para a verificação.<br>
     * Uma mensagem será exibida ao usuário.<br>
     * Caso não for encontrado um jogador que apresente o login e senha informados, 
     * o usuário será redirecionado para a página de login.<br>
     * Caso contrário, será redirecionado para a página principal.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return String: retorna um redirecionamento
     */
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
    
    /**
     * Método para disponibilizar um nome alternativo para o botão do menu ligado ao jogador logado
     * <p>
     * Caso o nome do jogador for muito grande, uma parte será cortada e usada no botão do menu.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return String: retorna uma parte do nome do jogador logado
     */
    public String nomeJogador(){
        String nomeReduzido = jogador.getNome();
        if (nomeReduzido.length() > 10) {
            nomeReduzido = nomeReduzido.substring(0, 10);
        }
        return nomeReduzido;
    }
    
    /**
     * Método chamado pela página para deslogar o jogador
     * <p>
     * Remove as mensagens exibidas ao jogador<br>
     * Remove o jogador da sessão.<br>
     * O usuário será redirecionado para página de login.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return String: retorna um redirecionamento
     */
    public String deslogar() {
        removerJogadorSessao();
        removerMensagem();
        return "login?faces-redirect=true";
    }
    
    /**
     * Método chamado pela página para ver detalhes e possibilitar a alteração do jogador logado
     * <p>
     * Remove as mensagens exibidas ao jogador<br>
     * O usuário será redirecionado para página do jogador.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return String: retorna um redirecionamento
     */
    public String verDetalhes() {
        removerMensagem();
        return "jogador?faces-redirect=true";
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
     * Método para adicionar um jogador na sessão
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private void adicionarJogadorSessao(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("jogadorLogado", jogador);
    }
    
    /**
     * Método para remover o jogador da sessão
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private void removerJogadorSessao(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("jogadorLogado");
    }
    
    /**
     * Método para pegar o jogador da sessão
     * <p>
     * Se existir um jogador na sessão, será consultado o jogador atualizado do banco.
     * Senão, um jogador vazio será instanciado.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
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