package ec.edu.ups.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import ec.edu.ups.ejb.CityFacade;
import ec.edu.ups.ejb.ProductFacade;
import ec.edu.ups.ejb.WarehouseFacade;
import ec.edu.ups.entities.City;
import ec.edu.ups.entities.Product;
import ec.edu.ups.entities.ProductWarehouse;
import ec.edu.ups.entities.Warehouse;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class WarehouseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private WarehouseFacade warehouseFacade;

    @EJB
    private CityFacade cityFacade;

    @EJB
    private ProductFacade productFacade;

    private List<City> cityList;
    private List<Product> productList;
    private List<Warehouse> warehouseList;
    private List<ProductWarehouse> productWarehouseList;

    private Warehouse selectedWarehouse;
    private City city;
    private String address;

    private int index;

    public WarehouseBean() {
	super();
    }

    @PostConstruct
    public void init() {

	// addCities();

	loadCities();
	loadWarehouses();
	loadProducts();

    }

    private void loadProducts() {
	this.productList = this.productFacade.findAll();

    }

    public String create() {
	Warehouse warehouse = new Warehouse();
	warehouse.setAddress(this.address);
	this.city = cityFacade.read(index);
	warehouse.setCity(this.city);

	warehouseFacade.create(warehouse);
	loadWarehouses();
	return null;
    }

    public String read() {

	return null;
    }

    public String edit(Warehouse warehouse) {
	warehouse.setEditable(true);
	return null;
    }

    public String save(Warehouse warehouse) {
	warehouseFacade.update(warehouse);
	warehouse.setEditable(false);
	return null;
    }

    public String delete(Warehouse warehouse) {
	warehouse.setDeleted(true);
	warehouseFacade.update(warehouse);
	loadWarehouses();
	return null;
    }

    public String restore(Warehouse warehouse) {
	warehouse.setDeleted(false);
	warehouseFacade.update(warehouse);
	loadWarehouses();
	return null;
    }

    public void manageWarehouse(Warehouse warehouse) {
	warehouse.setSelected(true);
	warehouseFacade.update(warehouse);
	this.selectedWarehouse = warehouse;
	loadWarehouses();
    }

    public void loadCities() {
	this.cityList = cityFacade.findAll();
    }

    public void loadWarehouses() {
	this.warehouseList = this.warehouseFacade.findAll();
    }

    public void addCities() {
	List<String> cities = new ArrayList<>();
	cities.add("Macas");
	cities.add("Quito");
	cities.add("Riobamba");
	cities.add("Ambato");
	cities.add("Guayaquil");
	cities.add("Tena");
	cities.add("Paute");
	cities.add("Cuenca");

	for (String string : cities) {
	    City city = new City();
	    city.setName(string);
	    this.cityFacade.create(city);
	}
    }

    public List<Warehouse> getWarehouseList() {
	return warehouseList;
    }

    public void setWarehouseList(List<Warehouse> warehouseList) {
	this.warehouseList = warehouseList;
    }

    public City getCity() {
	return city;
    }

    public void setCity(City city) {
	this.city = city;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public List<ProductWarehouse> getProductWarehouseList() {
	return productWarehouseList;
    }

    public void setProductWarehouseList(
	    List<ProductWarehouse> productWarehouseList) {
	this.productWarehouseList = productWarehouseList;
    }

    public List<City> getCityList() {
	return cityList;
    }

    public void setCityList(List<City> cityList) {
	this.cityList = cityList;
    }

    public List<Product> getProductList() {
	return productList;
    }

    public void setProductList(List<Product> productList) {
	this.productList = productList;
    }

    public int getIndex() {
	return index;
    }

    public void setIndex(int index) {
	this.index = index;
    }

    public Warehouse getSelectedWarehouse() {
	return selectedWarehouse;
    }

    public void setSelectedWarehouse(Warehouse selectedWarehouse) {
	this.selectedWarehouse = selectedWarehouse;
    }

}
