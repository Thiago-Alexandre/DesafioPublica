package model;

import java.io.Serializable;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Classe responsável por realizar as validações
 * <p>
 * ValidatorFactory: Objeto utilizado para construir um objeto do tipo Validator.
 * Validator: Objeto validador
 * Set ConstraintViolation T: Armazena mensagens de saída de erros.
 * para poderem ser testados utilizando parâmetros aceitáveis.
 * </p>
 * @author Thiago Alexandre Buerger
 * @version 1.0
 * @param <T> Class T: Define sobre qual classe a validação será efetuada
 */
public class Validacao<T> implements Serializable{
    
    private ValidatorFactory factory;
    private Validator validator;
    private Set<ConstraintViolation<T>> erros;
    private final Class<T> classe;
    
    /**
     * Construtor da classe Validacao
     * <p>
     * O próprio construtor da classe faz a validação do objeto passado por parâmetro 
     * e armazena os erros encontrados.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param classe Class T: Define sobre qual classe a validação será efetuada
     * @param objeto T: objeto que será validado, definido pela classe informada
     */
    public Validacao(Class<T> classe, T objeto) {
        this.classe = classe;
        try {
            factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
            erros = validator.validate(objeto);
        } catch (Exception e) {
            System.out.println("Erro! " + e);
        }
    }

    /**
     * Método de retorno dos erros encontrados
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return Set ConstraintViolation T: lista de erros encontrados na validação
     */
    public Set<ConstraintViolation<T>> getErros(){
        return erros;
    }
}