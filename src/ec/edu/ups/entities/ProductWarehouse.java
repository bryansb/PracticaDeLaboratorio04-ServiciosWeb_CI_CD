package ec.edu.ups.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ProductDetail
 *
 */
@Entity
@Table(name = "PRODUCT_WAREHOUSES")
public class ProductWarehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_det_id")
    private int id;

    @Column(name = "pro_det_stock")
    private int stock;

    @Column(name = "pro_det_price", precision = 10, scale = 2)
    private double price;

    @JsonbTransient
    @Column(name = "pro_det_deleted", columnDefinition = "BOOLEAN DEFAULT 0")
    private boolean deleted;
    
    @JsonbTransient
    @Transient
    private boolean editable;
    
    @JsonbTransient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productWarehouse")
    private List<BillDetail> billDetails = new ArrayList<BillDetail>();

    @JsonbTransient
    @ManyToOne
    @JoinColumn
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn
    private Product product;
    
    @JsonbTransient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productWarehouse")
	private List<OrderDetail> orders;

    public ProductWarehouse() {
	super();
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getStock() {
	return stock;
    }

    public void setStock(int stock) {
	this.stock = stock;
    }

    public double getPrice() {
	return price;
    }

    public void setPrice(double price) {
	this.price = price;
    }

    public boolean isDeleted() {
	return deleted;
    }

    public void setDeleted(boolean deleted) {
	this.deleted = deleted;
    }

    public List<BillDetail> getBillDetails() {
	return billDetails;
    }

    public void setBillDetails(List<BillDetail> billDetails) {
	this.billDetails = billDetails;
    }

    public Warehouse getWarehouse() {
	return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
	this.warehouse = warehouse;
    }

    public Product getProduct() {
	return product;
    }

    public void setProduct(Product product) {
	this.product = product;
    }

    public boolean isEditable() {
	return editable;
    }

    public void setEditable(boolean editable) {
    	this.editable = editable;
    }

	public List<OrderDetail> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDetail> orders) {
		this.orders = orders;
	}

	@Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((billDetails == null) ? 0 : billDetails.hashCode());
	result = prime * result + (deleted ? 1231 : 1237);
	result = prime * result + id;
	long temp;
	temp = Double.doubleToLongBits(price);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((product == null) ? 0 : product.hashCode());
	result = prime * result + stock;
	result = prime * result
		+ ((warehouse == null) ? 0 : warehouse.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ProductWarehouse other = (ProductWarehouse) obj;
	if (billDetails == null) {
	    if (other.billDetails != null)
		return false;
	} else if (!billDetails.equals(other.billDetails))
	    return false;
	if (deleted != other.deleted)
	    return false;
	if (id != other.id)
	    return false;
	if (Double.doubleToLongBits(price) != Double
		.doubleToLongBits(other.price))
	    return false;
	if (product == null) {
	    if (other.product != null)
		return false;
	} else if (!product.equals(other.product))
	    return false;
	if (stock != other.stock)
	    return false;
	if (warehouse == null) {
	    if (other.warehouse != null)
		return false;
	} else if (!warehouse.equals(other.warehouse))
	    return false;
	return true;
    }

	@Override
	public String toString() {
		return "ProductWarehouse [id=" + id + ", stock=" + stock + ", price="
				+ price + ", deleted=" + deleted + ", editable=" + editable
				+ ", billDetails=" + billDetails + ", warehouse=" + warehouse
				+ ", product=" + product;
	}

}
