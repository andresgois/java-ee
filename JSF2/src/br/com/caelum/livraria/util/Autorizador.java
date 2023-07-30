package br.com.caelum.livraria.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.caelum.livraria.modelo.Usuario;

public class Autorizador implements PhaseListener{

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent ev) {
		FacesContext context = ev.getFacesContext();
        String nomePagina = context.getViewRoot().getViewId();
        System.out.println(nomePagina);
        
        if("/login.xhtml".equals(nomePagina)) return;
        
        Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
        if(usuarioLogado != null) return;
        
        NavigationHandler handle = context.getApplication().getNavigationHandler();
        handle.handleNavigation(context, null, "/login?faces-redirect=true");
        context.renderResponse();      
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("BEFORE");
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
