package com.polytech.RestService;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.polytech.test.ANP;

@Path("ANP")
public class WebServer {

	private ANP anp;
	
	public WebServer() {
		anp = new ANP();
		
	}
	
	 @GET
	 @Path("{id}/{name}/{name2}")
	 @Produces("application/xml")
	 public Response getXmlWithParams(@PathParam("id") String id,@PathParam("name") String name,@PathParam("name2") String name2) {
	     System.out.println(id);
	     System.out.println(name);System.out.println(name2);
	     return Response
	          .status(Status.OK)
	          .entity("<bonjour>Bonjour  " + name + " / "+name2+"</bonjour>")
	          .build();
	 }

	@GET
	@Produces("application/xml")
	public String getXml() {
	    return "<bonjour>Bonjour ENSMA *********************</bonjour>";
	}
}
