package ec.edu.ups.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import ec.edu.ups.ejb.CityFacade;
import ec.edu.ups.entities.City;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class CityBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CityFacade ejbCityFacade;
	
	private List<City> cityList;
	private int id;
	private String name;
	private boolean deleted;
	
	public CityBean() {
		super();
	}
	
	@PostConstruct
	public void init() {
		try {
			ejbCityFacade.create(new City("Cuenca"));
		}catch (Exception e) {
			if(e.getMessage().contains("Transaction aborted")) {
				System.out.println("Ya existe Cuenca");
			}
		}
		cityList = ejbCityFacade.findAll();
	}
	
	public List<City> getCityList() {
		return cityList;
	}
	
	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public String add() {
		
		return null;
	}

}
