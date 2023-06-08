package io.github.andresgois.testes;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import io.github.andresgois.dao.MovimentacaoDao;
import io.github.andresgois.model.Movimentacao;

public class TestaMovimentacoesFiltradaPorDataCriteria {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas2");
        EntityManager em = emf.createEntityManager();
        
        MovimentacaoDao mov = new MovimentacaoDao(em);
        List<Movimentacao> m = mov.getMovimentacoesFiltradasPorData(12, null, 2017);
        
        for(Movimentacao movi: m) {
            System.out.println("Descrição -> "+movi.getDescricao());
            System.out.println("Valor -> "+movi.getValor());
            System.out.println(" --------------- ");
        }
    }
    
}
