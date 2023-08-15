package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transacional
@Interceptor
public class GerenciadorDeTransacao implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager em;
	
	@AroundInvoke
	public Object executaTX(InvocationContext context) throws Exception {
		em.getTransaction().begin();
		// chama o dao
		Object obj = context.proceed();
		em.getTransaction().commit();
		return obj;
	}
}
