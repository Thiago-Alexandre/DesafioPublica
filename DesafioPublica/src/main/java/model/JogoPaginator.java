package model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * Classe responsável por disponibilizar dados de Jogo paginados
 * <p>
 * extends LazyDataModel: objeto utilizado pelo componente dataTable do PrimeFaces.
 * </p>
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
public class JogoPaginator extends LazyDataModel implements Serializable{
    
    /**
     * Filtro utilizado para a paginação
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private JogoFilter filtro;
    
    /**
     * Objeto DAO para obter dados de Jogo
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private final JogoDAO dao;

    /**
     * Construtor da classe JogoPaginator
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param filtro: filtro utilizado para a paginação
     */
    public JogoPaginator(JogoFilter filtro) {
        this.filtro = filtro;
        dao = new JogoDAO();
    }
    
    /**
     * Método sobrescrito load
     * <p>
     * Método utilizado pelo componente dataTable do PrimeFaces para realizar paginação real.
     * </p>
     * @param first int: primeiro registro da página
     * @param pageSize int: tamanho de registros por página
     * @param sortField String: não utilizado
     * @param sortOrder SortOrder: não utilizado
     * @param filters Map: não utilizado
     * @return List Jogo: retorna uma lista de jogos
     */
    @Override
    public List<Jogo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
        filtro.setPrimeiroRegistro(first);
        filtro.setQuantidadeRegistros(pageSize);
        filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
        filtro.setPropriedadeOrdenacao(sortField);
        setRowCount(dao.quantidadeFiltrados(filtro));
        return dao.filtrados(filtro);
    }
}