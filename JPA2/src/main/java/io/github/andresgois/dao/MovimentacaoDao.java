package io.github.andresgois.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import io.github.andresgois.model.MediaComData;
import io.github.andresgois.model.Movimentacao;

public class MovimentacaoDao {
    
    private EntityManager em;
    
    public MovimentacaoDao(EntityManager em) {
        this.em = em;
    }
    
    public List<Movimentacao> getMovimentacoesFiltradasPorData(Integer dia, Integer mes, Integer ano) {
        // Necessário um builder para a consulta
        CriteriaBuilder builder = em.getCriteriaBuilder();
        // Monta a query
        CriteriaQuery<Movimentacao> query = builder.createQuery(Movimentacao.class);
        // from será a tabela
        Root<Movimentacao> root = query.from(Movimentacao.class);
        List<Predicate> predicates = new ArrayList<>();
        
        if (dia != null) {            // day(m.data) = :pDia
            Predicate predicate = builder.equal(builder.function("day", Integer.class, root.get("data")), dia);
            predicates.add(predicate);
        }
        
        if (mes != null) {            // month(m.data) = :pMes
            Predicate predicate = builder.equal(builder.function("month", Integer.class, root.get("data")), mes);
            predicates.add(predicate);
        }
        
        if (ano != null) {            // month(m.data) = :pAno
            Predicate predicate = builder.equal(builder.function("year", Integer.class, root.get("data")), ano);
            predicates.add(predicate);
        }
        
        query.where(predicates.toArray(new Predicate[0]));
        
        TypedQuery<Movimentacao> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
    
    public List<MediaComData> getMediaDiariaDasMovimentacoes() {
        //String jpql = "select new io.github.andresgois.model.MediaComData(avg(m.valor), day(m.data), month(m.data)) from Movimentacao m group by day(m.data), month(m.data), year(m.data)";
        
        TypedQuery<MediaComData> query = em.createNamedQuery("mediaDiariaMovimentacos", MediaComData.class);
        return query.getResultList();
    }
    
    public BigDecimal getSomaDasMovimentacoes() {
        String jpql = "select sum(m.valor) from Movimentacao m ";
        
        TypedQuery<BigDecimal> query = em.createQuery(jpql, BigDecimal.class);
        BigDecimal somaDasMovimentacoes = query.getSingleResult();
        
        return somaDasMovimentacoes;
    }
    
    public BigDecimal getSomaDasMovimentacoesComCriteria() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> query = builder.createQuery(BigDecimal.class);
        
        Root<Movimentacao> root = query.from(Movimentacao.class);
        Expression<BigDecimal> sum = builder.sum(root.<BigDecimal>get("valor"));
        
        query.select(sum);
        
        TypedQuery<BigDecimal> typedQuery = em.createQuery(query);
        
        return typedQuery.getSingleResult();
    }
}
