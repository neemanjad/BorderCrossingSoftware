package etf.project.logic;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import etf.project.model.PasswordContainer.PasswordContainer;
import etf.project.model.user.User;
import etf.project.utility.InMemoryDataBaseUtility;


@Path("/users")
public class InMemoryDataBaseRest {
	
	InMemoryDataBaseUtility service;
	
	public InMemoryDataBaseRest() {
		service = new InMemoryDataBaseUtility();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getUsers(){
		return service.getUsers();
	}
	
	@GET
	@Path("/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("userName") String userName) {
		
		User tempUser = service.getUser(userName);
		if(tempUser != null)
			return Response.status(200).entity(tempUser).build();
		
		return Response.status(404).entity("NO_USER").build();
	}
	
	@POST
	@Path("/validate/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response validateLogin(@PathParam("userName") String userName, String password) {
		return service.validateLogin(userName, password) ? Response.status(200).build() : Response.status(404).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(User user) {
		return service.addUser(user) ? Response.status(200).build() : Response.status(404).build();		
	}
	
	@PUT
	@Path("/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("userName") String userName, User user) {
		return service.updateUser(userName, user) ? Response.status(200).build() : Response.status(404).build();	
	}
	
	@PUT
	@Path("/change_password/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changePassword(@PathParam("userName") String userName, PasswordContainer container) {
		return service.changePassword(userName, container.getOldPassword(), container.getNewPassword()) ? 
				Response.status(200).build() : Response.status(404).build();
	}
	
	@DELETE
	@Path("/{userName}")
	public Response deleteUser(@PathParam("userName") String userName) {
		return service.removeUser(userName) ? Response.status(200).build() : Response.status(404).build();
	}
}
