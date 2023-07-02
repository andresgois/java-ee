package io.github.andresgois.config;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.github.andresgois.model.Usuario;

@Singleton
@Startup
public class InitConfig {
	
	@PersistenceContext
	private EntityManager em;
	private static final Logger LOGGER = Logger.getLogger(InitConfig.class.getName());
	public static List<Usuario> u = new ArrayList<Usuario>();
	
	static {
		init();
	}

	@PostConstruct
	void inicio() {
		System.out.println("POSTCONSTRUCT");
		Usuario u1 = new Usuario("André", "CE");
		Usuario u2 = new Usuario("Priscila", "CE");
		Usuario u3 = new Usuario("Andreia", "CE");
		Usuario u4 = new Usuario("Beatriz", "CE");
		Usuario u5 = new Usuario("Marcos", "RJ");
		Usuario u6 = new Usuario("Lucas", "RJ");
		Usuario u7 = new Usuario("Maria", "SP");
		
		em.persist(u1);
		em.persist(u2);
		em.persist(u3);
		em.persist(u4);
		em.persist(u5);
		em.persist(u6);
		em.persist(u7);
	}
	public static void init() {
		LOGGER.info("------------ INICIO ------------");
		//em = EntityManagerFactoryGeneric.get().createEntityManager();
		Usuario u1 = new Usuario("André", "CE");
		Usuario u2 = new Usuario("Priscila", "CE");
		Usuario u3 = new Usuario("Andreia", "CE");
		Usuario u4 = new Usuario("Beatriz", "CE");
		Usuario u5 = new Usuario("Marcos", "RJ");
		Usuario u6 = new Usuario("Lucas", "RJ");
		Usuario u7 = new Usuario("Maria", "SP");
		
		u.add(u1);
		//em.getTransaction().begin();
		/*em.persist(u1);
		em.persist(u2);
		em.persist(u3);
		em.persist(u4);
		em.persist(u5);
		em.persist(u6);
		em.persist(u7);*/
		//em.getTransaction().commit();
	}
}
