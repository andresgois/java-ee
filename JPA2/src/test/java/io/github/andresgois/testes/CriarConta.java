package io.github.andresgois.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import io.github.andresgois.model.Conta;

public class CriarConta {
    
    public static void main(String[] args) {
        Conta contat = new Conta();
        contat.setAgencia(123123);
        contat.setNumero(456456);
        contat.setSaldo(300.0);
        contat.setTitular("Andre");
        
        EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("contas2");
        EntityManager em1 = emf1.createEntityManager();
        
        em1.getTransaction().begin();
        System.out.println("Inseriu Conta");
        em1.persist(contat);
        em1.getTransaction().commit();
        em1.close();
        
    }
    
}
