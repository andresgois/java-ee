package io.github.andresgois.testes;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import io.github.andresgois.model.MediaComData;

public class TestaMediaDiariaDasMovimentacoes {
public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas2");
        EntityManager em = emf.createEntityManager();
        
        String jpql = "select new io.github.andresgois.model.MediaComData(avg(m.valor), day(m.data), month(m.data)) from Movimentacao m group by day(m.data), month(m.data), year(m.data)";
        
        TypedQuery<MediaComData> query = em.createQuery(jpql, MediaComData.class);
        List<MediaComData> mediaDasMovimentacoes = query.getResultList();//query.getSingleResult();
        
        //System.out.println("A média das movimentações é: "+mediaDasMovimentacoes);
        
        for (MediaComData resultado : mediaDasMovimentacoes) {
            System.out.println("A média das movimentacoes do dia " + resultado.getDia() + "/" + resultado.getMes() + " é: " + resultado.getValor());
        }
        
    }
}
