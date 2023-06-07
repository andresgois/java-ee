package io.github.andresgois;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import io.github.andresgois.model.Conta;

public class CriaConta2 {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
        EntityManager em = emf.createEntityManager();
        
        Conta c1 = em.find(Conta.class, 1L);
        
        em.getTransaction().begin();
        
        c1.setSaldo(1000.0);
        
        em.getTransaction().commit();
        
        em.close();          
    }
    
}
