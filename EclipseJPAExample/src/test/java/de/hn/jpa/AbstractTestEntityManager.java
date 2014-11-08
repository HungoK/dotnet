package de.hn.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class AbstractTestEntityManager {
	static EntityManagerFactory entityManagerFactory;
	EntityManager em;

	@BeforeClass
	public static void setUp() throws Exception {
		entityManagerFactory = Persistence
				.createEntityManagerFactory("persistenceUnit");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		entityManagerFactory.close();
	}

	@Before
	public final void preparePersistence() throws Exception {
		em = entityManagerFactory.createEntityManager();

	}

	@After
	public final void closePersistence() throws Exception {
		if (em != null && em.isOpen()) {
			em.close();
			em = null;
		}
	}

	/**
	 * 
	 * @param objects
	 */
	public void persist(Object ...objects) {
		em.getTransaction().begin();
		for( Object object : objects ){
			em.persist(object);
		}
		em.getTransaction().commit();
		
	}

	/**
	 * 
	 * @param objects
	 */
	public void persistAndClearEm(Object ...objects ){
		persist(objects);
		em.clear();
	}
	/**
	 * 
	 * @param objects
	 */
	public void merge(Object ... objects){
		em.getTransaction().begin();
		for( Object object : objects ){
			em.merge(object);
		}
		em.getTransaction().commit();	
	}
	/**
	 * 
	 * @param objects
	 */
	public void mergeAndClearEm(Object ... objects ) {
		merge(objects);
		em.clear();
	}

	/**
	 * 
	 * @return
	 */
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	
}
