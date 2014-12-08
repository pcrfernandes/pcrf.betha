package pcrf.betha.base.dao;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {
	@SuppressWarnings("unused")
	@Produces @Default
	@PersistenceContext(unitName="MySQLDS")
	private EntityManager entityManager;	
}
