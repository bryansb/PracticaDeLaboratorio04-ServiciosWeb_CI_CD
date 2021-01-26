package ec.edu.ups.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import ec.edu.ups.ejb.UserFacade;
import ec.edu.ups.entities.User;
import ec.edu.ups.utils.MathFunction;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class UserBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private UserFacade ejbUserFacade;
	
	private String email;
	private String dni;
	private String name;
	private String lastname;
	private User user;
	private List<User> userList;
	
	public UserBean() {
		super();
	}
	
	@PostConstruct
	public void init() {
		userList = ejbUserFacade.findClients();
	}
	
	public void searchUser() {
		user = ejbUserFacade.findUserByDNI(dni);
		if (user == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"No se encuentra el usuario", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("msg2", message);
		}
		cleanString();
	}
	
	public String create() {
		try {
			User user = new User();
			user.setEmail(email);
			user.setDni(dni);
			user.setPassword(MathFunction.getMd5("c"));
			user.setName(name);
			user.setLastname(lastname);
			user.setRole('C');
			ejbUserFacade.create(user);
			userList = ejbUserFacade.findClients();
			cleanString();
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Ya existe DNI o Email", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("msg1", message);
		}
		return null;
	}
	
	public String update(User user) {
		user.setEditable(true);
		return "Success";

	}
	
	public String save(User user) {
		ejbUserFacade.update(user);
		user.setEditable(false);
		userList = ejbUserFacade.findClients();
		user = ejbUserFacade.findUserByDNI(dni);
		return null;
	}
	
	public String delete(User user) {
		try {
			user.setDeleted(true);
			ejbUserFacade.update(user);
			userList = ejbUserFacade.findClients();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String restore(User user) {
		try {
			user.setDeleted(false);
			ejbUserFacade.update(user);
			userList = ejbUserFacade.findClients();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void cleanString() {
		email = "";
		dni = "";
		name = "";
		lastname = "";
	}
	
	public void validateDNI(FacesContext context, UIComponent componentToValidate, 
			Object value) {
		String dni = (String) value;
		if (!MathFunction.validateDNI(dni)) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Cédula incorrecta", null);
			throw new ValidatorException(message);
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String username) {
		this.dni = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

}
