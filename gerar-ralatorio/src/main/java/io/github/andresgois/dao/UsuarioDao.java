package io.github.andresgois.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import io.github.andresgois.model.Usuario;

@Stateless
public class UsuarioDao {

	@PersistenceContext
	private EntityManager em;
	
	public void create(Usuario u) {
		//em = EntityManagerFactoryGeneric.get().createEntityManager();
		em.persist(u);
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> findAll() {
		Query query =  em.createQuery("select u from Usuario u");
		return query.getResultList();
	}
	
	public Usuario findById(Long id) {
		Query query = em
				.createQuery("select u from Usuario u where 1=1 and id =:pId")
				.setParameter("pId", id);
		return (Usuario) query.getSingleResult();
	}
	
	public Usuario findByUser(Usuario u) {
		Long id = u.getId();
		return this.findById(id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> findByUserPerState(String e) {
		Query query = em
				.createQuery("select u from Usuario u where 1=1 and u.estado =:pEstado")
				.setParameter("pEstado", e);
		return query.getResultList();
	}
}
