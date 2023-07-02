package io.github.andresgois.resource;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.andresgois.dto.RelatorioDto;
import io.github.andresgois.model.Usuario;
import io.github.andresgois.services.UsuarioService;

@Path("/home")
@Stateless
public class Home {
	// http://localhost:8081/gerar-ralatorio/
	// http://localhost:8081/gerar-ralatorio/ws/home/usuario
	@Inject
	UsuarioService service;
    
    @GET
    public String home(){
        return "Home Stateless";
    }
    
    @GET
    @Path("/usuario")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response users() {
    	List<Usuario> users = service.listaUsuarios();
    	return Response.ok().entity(users).build();
    }
    
    @GET
    @Path("/usuario/{estado}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listaPorEstado(@PathParam("estado") String estado) {
    	List<Usuario> users = service.listaUsuariosPorEstado(estado);
        return Response.ok()
          .entity(users)
          .build();
    }
    
    @GET
    @Path("/usuario/{estado}/relatorio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response relatorio(@PathParam("estado") String estado) {
    	RelatorioDto r = service.relatorio(estado);
        return Response.ok()
          .entity(r)
          .build();
    }
}
