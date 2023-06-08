package io.github.andresgois;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import io.github.andresgois.model.Conta;
import io.github.andresgois.model.Movimentacao;

public class TesteJPQL {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
        EntityManager em = emf.createEntityManager();

        Conta conta = new Conta();
        conta.setId(3L);
        //String jpql = "select m from Movimentacao m where m.conta = :pConta";
        
        String jpql = "select m from Movimentacao m where m.conta = :pConta order by m.valor desc";

        //Query query = em.createQuery(jpql);
        TypedQuery<Movimentacao> query = em.createQuery(jpql, Movimentacao.class);
        query.setParameter("pConta", conta);
        
        //@SuppressWarnings("unchecked")
        List<Movimentacao> resultList = query.getResultList();
        

        for (Movimentacao movimentacao : resultList) {
            System.out.println("Descrição: " + movimentacao.getDescricao());
            System.out.println("Tipo: " + movimentacao.getTipoMovimentacao());
        }
    }
        
}
