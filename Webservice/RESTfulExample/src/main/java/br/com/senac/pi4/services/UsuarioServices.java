package br.com.senac.pi4.services;
 
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
 
@Path("/usuario")
public class UsuarioServices {
 
	@GET
	@Path("/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("param") String loginUsuario) {
 
		System.out.println("buscando usuario com login "+loginUsuario);
 
		//busca o usuario do banco de dados
		
		//aqui estamos criando um usuario mock
		Usuario user = new Usuario();
		user.setAtivo((byte)1);
		user.setLoginUsuario(loginUsuario);
		user.setSenhaUsuario("segredo");
		user.setNomeUsuario("Fulaninho da Silva");
		user.setTipoPerfil("A");
		
		Gson gson = new Gson ();
		
		String jsonUser = gson.toJson(user);
		return Response.status(200).entity(jsonUser).build();
 
	}
	
	@POST	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveUser(Usuario user) {
 
		Gson gson = new Gson ();
		String jsonUser = gson.toJson(user);
		System.out.println("salvando usuario "+jsonUser);
		return Response.status(200).entity("").build();
 
	}
 
}