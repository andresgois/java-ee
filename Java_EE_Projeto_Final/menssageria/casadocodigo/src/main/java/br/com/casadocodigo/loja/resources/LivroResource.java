package br.com.casadocodigo.loja.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;

import br.com.casadocodigo.loja.daos.LivroDao;
import br.com.casadocodigo.loja.models.Livro;

@Path("livros")
public class LivroResource {

    @Inject
    private LivroDao dao;

    /*@GET
    @Path("lancamentosJson")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Livro> ultimosLancamentosJson() {
        return dao.ultimosLancamentos();
    }
    
    @GET
    @Path("lancamentosXml")
    @Produces({MediaType.APPLICATION_XML})
    @Wrapped(element = "livros")
    public List<Livro> ultimosLancamentosXml() {
        return dao.ultimosLancamentos();
    }*/

    @GET
    @Path("lancamentos")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Wrapped(element = "livros")
    public List<Livro> ultimosLancamentosJson() {
        return dao.ultimosLancamentos();
    }
}
