package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe fornecedora de conexões para o acesso ao banco de dados utilizando JPA.
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
public class JPAConnectionFactory {
    
    /**
     * Objeto de referencia ao banco de dados.
     * <p>
     * Criado a partir das configurações do persistence.xml.
     * Caso seja alterado o banco, pode-se apenas alterar a referência ao persistence.xml.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     */
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("desafio_publica");
    
    /**
     * Método que fornece conexão ao banco de dados
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return EntityManager: objeto de conexão ao banco de dados.
     */
    public static EntityManager getEntityManager() {
        EntityManager em = null;
        try {
            em = EMF.createEntityManager();
        } catch(Exception e){
            System.out.println("Erro! " + e);
        }
        return em;
    }
}