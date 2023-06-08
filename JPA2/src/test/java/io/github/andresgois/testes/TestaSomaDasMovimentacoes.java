package io.github.andresgois.testes;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import io.github.andresgois.dao.MovimentacaoDao;
import io.github.andresgois.model.Movimentacao;

public class TestaSomaDasMovimentacoes {
    
public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas2");
        EntityManager em = emf.createEntityManager();

        /*CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> query = builder.createQuery(BigDecimal.class);
        
        //from Movimentacao m
        Root<Movimentacao> root = query.from(Movimentacao.class);
        
        // String jpql = "select sum(m.valor) from Movimentacao m ";
        // String jpql = "select avg(m.valor) from Movimentacao m ";
        //sum(m.valor)
        Expression<BigDecimal> sum = builder.sum(root.<BigDecimal>get("valor"));
        
        //select sum(..)
        query.select(sum);
        
        TypedQuery<BigDecimal> typedQuery = em.createQuery(query);
        
        System.out.println("A soma das movimentacoes é: " + typedQuery.getSingleResult());*/
        
        MovimentacaoDao dao = new MovimentacaoDao(em);
        BigDecimal soma = dao.getSomaDasMovimentacoesComCriteria();
        System.out.println("A soma das movimentacoes é: " + soma);
    }
}
