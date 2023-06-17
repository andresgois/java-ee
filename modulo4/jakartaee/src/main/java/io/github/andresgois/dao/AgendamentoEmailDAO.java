package io.github.andresgois.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import io.github.andresgois.entity.AgendamentoEmail;

@Stateless
public class AgendamentoEmailDAO {

	/*-----------------------------------
	 * Resposabilidade de INFRA
	 * -----------------------------------
	 */
	@PersistenceContext
	private EntityManager em;
	
	public List<AgendamentoEmail> listar(){
		return em
				.createQuery("SELECT a FROM AgendamentoEmail a", AgendamentoEmail.class)
				.getResultList();
	}
	
	
	/*-----------------------------------
	 * Resposabilidade do desenvolvedor
	 */
	/*-----------------------------------
	private EntityManager em;
	
	public AgendamentoEmailDAO() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AgendamentoEmailDS");
		this.em = entityManagerFactory.createEntityManager();
	}
	
	public List<AgendamentoEmail> listar(){
		em.getTransaction().begin();
		List<AgendamentoEmail> result = em
				.createQuery("SELECT a FROM AgendamentoEmail a", AgendamentoEmail.class)
				.getResultList();
		em.getTransaction().commit();
		em.close();
		
		return result;
	}*/
}