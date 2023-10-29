package br.com.casadocodigo.loja.service;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
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
	
	private static ExecutorService executor = Executors.newFixedThreadPool(50);

	// 1 forma
    /*@POST
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
    }*/
	
	
	//2 forma
	/*@POST
    public void pagar(@Suspended final AsyncResponse ar,@QueryParam("uuid") String uuid) {
        Compra compra = compraDao.buscaPorUuid(uuid);
        
        executor.submit(new Runnable() {

            @Override
            public void run() {
            	try {
					pagamentoGateway.pagar(compra.getTotal());
					URI reposnseUri = UriBuilder
							.fromPath("http://localhost:8080" + context.getContextPath() + "/index.xhtml")
							.queryParam("msg", "Compra realizada com sucesso!").build();
					Response response = Response.seeOther(reposnseUri).build();
					ar.resume(response);
				} catch (Exception e) {
					ar.resume(new WebApplicationException(e));
				}
            }
        });
    }*/
	
	// 3 forma
	@POST
    public void pagar(@Suspended final AsyncResponse ar,@QueryParam("uuid") String uuid) {
        Compra compra = compraDao.buscaPorUuid(uuid);
        String contextPath = context.getContextPath();
        
        executor.submit(() -> {
            	try {
					pagamentoGateway.pagar(compra.getTotal());
					// pra onde vai
					URI reposnseUri = UriBuilder
							.fromPath("http://localhost:8080" + contextPath + "/index.xhtml")
							.queryParam("msg", "Compra realizada com sucesso!").build();
					Response response = Response.seeOther(reposnseUri).build();
					// encerrou, então envia a resposta
					ar.resume(response);
				} catch (Exception e) {
					ar.resume(new WebApplicationException(e));
				}
            });
    }
}
