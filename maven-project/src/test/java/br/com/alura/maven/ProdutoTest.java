package br.com.alura.maven;

import junit.framework.TestCase;
import org.junit.Test;

public class ProdutoTest extends TestCase {

    public void testVerificaPrecoComImposto() {
        Produto bala = new Produto("juquinha", 0.10);
        System.out.println("Teste");
        assertEquals(0.11, bala.getPrecoComImposto(), 0.0001);
    }
}