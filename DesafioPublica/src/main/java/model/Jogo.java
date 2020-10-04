package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * Classe para objetos do tipo Jogo
 * <p>
 * Anotação @Entity: classe referenciada como entidade no banco de dados.<br>
 * Anotação @Table: fornece a ligação da classe "Jogo" com a entidade "jogo".<br>
 * Implements Comparable: necessário para sobrescrever o método compareTo que será usado para encontrar o jogo com maior ou menor pontuação.
 * </p>
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
@Entity
@Table(name="jogo")
public class Jogo implements Serializable, Comparable<Jogo>{
   
    /**
     * Identificação do jogo
     * <p>
     * Anotação @Id: atributo de referência a chave primária da entidade.<br>
     * Anotação @GenerateValue: define como a chave primária será gerada.<br>
     * Neste caso, a estratégia será identificar a forma definida pelo banco de dados.<br>
     * Anotação @Column: fornece a ligação entre o atributo "id" e o campo "id_jogo" da entidade definida no banco.<br>
     * Anotação @Range: impede que seja definido um valor negativo ou 0.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_jogo")
    @Range(min=1)
    private Integer id;
    
    /**
     * Data e hora do jogo
     * <p>
     * Anotação @Column: fornece a ligação entre o atributo "data" e o campo "data_jogo" da entidade definida no banco.<br>
     * Anotação @NotNull: impede que um valor nulo seja definido ao campo.<br>
     * Anotação @Temporal: define como será tratado o tipo de informação de data.
     * Neste caso, será aplicado a forma para armazenar data e horas.<br>
     * Anotação @Past: impede que seja definido uma data no futuro.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Column(name="data_jogo")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @NotNull()
    @Past()
    private Date data;
    
    /**
     * Descrição do jogo
     * <p>
     * Anotação @Column: fornece a ligação entre o atributo "descricao" e o campo "descricao_jogo" da entidade definida no banco.<br>
     * Anotação @NotNull e @NotEmpty: impedem que um valor nulo ou vazio seja definido ao campo.<br>
     * Anotação @Size: impede que uma String maior que a definida no banco seja informada.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Column(name="descricao_jogo")
    @NotNull()
    @NotEmpty()
    @Size(max=250)
    private String descricao;
    
    /**
     * Pontuação obtida no jogo
     * <p>
     * Anotação @Column: fornece a ligação entre o atributo "placar" e o campo "placar_jogador" da entidade definida no banco.<br>
     * Anotação @NotNull: impede que um valor nulo seja definido ao campo.<br>
     * Anotação @Range: impede que seja definido um valor negativo ou maior que 1000.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @Column(name="placar_jogo")
    @NotNull()
    @Range(min=0, max=1000)
    private Integer placar;
    
    /**
     * Temporada na qual o jogo está vinculado
     * <p>
     * Anotação @ManyToOne: referencia a ligação entre as entidades "Jogo" e "Temporada".
     * Neste caso, a temporada poderá conter muitos jogos e cada jogo será exclusivo de uma temporada.<br>
     * Anotação @JoinColumn: fornece a ligação entre o atributo "temporada" e o campo "temporada_jogo" da entidade.
     * Neste caso, referencia uma chave estrangeira que irá retornar um objeto da classe "Temporada".<br>
     * Anotação @NotNull: impede que um valor nulo seja definido ao campo.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    @ManyToOne
    @JoinColumn(name="temporada_jogo")
    @NotNull()
    private Temporada temporada;

    /** 
     * Construtor Vazio para objetos Jogo
     * <p>
     * Necessário para o funcionamento do ManagedBean, assim como os getters e setters.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    public Jogo() {}

    /**
     * Construtor de objeto Jogo
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param id Integer: identificação do Jogo
     * @param data Date: data e hora do Jogo
     * @param descricao String: descrição do Jogo
     * @param placar Integer: pontuação obtida no Jogo
     * @param temporada Temporada: temporada na qual o jogo está vinculado
     */
    public Jogo(Integer id, Date data, String descricao, Integer placar, Temporada temporada) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.placar = placar;
        this.temporada = temporada;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPlacar() {
        return placar;
    }

    public void setPlacar(Integer placar) {
        this.placar = placar;
    }

    public Temporada getTemporada() {
        return temporada;
    }

    public void setTemporada(Temporada temporada) {
        this.temporada = temporada;
    }

    /**
     * Método compareTo sobrescrito
     * <p>
     * Necessário para definir como será a comparação realizada entre dois jogos, 
     * permitindo descobrir qual jogo possui maior ou menor pontuação.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param j Jogo: objeto Jogo que será comparado
     * @return int: valor de retorno, onde:
     * <ul>
    *       <li>valor positivo significa que o jogo possui mais pontuação que o jogo comparado</li>
    *       <li>valor negativo significa que o jogo possui menos pontuação que o jogo comparado</li>
    *       <li>valor 0 significa que os jogos possuem a mesma pontuação</li>
    * </ul>
     */
    @Override
    public int compareTo(Jogo j) {
        if (this.placar > j.getPlacar()) {
            return 1;
        }
        if (this.placar < j.getPlacar()) {
            return -1;
        }
        return 0;
    }
}