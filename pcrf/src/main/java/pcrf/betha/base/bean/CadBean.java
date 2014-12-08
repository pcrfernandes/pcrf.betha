package pcrf.betha.base.bean;

import javax.faces.context.FacesContext;

import org.jboss.solder.core.Veto;

import pcrf.betha.base.entity.Entity;
import pcrf.betha.base.fcd.Fcd;
import pcrf.betha.util.FacesUtils;

@Veto
public abstract class CadBean<T extends Entity> extends Bean {
	private static final long serialVersionUID = 1L;

	protected abstract Fcd<T> getFcd();

	public abstract String getViewRedir();

	private Integer id;
	
	private T entity;

	private Boolean editing = false;

	@Override
	public void begin() {
		if (FacesContext.getCurrentInstance().isPostback())
			return;

		super.begin();

		if (id == null) {
			this.entity = this.getFcd().novo();
		} else {
			this.entity = this.getFcd().obterEdicao(id);
			this.editing = true;
		}
		
	}

	public void salvar() {
		this.getFcd().salvar(entity);
		
		FacesUtils.redir(this.getViewRedir());
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}
	
	public Boolean getEditing() {
		return editing;
	}

}
