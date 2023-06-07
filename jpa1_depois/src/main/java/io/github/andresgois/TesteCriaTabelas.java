package io.github.andresgois;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import io.github.andresgois.model.Conta;

public class TesteCriaTabelas {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
        EntityManager em = emf.createEntityManager();
        
        Conta conta = new Conta();
        conta.setTitular("Leonardo");
        conta.setNumero(1234);
        conta.setAgencia(4321);
        
        em.getTransaction().begin();
        em.persist(conta);
        em.getTransaction().commit();
        
        em.close();        
    }
    

}
