package ec.edu.ups.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import ec.edu.ups.ejb.CategoryFacade;
import ec.edu.ups.ejb.ProductFacade;
import ec.edu.ups.ejb.WarehouseFacade;
import ec.edu.ups.entities.Category;
import ec.edu.ups.entities.Product;
import ec.edu.ups.entities.Warehouse;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class ProductBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ProductFacade productFacade;

    @EJB
    private WarehouseFacade warehouseFacade;

    @EJB
    private CategoryFacade categoryFacade;

    private List<Warehouse> warehouseList;
    private List<Product> productList;
    private List<Category> categoryList;

    private Category category;
    private String name;
    private int index;

    public ProductBean() {
	super();
    }

    @PostConstruct
    public void init() {

	// addCategories();

	loadCategories();
	loadProducts();
    }

    public void addCategories() {
	List<String> categories = new ArrayList<>();
	categories.add("Limpieza");
	categories.add("Decoración");
	categories.add("Aseo Personal");
	categories.add("Utencilios de Cocina");
	categories.add("Otros");

	for (String string : categories) {
	    Category category = new Category();
	    category.setName(string);
	    this.categoryFacade.create(category);
	}
    }

    public void loadCategories() {
	this.categoryList = this.categoryFacade.findAll();
    }

    public void loadProducts() {
	this.productList = productFacade.findAll();
    }

    public String create() {
	Product product = new Product();
	product.setName(this.name);
	this.category = categoryFacade.read(index);
	product.setCategory(this.category);

	productFacade.create(product);
	loadProducts();
	return null;
    }

    public String read() {

	return null;
    }

    public String edit(Product product) {
	product.setEditable(true);
	return null;
    }

    public String save(Product product) {
	productFacade.update(product);
	product.setEditable(false);
	return null;
    }

    public String delete(Product product) {
	product.setDeleted(true);
	productFacade.update(product);
	loadProducts();
	return null;
    }

    public String restore(Product product) {
	product.setDeleted(false);
	productFacade.update(product);
	loadProducts();
	return null;
    }

    public List<Warehouse> getWarehouseList() {
	return warehouseList;
    }

    public void setWarehouseList(List<Warehouse> warehouseList) {
	this.warehouseList = warehouseList;
    }

    public List<Product> getProductList() {
	return productList;
    }

    public void setProductList(List<Product> productList) {
	this.productList = productList;
    }

    public List<Category> getCategoryList() {
	return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
	this.categoryList = categoryList;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public int getIndex() {
	return index;
    }

    public void setIndex(int index) {
	this.index = index;
    }

    public Category getCategory() {
	return category;
    }

    public void setCategory(Category category) {
	this.category = category;
    }

}
