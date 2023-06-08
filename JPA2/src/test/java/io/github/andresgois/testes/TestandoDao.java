package io.github.andresgois.testes;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import io.github.andresgois.dao.MovimentacaoDao;
import io.github.andresgois.model.MediaComData;

public class TestandoDao {
    
    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas2");
        EntityManager em = emf.createEntityManager();
        
        List<MediaComData> mediaDasMovimentacoes = new MovimentacaoDao(em).getMediaDiariaDasMovimentacoes();

        for (MediaComData resultado : mediaDasMovimentacoes) {
            System.out.println("A média das movimentacoes do dia " + resultado.getDia() + "/" + resultado.getMes() + " é: " + resultado.getValor());
        }
    }
    
}
