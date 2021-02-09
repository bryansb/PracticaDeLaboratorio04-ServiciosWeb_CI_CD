package ec.edu.ups.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity implementation class for Entity: Products
 *
 */
@Entity
@Table(name = "PRODUCTS")

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pro_id")
	private int id;

	@Column(name = "pro_name", length = 255, nullable = false)
	private String name;
	
	@Column(name = "pro_discount", precision = 4, scale = 2)
    private BigDecimal discount;
	
	@JsonbTransient
	@Transient
	private boolean editable;

	@Column(name = "pro_deleted", columnDefinition = "BOOLEAN DEFAULT 0")
	private boolean deleted;

	@JsonbTransient
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
	private List<ProductWarehouse> productDetails = new ArrayList<ProductWarehouse>();

	@ManyToOne
	@JoinColumn
	private Category category;

	public Product() {
		super();
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

	public List<ProductWarehouse> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<ProductWarehouse> productDetails) {
		this.productDetails = productDetails;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isEditable() {
	    return editable;
	}

	public void setEditable(boolean editable) {
	    this.editable = editable;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((productDetails == null) ? 0 : productDetails.hashCode());
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
		Product other = (Product) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (deleted != other.deleted)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (productDetails == null) {
			if (other.productDetails != null)
				return false;
		} else if (!productDetails.equals(other.productDetails))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", deleted=" + deleted
				+ ", productDetails=" + productDetails + ", category="
				+ category + "]";
	}
}
