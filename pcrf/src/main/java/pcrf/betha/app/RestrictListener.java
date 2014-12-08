package pcrf.betha.app;

import javax.enterprise.event.Observes;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.inject.Inject;

import org.jboss.seam.faces.event.qualifier.After;
import org.jboss.seam.faces.event.qualifier.InvokeApplication;
import org.jboss.seam.faces.event.qualifier.RestoreView;

import pcrf.betha.app.session.SessionBean;
import pcrf.betha.util.FacesUtils;

public class RestrictListener {

	@Inject
	private SessionBean session;
	
	private void verify(PhaseEvent event) {
		FacesContext ctx = event.getFacesContext();
 		String url = ctx.getViewRoot().getViewId();
 
 		if (url.contains("login.xhtml") || url.contains("/teste-rest"))
 			return;

 		if (!session.isActive()) {
			FacesUtils.redir("/login.xhtml");
 		}

 		if (url.contains("/admin") && !session.isAdmin())
 			FacesUtils.redir("/index.xhtml");
	}

	public void afterRestoreView(@Observes @After @RestoreView PhaseEvent event, NavigationHandler navHandler) {
		verify(event);
	}
	
	public void afterInvokeApp(@Observes @After @InvokeApplication PhaseEvent event, NavigationHandler navHandler) {
		verify(event);
	}

}
