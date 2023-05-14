package br.imd.restServer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.imd.model.HelloWorld;

@Path("hello")
public class RestService {

	public RestService() {
		// TODO Auto-generated constructor stub
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response helloWorld() {

		HelloWorld helloWorld = new HelloWorld("Hello World !");

		return Response.ok(helloWorld).build();
	}
	
	
	@GET
	@Path("hello-people/{nome}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response helloPeople(@PathParam("nome") String nome) {

		HelloWorld helloWorld = new HelloWorld("Hello "+nome+" !");

		return Response.ok(helloWorld).build();
	}
	
	@GET
	@Path("hello-people2")
	@Produces(MediaType.APPLICATION_JSON)
	public Response helloPeople2(@QueryParam("nome") String nome, 
			@QueryParam("sobrenome") String sobrenome) {

		HelloWorld helloWorld = new HelloWorld("Hello "+nome+" "+sobrenome+" !");

		return Response.ok(helloWorld).build();
	}
	
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response helloPost(String nome) {
		
		HelloWorld helloWorld = new HelloWorld("Hello "+nome+" !");

		return Response.ok(helloWorld).build();
	}
	
	
	
	
}
