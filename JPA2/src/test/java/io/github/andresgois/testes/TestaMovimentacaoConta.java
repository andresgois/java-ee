package io.github.andresgois.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import io.github.andresgois.model.Conta;
import io.github.andresgois.model.Movimentacao;

public class TestaMovimentacaoConta {
  
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas2");
        EntityManager em = emf.createEntityManager();
        
        // retorno um estado managed
        Movimentacao movimentacao = em.find(Movimentacao.class, 1L);
        Conta conta = movimentacao.getConta();
        
        int quantidadeDeMovimentacoes = conta.getMovimentacoes().size();
        
        System.out.println("Quantidade de movimentacoes: " + quantidadeDeMovimentacoes);
        System.out.println("Titular da conta: " + conta.getTitular());
    }
    
}
