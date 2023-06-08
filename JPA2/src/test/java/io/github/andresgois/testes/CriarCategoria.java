package io.github.andresgois.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import io.github.andresgois.model.Categoria;

public class CriarCategoria {
    
    public static void main(String[] args) {
        Categoria categoria = new Categoria("Viagem");
        Categoria categoria2 = new Categoria("Negócios");
        
        EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("contas2");
        EntityManager em2 = emf2.createEntityManager();
        
        System.out.println("Inserindo categorias");
        em2.getTransaction().begin();
            em2.persist(categoria);
            em2.persist(categoria2);        
        em2.getTransaction().commit();
        em2.close();
        
    }
    
}
