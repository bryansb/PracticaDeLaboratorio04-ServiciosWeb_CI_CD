package ec.edu.ups.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import ec.edu.ups.ejb.CategoryFacade;
import ec.edu.ups.ejb.ProductWarehouseFacade;
import ec.edu.ups.ejb.WarehouseFacade;
import ec.edu.ups.entities.Category;
import ec.edu.ups.entities.ProductWarehouse;
import ec.edu.ups.entities.Warehouse;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class IndexBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private WarehouseFacade ejbWarehouseFacade;
	
	@EJB
	private CategoryFacade ejbCategoryFacade;
	
	@EJB
	private ProductWarehouseFacade ejbProductWarehouseFacade;

	private List<ProductWarehouse> productWarehouseList;

	private List<Warehouse> warehouseList;
	private List<Category> categoryList;

	private int warehouseId;
	private int categoryId;
	
	private String productName;
	
	public IndexBean() {
	}
	
	@PostConstruct
	public void init() {
		productName = "";
		warehouseList = ejbWarehouseFacade.findAll();
		categoryList = ejbCategoryFacade.findAll();
		filterProductWarehouse();
	}

	public void filterProductWarehouse() {
		if (warehouseId != 0 && categoryId != 0) {
			productWarehouseList = ejbProductWarehouseFacade
					.findByWarehouseAndCategoryId(warehouseId, categoryId, productName);
		} else if (warehouseId == 0 && categoryId != 0) {
			productWarehouseList = ejbProductWarehouseFacade.findByCategoryId(categoryId, productName);
		} else if (warehouseId != 0 && categoryId == 0) {
			productWarehouseList = ejbProductWarehouseFacade.findByWarehouseId(warehouseId, productName);
		} else {
			productWarehouseList = ejbProductWarehouseFacade.findAllOrderedByProductName(productName);
		}
	}
	
	public List<ProductWarehouse> getProductWarehouseList() {
		filterProductWarehouse();
		return productWarehouseList;
	}

	public void setProductWarehouseList(
			List<ProductWarehouse> productWarehouseList) {
		this.productWarehouseList = productWarehouseList;
	}

	public List<Warehouse> getWarehouseList() {
		return warehouseList;
	}

	public void setWarehouseList(List<Warehouse> warehouseList) {
		this.warehouseList = warehouseList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
