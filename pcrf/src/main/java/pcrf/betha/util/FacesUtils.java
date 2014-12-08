package pcrf.betha.util;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
public class FacesUtils implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void redir(String url) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		try {
			ctx.getExternalContext().redirect(ctx.getApplication().getViewHandler().getActionURL(ctx, url));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
