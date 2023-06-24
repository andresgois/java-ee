package io.github.andresgois.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import io.github.andresgois.entity.AgendamentoEmail;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN) //transaction is required
public class TesteTransacaoBMTAgendamentoEmailDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private UserTransaction userTransaction;
	
	public List<AgendamentoEmail> listar(){
		return em
				.createQuery("SELECT a FROM AgendamentoEmail a", AgendamentoEmail.class)
				.getResultList();
	}
	
	public void inserir(AgendamentoEmail agendamentoEmail) {
		em.persist(agendamentoEmail);
	}
	
	public List<AgendamentoEmail> listarPorNaoAgndado(){
		return em.createQuery("SELECT a FROM AgendamentoEmail a WHERE a.agendado = false"
				, AgendamentoEmail.class).getResultList();
	}
	
	public void alterar(AgendamentoEmail ag) {
		try {
			userTransaction.begin();
			em.merge(ag);
			userTransaction.commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}