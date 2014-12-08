package pcrf.betha.base.bean;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.jboss.solder.core.Veto;

@Veto
public abstract class Bean implements Serializable {
	private static final long serialVersionUID = 1L;

	public void begin() {
		if (FacesContext.getCurrentInstance().isPostback())
			return;
		
	}

}
