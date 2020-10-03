package model;

import java.io.Serializable;

/**
 * Classe responsável pelos filtros de pesquisa e paginação de objetos do tipo Jogo
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
public class JogoFilter implements Serializable{
    
    /**
     * Primeiro registro da paginação
     * <p>
     * Indica onde irá começar cada página da paginação.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private int primeiroRegistro;
    
    /**
     * Total de registros por página
     * <p>
     * Indica quantos registros deverão ser exibidos por página, calculando assim quantas páginas irão ser criadas.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private int quantidadeRegistros;
    
    /**
     * Propriedade sobre a qual será realizada a ordenação
     * <p>
     * Indica qual propriedade será utilizada para realizar a ordenação.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private String propriedadeOrdenacao;
    
    /**
     * Propriedade sobre qual ordenação será realizada
     * <p>
     * Indica qual ordenação será realizada na paginação.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private boolean ascendente;
    
    /**
     * Filtro de temporada
     * <p>
     * Indica um filtro utilizado na consulta. Neste caso, os jogos poderão ser filtrados por temporada.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private Temporada temporada;
    
    /**
     * Filtro de jogador
     * <p>
     * Indica um filtro utilizado na consulta. Neste caso, os jogos poderão ser filtrados por jogador.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private Jogador jogador;

    public int getPrimeiroRegistro() {
        return primeiroRegistro;
    }

    public void setPrimeiroRegistro(int primeiroRegistro) {
        this.primeiroRegistro = primeiroRegistro;
    }

    public int getQuantidadeRegistros() {
        return quantidadeRegistros;
    }

    public void setQuantidadeRegistros(int quantidadeRegistros) {
        this.quantidadeRegistros = quantidadeRegistros;
    }

    public String getPropriedadeOrdenacao() {
        return propriedadeOrdenacao;
    }

    public void setPropriedadeOrdenacao(String propriedadeOrdenacao) {
        this.propriedadeOrdenacao = propriedadeOrdenacao;
    }

    public boolean isAscendente() {
        return ascendente;
    }

    public void setAscendente(boolean ascendente) {
        this.ascendente = ascendente;
    }

    public Temporada getTemporada() {
        return temporada;
    }

    public void setTemporada(Temporada temporada) {
        this.temporada = temporada;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }
}