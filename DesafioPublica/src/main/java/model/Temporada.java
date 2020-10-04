package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * Classe para objetos do tipo Temporada
 * <p>
 * Anotação @Entity: classe referenciada como entidade no banco de dados.<br>
 * Anotação @Table: fornece a ligação da classe "Temporada" com a entidade "temporada".
 * </p>
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
@Entity
@Table(name="temporada")
public class Temporada implements Serializable {
    
    /**
     * Identificação da temporada
     * <p>
     * Anotação @Id: atributo de referência a chave primária da entidade.<br>
     * Anotação @GenerateValue: define como a chave primária será gerada.<br>
     * Neste caso, a estratégia será identificar a forma definida pelo banco de dados.<br>
     * Anotação @Column: fornece a ligação entre o atributo "id" e o campo "id_temporada" da entidade definida no banco.<br>
     * Anotação @Range: impede que seja definido um valor negativo ou 0.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_temporada")
    @Range(min=1)
    private Integer id;
    
    /**
     * Nome da Temporada
     * <p>
     * Anotação @Column: fornece a ligação entre o atributo "nome" e o campo "nome_temporada" da entidade definida no banco.<br>
     * Anotações @NotNull e @NotEmpty: impedem que um valor nulo ou vazio seja definido ao campo.<br>
     * Anotação @Size: impede que uma String maior que a definida no banco seja informada.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Column(name="nome_temporada")
    @NotNull()
    @NotEmpty()
    @Size(max=30)
    private String nome;
    
    /**
     * Pontuação mínima obtida na Temporada
     * <p>
     * Anotação @Column: fornece a ligação entre o atributo "minimo" e o campo "min_temporada" da entidade definida no banco.<br>
     * Anotação @NotNull: impede que um valor nulo seja definido ao campo.<br>
     * Anotação @Range: impede que seja definido um valor negativo.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Column(name="min_temporada")
    @NotNull()
    @Range(min=0)
    private Integer minimo;
    
    /**
     * Pontuação máxima obtida na temporada
     * <p>
     * Anotação @Column: fornece a ligação entre o atributo "maximo" e o campo "max_temporada" da entidade definida no banco.<br>
     * Anotação @NotNull: impede que um valor nulo seja definido ao campo.<br>
     * Anotação @Range: impede que seja definido um valor negativo.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Column(name="max_temporada")
    @NotNull()
    @Range(min=0)
    private Integer maximo;
    
    /**
     * Jogador proprietário dos dados da temporada
     * <p>
     * Anotação @ManyToOne: referencia a ligação entre as entidades "Temporada" e "Jogador".
     * Neste caso, o jogador poderá registrar muitas temporadas e cada temporada será exclusiva de um jogador.<br>
     * Anotação @JoinColumn: fornece a ligação entre o atributo "jogador" e o campo "jogador_temporada" da entidade.
     * Neste caso, referencia uma chave estrangeira que irá retornar um objeto da classe "Jogador".
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @ManyToOne
    @JoinColumn(name="jogador_temporada")
    private Jogador jogador;
    
    /**
     * Lista de jogos da temporada
     * <p>
     * Anotação @OneToMany(mappedBy="temporada"): referencia a ligação entre as entidades "Temporada" e "Jogo" através do atributo "temporada".
     * Neste caso, a temporada poderá conter muitos jogose cada temporada será exclusiva de um jogo.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @OneToMany(mappedBy="temporada")
    private List<Jogo> jogos;
    
    /** 
     * Construtor Vazio para objetos Temporada
     * <p>
     * Necessário para o funcionamento do ManagedBean, assim como os getters e setters.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    public Temporada() {}

    /**
     * Construtor de objeto Temporada
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param id Integer: identificação da temporada
     * @param nome String: nome da temporada
     * @param minimo Integer: pontuação mínima da temporada
     * @param maximo Integer: pontuação máxima da temporada
     * @param jogador Jogador: jogador proprietário dos dados da temporada
     */
    public Temporada(Integer id, String nome, Integer minimo, Integer maximo, Jogador jogador) {
        this.id = id;
        this.nome = nome;
        this.minimo = minimo;
        this.maximo = maximo;
        this.jogador = jogador;
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

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }
    
    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }
    
    /**
     * Método para atualizar a pontuação mínima e máxima da temporada após seu primeiro jogo
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param jogo Jogo: novo jogo realizado pelo jogador na temporada
     */
    public void primeiroJogo(Jogo jogo){
        minimo = jogo.getPlacar();
        maximo = jogo.getPlacar();
    }
    
    /**
     * Método para verificar se o novo jogo possui pontuação menor que o mínimo obtido da temporada 
     * ou pontuação maior que o máximo da temporada
     * <p>
     * Deve ser chamado toda vez que um novo jogo for adicionado.<br> 
     * Se um jogo possuir um placar menor que o mínimo da temporada, essa informação será atualizada
     * e a pontuação mínima da temporada será acrescentada.<br>
     * Se um jogo possuir um placar maior que o máximo da temporada, essa informação será atualizada
     * e a pontuação máxima da temporada será acrescentada.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param jogo Jogo: novo jogo realizado pelo jogador
     * @return Boolean: retorno um valor booleano, onde:
     * <ul>
     *      <li>valor true significa que houve alteração em alguma pontuação (mínima ou máxima) e deverá ser realizado a atualização do jogador</li>
     *      <li>valor false significa que não houve alteração em nenhuma pontuação e não será necessária a atualização do jogador</li>
     * </ul>
     */
    public Boolean atualizarPontuacao(Jogo jogo){
        if (jogo.getPlacar() < minimo) {
            minimo = jogo.getPlacar();
            return true;
        }
        if (jogo.getPlacar() > maximo) {
            maximo = jogo.getPlacar();
            return true;
        }
        return false;
    }
}