package pcrf.betha.base.svc;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.solder.core.Veto;

import pcrf.betha.base.dao.Dao;
import pcrf.betha.base.entity.Entity;
import pcrf.betha.util.GeneralException;

@Veto
public abstract class Svc<T extends Entity> implements Serializable {
	private static final long serialVersionUID = 1L;

	public EntityManager getEntityManager() {
		return getDao().getEntityManager();
	}
	
	public abstract Dao<T> getDao();
	
	public T salvar(T entity) {
		return this.getDao().merge(entity);
	}

	public T novo() {
		return this.getDao().novo();
	}

	public T obter(Object id) {
		return this.getDao().find(id);
	}

	public T obterEdicao(Object id) {
		if (id == null)
			throw new GeneralException("Código não informado");
		
		return this.getDao().find(id);
	}

	public List<T> obterTodos() {
		return this.getDao().getAll();
	}
	
}
