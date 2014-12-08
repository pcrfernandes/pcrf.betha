package pcrf.betha.base.fcd;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.solder.core.Veto;

import pcrf.betha.base.entity.Entity;
import pcrf.betha.base.svc.Svc;

@Veto
public abstract class Fcd<T extends Entity> implements Serializable {
	private static final long serialVersionUID = 1L;

	public abstract Svc<T> getSvc();
	
	public EntityManager getEntityManager() {
		return getSvc().getEntityManager();
	}

	public T novo() {
		return this.getSvc().novo();
	}

	public T obter(Object id) {
		return this.getSvc().obter(id);
	}

	public T obterEdicao(Object id) {
		return this.getSvc().obterEdicao(id);
	}

	public List<T> obterTodos() {
		return this.getSvc().obterTodos();
	}

	public T salvar(T entity) {
		return this.getSvc().salvar(entity);
	}

}
