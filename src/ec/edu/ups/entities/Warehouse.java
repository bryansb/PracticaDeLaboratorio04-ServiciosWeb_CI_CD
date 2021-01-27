package ec.edu.ups.entities;

import java.io.Serializable;
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
 * Entity implementation class for Entity: Warehouse
 *
 */
@Entity
@Table(name = "WAREHOUSES")

public class Warehouse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "war_id")
	private int id;

	@Column(name = "war_address", length = 255, nullable = false)
	private String address;
	
	@Column(name="war_deleted", columnDefinition="BOOLEAN DEFAULT 0")
	private boolean deleted;
	
	@JsonbTransient
	@Transient
	private boolean editable;
	
	@JsonbTransient
	@Transient
	private boolean selected;

	@JsonbTransient
	@ManyToOne
	@JoinColumn
	private City city;

	@JsonbTransient
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "warehouse")
	private List<ProductWarehouse> productWarehouse;

	public Warehouse() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public List<ProductWarehouse> getProductDetails() {
		return productWarehouse;
	}

	public void setProductDetails(List<ProductWarehouse> productDetails) {
		this.productWarehouse = productDetails;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result
				+ ((productWarehouse == null) ? 0 : productWarehouse.hashCode());
		return result;
	}

	public boolean isEditable() {
	    return editable;
	}

	public void setEditable(boolean editable) {
	    this.editable = editable;
	}

	public boolean isSelected() {
	    return selected;
	}

	public void setSelected(boolean selected) {
	    this.selected = selected;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Warehouse other = (Warehouse) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (deleted != other.deleted)
			return false;
		if (id != other.id)
			return false;
		if (productWarehouse == null) {
			if (other.productWarehouse != null)
				return false;
		} else if (!productWarehouse.equals(other.productWarehouse))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Warehouse [id=" + id + ", address=" + address + ", deleted="
				+ deleted + ", city=" + city + ", productWarehouse="
				+ productWarehouse + "]";
	}

}
