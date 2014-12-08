package pcrf.betha.base.bean;

import java.util.List;

import javax.faces.context.FacesContext;

import org.jboss.solder.core.Veto;

import pcrf.betha.base.entity.Entity;
import pcrf.betha.base.fcd.Fcd;

@Veto
public abstract class ConsBean<T extends Entity> extends Bean {
	private static final long serialVersionUID = 1L;

	protected abstract Fcd<T> getFcd();

	protected List<T> lista;
	
	protected T selected;

	@Override
	public void begin() {
		if (FacesContext.getCurrentInstance().isPostback())
			return;

		super.begin();

		this.obterDados();
	}
	
	protected void obterDados() {
		this.lista = this.getFcd().obterTodos();
	}

	public List<T> getLista() {
		return lista;
	}

	public void prepararInativacao(T row) {
		this.selected = row;
	}
	
	public void inativar() {
	}

}
