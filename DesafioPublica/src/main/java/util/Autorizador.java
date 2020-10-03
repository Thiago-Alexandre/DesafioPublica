package util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import model.Jogador;

/**
 * Classe responsável por interceptar requisições da aplicação
 * <p>
 * implements PhaseListener: tipo de objeto notificado no início e término de cada etapa do ciclo de vida do JSF.
 * </p>
 * @author Thiago Alexandre Buerger
 * @version 1.0
 */
public class Autorizador implements PhaseListener{

    /**
     * Método sobrescrito para realizar processos quando a fase específica acaba de ser concluída
     * <p>
     * Deverá ser verificado qual página o usuário deseja ir.<br>
     * Ser for para a página de login, o jogador da sessão, caso houver, será removido e o usuário será redirecionado.<br>
     * Se for para outra página, deverá ser verificado se existe algum usuário na sessão.
     * Caso existir, deverá ser verificado se a página é válida.
     * Caso for válida, o usuário poderá ser redirecionado.<br>
     * Caso a página não for válida ou não existir usuário na sessão, o usuário será redirecionado para a página de login.
     * </p>
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param evento PhaseEvent
     */
    @Override
    public void afterPhase(PhaseEvent evento) {
        FacesContext context = evento.getFacesContext();
        String nomePagina = context.getViewRoot().getViewId();
        if(nomePagina.equals("/login.xhtml")) {
            context.getExternalContext().getSessionMap().remove("jogadorLogado");
            return;
        }
        Jogador jogadorLogado = (Jogador) context.getExternalContext().getSessionMap().get("jogadorLogado");
        if(jogadorLogado != null) {
            if (nomePagina.equals("/index.xhtml") || nomePagina.equals("/temporada.xhtml") || nomePagina.equals("/jogador.xhtml") || nomePagina.equals("/jogo.xhtml")) {
                return;
            }
        }
        NavigationHandler handler = context.getApplication().getNavigationHandler();
        handler.handleNavigation(context, null, "/login?faces-redirect=true");
        context.renderResponse();
    }

    /**
     * Método sobrescrito para realizar processos quando a fase específica está prestes a começar
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @param pe PhaseEvent
     */
    @Override
    public void beforePhase(PhaseEvent pe) {}
    
    /**
     * Método sobrescrito para retornar o identificador da fase de processamento da solicitação
     * @author Thiago Alexandre Buerger
     * @version 1.0
     * @return PhaseId: retorna o identificador da fase de processamento da solicitação
     */
    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}