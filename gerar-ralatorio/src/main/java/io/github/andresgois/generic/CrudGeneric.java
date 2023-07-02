package io.github.andresgois.generic;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CrudGeneric<T> {

	@PersistenceContext
	private EntityManager em;
	
	public T insert(T t) {
		//return em.persist(t);
		return null;
	}
	
}
