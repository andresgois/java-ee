package br.com.casadocodigo.loja.service;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
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
import br.com.casadocodigo.loja.infra.MailSender;
import br.com.casadocodigo.loja.models.Compra;

@Path("/pagamento")
public class PagamentoService {
	
	@Inject
	private CompraDao compraDao;
	
	@Inject
	private PagamentoGateway pagamentoGateway;
	
	@Context
	private ServletContext context;

	@Inject
	private MailSender mailSender;
	
	/**
	 * Em geral usamos o JMS (Java Messaging Service) para fazer o assíncrono no Java. O JMS é uma especificação do Java EE com a qual temos a capacidade de enviar uma mensagem assíncrona. Para isso precisamos que o JMS passe por algumas configurações via annotations e um contexto do JMS dentro de pagamentoService.java:
	 */
	@Inject
	private JMSContext jmsContext;
	
	/**
	 * O destination é criado e configurado pelo servidor, mas em tempo de execução é criado pelo próprio servidor e o servidor precisa colocá-lo para nós, logo:
	 */
	@Resource(name="java:/jms/topics/CarrinhoComprasTopico")
	private Destination destination;
	
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
        
        /*
         * O JMSContext é um objeto que tem toda a comunicação com o servidor. Com o contexto do JMS podemos criar a mensagem. Para isso precisaremos de um produtor e de um "ouvidor" (listener) dessa mensagem. Então, dentro do método pagar() fazemos:
         */
        JMSProducer producer = jmsContext.createProducer();
        
        executor.submit(() -> {
            	try {
					pagamentoGateway.pagar(compra.getTotal());
					
					producer.send(destination, "olá");
					//enviaEmailELiberaOMeuUsuario();
					
					// pra onde vai
					URI reposnseUri = UriBuilder
							.fromPath("http://localhost:8080" + contextPath + "/index.xhtml")
							.queryParam("msg", "Compra realizada com sucesso!").build();
					Response response = Response.seeOther(reposnseUri).build();
					
					/* refatorando, enviando para outra classe
					String messageBody = "Sua compra foi realizada com sucesso, com o número de pedido " + compra.getUuid();
					// envia um e-mail
					mailSender.send("andre.s.gois3@gmail.com",
							compra.getUsuario().getEmail(),
							"Nova Compra na CDC", messageBody);
					*/
					
					// encerrou, então envia a resposta
					ar.resume(response);
				} catch (Exception e) {
					ar.resume(new WebApplicationException(e));
				}
            });
    }
}
