package br.com.casadocodigo.loja.service;

import java.net.URI;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import br.com.casadocodigo.loja.daos.CompraDao;
import br.com.casadocodigo.loja.models.Compra;

@Path("/pagamento")
public class PagamentoService {
	
	@Inject
	private CompraDao compraDao;
	
	@Inject
	private PagamentoGateway pagamentoGateway;
	
	@Context
	private ServletContext context;

    @POST
    public Response pagar(@QueryParam("uuid") String uuid) {
    	System.out.println("Aqui");
        System.out.println(uuid);
        
        Compra compra = compraDao.buscaPorUuid(uuid);
        pagamentoGateway.pagar(compra.getTotal());
        
        URI reposnseUri = UriBuilder.fromPath("http://localhost:8080"+
        		context.getContextPath()+"/index.xhtml")
        		.queryParam("msg","Compra realizada com sucesso!")
        		.build();
        Response response = Response.seeOther(reposnseUri).build();
        		
        return response;
    }
}
