package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * Classe para objetos do tipo Jogador
 * <p>
 * Anotação @Entity: classe referenciada como entidade no banco de dados.<br>
 * Anotação @Table: fornece a ligação da classe "Jogador" com a entidade "jogador".
 * </p>
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
@Entity
@Table(name="jogador")
public class Jogador implements Serializable{
    
    /**
     * Identificação do jogador
     * <p>
     * Anotação @Id: atributo de referência a chave primária da entidade.<br>
     * Anotação @GenerateValue: define como a chave primária será gerada.<br>
     * Neste caso, a estratégia será identificar a forma definida pelo banco de dados.<br>
     * Anotação @Column: fornece a ligação entre o atributo "id" e o campo "id_jogador" da entidade definido no banco.<br>
     * Anotação @Range: impede que seja definido um valor negativo ou 0.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_jogador")
    @Range(min=1)
    private Integer id;
    
    /**
     * Nome do jogador
     * <p>
     * Anotação @Column: fornece a ligação entre o atributo "nome" e o campo "nome_jogador" da entidade definida no banco.<br>
     * Anotações @NotNull e @NotEmpty: impedem que um valor nulo ou vazio seja definido ao campo.<br>
     * Anotação @Size: impede que uma String maior que a definida no banco seja informada.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Column(name="nome_jogador")
    @NotNull()
    @NotEmpty()
    @Size(min=1, max=50)
    private String nome;
    
    /**
     * Pontuação mínima obtida pelo jogador
     * <p>
     * Anotação @Column: fornece a ligação entre o atributo "minimo" e o campo "min_jogador" da entidade definida no banco.<br>
     * Anotação @NotNull: impede que um valor nulo seja definido ao campo.<br>
     * Anotação @Range: impede que seja definido um valor negativo.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Column(name="min_jogador")
    @NotNull()
    @Range(min=0)
    private Integer minimo;
    
    /**
     * Pontuação máxima obtida pelo jogador
     * <p>
     * Anotação @Column: fornece a ligação entre o atributo "maximo" e o campo "max_jogador" da entidade definida no banco.<br>
     * Anotação @NotNull: impede que um valor nulo seja definido ao campo.<br>
     * Anotação @Range: impede que seja definido um valor negativo.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Column(name="max_jogador")
    @NotNull()
    @Range(min=0)
    private Integer maximo;
    
    /**
     * Quantidade de vezes em que o recorde de pontos mínimos do Jogador foram quebrados
     * <p>
     * Anotação @Column: fornece a ligação entre o atributo "qtdQuebraRecordeMin" e o campo "quebra_recorde_min_jogador" da entidade definida no banco.<br>
     * Anotação @NotNull: impede que um valor nulo seja definido ao campo.<br>
     * Anotação @Range: impede que seja definido um valor negativo.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Column(name="quebra_recorde_min_jogador")
    @NotNull()
    @Range(min=0)
    private Integer qtdQuebraRecordeMin;
    
    /**
     * Quantidade de vezes em que o recorde de pontos máximos do Jogador foram quebrados
     * <p>
     * Anotação @Column: fornece a ligação entre o atributo "qtdQuebraRecordeMax" e o campo "quebra_recorde_max_jogador" da entidade definida no banco.<br>
     * Anotação @NotNull: impede que um valor nulo seja definido ao campo.<br>
     * Anotação @Range: impede que seja definido um valor negativo.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Column(name="quebra_recorde_max_jogador")
    @NotNull()
    @Range(min=0)
    private Integer qtdQuebraRecordeMax;
    
    /**
     * Login do jogador
     * <p>
     * Anotação @Column: fornece a ligação entre o atributo "login" e o campo "login_jogador" da entidade definida no banco.<br>
     * Anotações @NotNull e @NotEmpty: impedem que um valor nulo ou vazio seja definido ao campo.<br>
     * Anotação @Size: impede que uma String maior que a definida no banco seja informada.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Column(name="login_jogador")
    @NotNull()
    @NotEmpty()
    @Size(min=1, max=30)
    private String login;
    
    /**
     * Senha do jogador
     * <p>
     * Anotação @Column: fornece a ligação entre o atributo "senha" e o campo "senha_jogador" da entidade definida no banco.<br>
     * Anotações @NotNull e @NotEmpty: impedem que um valor nulo ou vazio seja definido ao campo.<br>
     * Anotação @Size: impede que uma String maior que a definida no banco seja informada.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Column(name="senha_jogador")
    @NotNull()
    @NotEmpty()
    @Size(min=1, max=30)
    private String senha;

    /** 
     * Construtor Vazio para objetos Jogador
     * <p>
     * Necessário para o funcionamento do ManagedBean, assim como os getters e setters.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    public Jogador() {}

    /**
     * Construtor com parâmetros para objetos Jogador
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param id Integer: identificação do Jogador
     * @param nome String: nome do Jogador
     * @param minimo Integer: pontuação mínima obtida pelo jogador
     * @param maximo Integer: pontuação máxima obtida pelo jogador
     * @param qtdQuebraRecordeMin Integer: quantidade de vezes em que o recorde de pontos mínimos do Jogador foram quebrados, independente da temporada
     * @param qtdQuebraRecordeMax Integer: quantidade de vezes em que o recorde de pontos máximos do Jogador foram quebrados, independente da temporada
     * @param login String: login utilizado pelo jogador para ter acesso a sua conta
     * @param senha String: senha utilizada pelo jogador para ter acesso a sua conta
     */
    public Jogador(Integer id, String nome, Integer minimo, Integer maximo, Integer qtdQuebraRecordeMin, Integer qtdQuebraRecordeMax, String login, String senha) {
        this.id = id;
        this.nome = nome;
        this.minimo = minimo;
        this.maximo = maximo;
        this.qtdQuebraRecordeMin = qtdQuebraRecordeMin;
        this.qtdQuebraRecordeMax = qtdQuebraRecordeMax;
        this.login = login;
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getMinimo() {
        return minimo;
    }

    public void setMinimo(Integer minimo) {
        this.minimo = minimo;
    }

    public Integer getMaximo() {
        return maximo;
    }

    public void setMaximo(Integer maximo) {
        this.maximo = maximo;
    }

    public Integer getQtdQuebraRecordeMin() {
        return qtdQuebraRecordeMin;
    }

    public void setQtdQuebraRecordeMin(Integer qtdQuebraRecordeMin) {
        this.qtdQuebraRecordeMin = qtdQuebraRecordeMin;
    }

    public Integer getQtdQuebraRecordeMax() {
        return qtdQuebraRecordeMax;
    }

    public void setQtdQuebraRecordeMax(Integer qtdQuebraRecordeMax) {
        this.qtdQuebraRecordeMax = qtdQuebraRecordeMax;
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
    
    /**
     * Método para atualizar a pontuação mínima e máxima do jogador após seu primeiro jogo
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param jogo Jogo: novo jogo realizado pelo jogador
     */
    public void primeiroJogo(Jogo jogo){
        minimo = jogo.getPlacar();
        maximo = jogo.getPlacar();
    }
    
    /**
     * Método para verificar se o novo jogo possui pontuação menor que o mínimo obtido pelo jogador 
     * ou pontuação maior que o máximo obtido pelo jogador
     * <p>
     * Deve ser chamado toda vez que um novo jogo for adicionado.<br> 
     * Se um jogo possuir um placar menor que o mínimo do jogador, essa informação será atualizada
     * e a quantidade de quebra de recordes mínimos do jogador será acrescentada.<br>
     * Se um jogo possuir um placar maior que o máximo do jogador, essa informação será atualizada
     * e a quantidade de quebra de recordes máxima do jogador será acrescentada.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param jogo Jogo: novo jogo realizado pelo jogador
     * @return Boolean: retorno um valor booleano, onde:
     * <p>
     *      <ul>
     *           <li>valor true significa que houve alteração em alguma pontuação (mínima ou máxima) e deverá ser realizado a atualização do jogador</li>
     *           <li>valor false significa que não houve alteração em nenhuma pontuação e não será necessária a atualização do jogador</li>
     *      </ul>
     * </p>
     */
    public Boolean atualizarRecordes(Jogo jogo){
        if (jogo.getPlacar() < minimo) {
            System.out.println("É menor");
            minimo = jogo.getPlacar();
            qtdQuebraRecordeMin++;
            return true;
        }
        if (jogo.getPlacar() > maximo) {
            System.out.println("É maior");
            maximo = jogo.getPlacar();
            qtdQuebraRecordeMax++;
            return true;
        }
        return false;
    }
}