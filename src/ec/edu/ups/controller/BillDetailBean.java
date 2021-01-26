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

import ec.edu.ups.ejb.BillDetailFacade;
import ec.edu.ups.ejb.CategoryFacade;
import ec.edu.ups.ejb.ProductWarehouseFacade;
import ec.edu.ups.ejb.WarehouseFacade;
import ec.edu.ups.entities.BillDetail;
import ec.edu.ups.entities.BillHead;
import ec.edu.ups.entities.Category;
import ec.edu.ups.entities.ProductWarehouse;
import ec.edu.ups.entities.Warehouse;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class BillDetailBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private WarehouseFacade ejbWarehouseFacade;
	
	@EJB
	private CategoryFacade ejbCategoryFacade;
	
	@EJB
	private BillDetailFacade ejbBillDetailFacade;
	
	@EJB
	private ProductWarehouseFacade ejbProductWarehouseFacade;
	
	private int amount;
	private ProductWarehouse productWarehouse;
	private BillHead billHead;
	private List<ProductWarehouse> productWarehouseList;
	private String current;
	
	private boolean inputAmount;
	
	private List<Warehouse> warehouseList;
	private List<Category> categoryList;
	
	private int warehouseId;
	private int categoryId;
	
	private String productName;
	
	public BillDetailBean() {
	}
	
	@PostConstruct
	public void init() {
		this.amount = 1;
		productName = "";
		warehouseList = ejbWarehouseFacade.findAll();
		categoryList = ejbCategoryFacade.findAll();
		filterProductWarehouse();
	}
	
	public List<BillDetail> getBillDetailList() {
		return ejbBillDetailFacade.findAll();
	}
	
	public List<ProductWarehouse> getProductWarehouseList() {
		filterProductWarehouse();
		return productWarehouseList;
	}

	public void setProductWarehouseList(
			List<ProductWarehouse> productWarehouseList) {
		this.productWarehouseList = productWarehouseList;
	}

	public String create(BillHead billHead) {
		billHead.createBillDetail(this.amount, 
				this.productWarehouse.getPrice(), 
				this.productWarehouse, billHead);
		this.inputAmount = false;
		this.productWarehouse.setStock(this.productWarehouse.getStock() 
				- this.amount);
		this.amount = 1;
		billHead.calculateTotal();
		return null;
	}
	
	public void validateAmount(FacesContext context, UIComponent componentToValidate, 
			Object value) {
		Integer amount = (Integer) value;
		if (amount <= 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"La cantidad no puede ser menor o igual a cero", null);
			throw new ValidatorException(message);
		}
		if (amount > productWarehouse.getStock()) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"La cantidad debe ser menor o igual al stock: "
							+ productWarehouse.getStock(), null);
			throw new ValidatorException(message);
		} 
	}
	
	public String cancelBilling() {
		productWarehouseList = ejbProductWarehouseFacade.findAll();
		inputAmount = false;
		return null;
	}
	
	public String delete(BillDetail billDetail) {
		ejbBillDetailFacade.delete(billDetail);
		return null;
	}
	
	public String loadProduct(ProductWarehouse productWarehouse) {
		this.amount = 1;
		this.inputAmount = true;
		this.productWarehouse = productWarehouse;
		return null;
	}

	public String cancelProduct() {
		this.amount = 1;
		this.inputAmount = false;
		this.productWarehouse = null;
		return null;
	}
	
	public String cancelBillDetail(BillHead billHead) {
		for (BillDetail billDetail : billHead.getBillDetails()) {
			ProductWarehouse productWarehouse = billDetail.getProductWarehouse();
			int amount = billDetail.getAmount();
			int stock = billDetail.getProductWarehouse().getStock();
			productWarehouse.setStock(stock + amount);
			ejbProductWarehouseFacade.update(productWarehouse);
		}
		productWarehouseList = ejbProductWarehouseFacade.findAll();
		return null;
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
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public ProductWarehouse getProductWarehouse() {
		return productWarehouse;
	}

	public void setProductWarehouse(ProductWarehouse productDetail) {
		this.productWarehouse = productDetail;
	}

	public BillHead getBillHead() {
		return billHead;
	}

	public void setBillHead(BillHead billHead) {
		this.billHead = billHead;
	}

	public boolean isInputAmount() {
		return inputAmount;
	}

	public void setInputAmount(boolean inputAmount) {
		this.inputAmount = inputAmount;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
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

	public String printCurrent() {
		ProductWarehouse p = ejbProductWarehouseFacade.read(Integer.parseInt(current));
		System.out.println(p.getStock());
		return null;
	}
}
