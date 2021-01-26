package ec.edu.ups.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import ec.edu.ups.ejb.UserFacade;
import ec.edu.ups.entities.User;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@RequestScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private UserFacade ejbUserFacade;
		
	private String email;
	private String password;
	
	private UIComponent mybutton;
	
	public LoginBean() {
		super();
	}
	
	@PostConstruct
	public void init() {
	}
	
	public String login() {
		User user = ejbUserFacade.login(email, password);
		if (user == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"No se encuentra registrado", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(mybutton.getClientId(context), message);
            return "login";
		}
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogged", true);
		return "home";
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogged", false);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", null);
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "home";
	}

	public UserFacade getEjbUserFacade() {
		return ejbUserFacade;
	}

	public void setEjbUserFacade(UserFacade ejbUserFacade) {
		this.ejbUserFacade = ejbUserFacade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UIComponent getMybutton() {
		return mybutton;
	}

	public void setMybutton(UIComponent mybutton) {
		this.mybutton = mybutton;
	}
	
}
