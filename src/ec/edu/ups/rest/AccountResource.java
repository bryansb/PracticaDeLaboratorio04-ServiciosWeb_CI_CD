package ec.edu.ups.rest;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ec.edu.ups.ejb.UserFacade;
import ec.edu.ups.entities.User;
import ec.edu.ups.utils.MathFunction;

@Path("/account/")
public class AccountResource {
	
	@EJB
	private UserFacade userFacade;
	
	@GET
    @Path("/findUser/{dni}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getUserByDNI(@PathParam("dni") String dni) {
		Jsonb jsonb = JsonbBuilder.create();
		try {
			User user = userFacade.findUserByDNI(dni);
			return Response.ok(jsonb.toJson(user))
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
		} catch (Exception e) {
			return Response.status(404).entity("No se encuentra el usuario").build();
		}
	}
	
	@GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		Jsonb jsonb = JsonbBuilder.create();
		try {
			List<User> users = userFacade.findAll();
			return Response.ok(jsonb.toJson(users))
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
		} catch (Exception e) {
			return Response.status(404).entity("No hay usuarios").build();
		}
	}
	
	@POST
    @Path("/loginUser")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response LoginUser(
			@FormParam("key") String key,
			@FormParam("password") String password) {
		Jsonb jsonb = JsonbBuilder.create();
		try {
			User user = userFacade.loginUser(key, passwordMD5(password));
			
			return Response.ok(jsonb.toJson(user))
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
		} catch (Exception e) {
			return Response.status(405).entity("Usuario y contraseña incorrectos").build();
		}
		
	}
	
	@PUT
    @Path("/createUser")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response CreateUser(
			@FormParam("email") String email,
			@FormParam("dni") String dni,
			@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("confirmPassword") String confirmPassword,
			@FormParam("name") String name,
			@FormParam("lastname") String lastname) throws IOException {
		password = passwordMD5(password);
		confirmPassword = passwordMD5(confirmPassword);
		if (userFacade.findUserByDNI(dni) == null) {
			return Response.status(404).entity("Esta cédula no esta en el sistema").build();
		}else if (userFacade.findUserByUsername(username) != null) {
			return Response.status(405).entity("Este nombre de usuario no esta disponible").build();
		}else if (userFacade.findUserByEmail(email) != null) {
			return Response.status(405).entity("Este correo ya esta en uso").build();
		}else if (!password.equals(confirmPassword)) {
			return Response.status(405).entity("Las contraseñas no coinciden").build();
		}else {
			User user = userFacade.findUserByDNI(dni);
			user.setEmail(email);
			user.setUsername(username);
			user.setPassword(password);
			user.setName(name);
			user.setLastname(lastname);
			userFacade.update(user);
			return Response.status(200).entity("Ususario creado correctamente").build();
		}
	}
	
	@PUT
	@Path("/updateUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateUser(
			@FormParam("id") int id,
			@FormParam("email") String email,
			@FormParam("username") String username,
			@FormParam("oldPassword") String oldPassword,
			@FormParam("newPassword") String newPassword,
			@FormParam("name") String name,
			@FormParam("lastname") String lastname) throws IOException {
		
		oldPassword = passwordMD5(oldPassword);
		newPassword = passwordMD5(newPassword);
		
		User user = userFacade.read(id);
		
		if (!user.getPassword().equals(oldPassword)) {
			return Response.status(401).entity("Contraseña Incorrecta").build();
		}else if (userFacade.findUserByEmail(email) != null) {
			return Response.status(401).entity("Email ya existe").build();
		}else if (userFacade.findUserByUsername(username) != null) {
			return Response.status(401).entity("Username ya existe").build();
		}else {
			user.setEmail(email);
			user.setUsername(username);
			user.setPassword(newPassword);
			user.setName(name);
			user.setLastname(lastname);
			userFacade.update(user);
			return Response.status(200).entity("Usuario actualizado correctamente").build();
		}
	}
	
	@PUT
	@Path("/deleteUser/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("id") int id){
		
		User user = userFacade.read(id);
		user.setDeleted(true);
		userFacade.update(user);
		return Response.status(200).entity("Usuario correctamente eliminado de forma lógica").build();
	}
	
	private String passwordMD5(String password) {
		return MathFunction.getMd5(password);
	}
}
