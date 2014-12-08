package pcrf.betha.base.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.jboss.solder.core.Veto;

import pcrf.betha.base.entity.Entity;
import pcrf.betha.util.TypeResolver;

@Veto
public abstract class Dao<T extends Entity> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	protected Class<T> persistentClass;
	
	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	@SuppressWarnings("unchecked")
	public Dao() {
        Class<?>[] typeArguments = TypeResolver.resolveArguments(getClass(), Dao.class);
        this.persistentClass = (Class<T>) typeArguments[0];
    }

	public T novo() {
		try {
			return this.persistentClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public T merge(T entity) {
		return this.getEntityManager().merge(entity);
	}

	public T find(Object id) {
		return this.getEntityManager().find(persistentClass, id);
	}
	
	public List<T> getAll() {
		/*Query query = this.getEntityManager().createQuery("SELECT e FROM " + persistentClass.getName() + " e");
		return (List<T>) query.getResultList();*/
		
		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(persistentClass);
        CriteriaQuery<T> all = cq.select(cq.from(persistentClass));

        return this.getEntityManager().createQuery(all).getResultList();
	}
	
}
