package ec.edu.ups.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: City
 *
 */
@Entity
@Table(name="CITIES")

public class City implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cit_id")
	private int id;
	
	@Column(name="cit_name", length=255, nullable=false, unique=true)
	private String name;
	
	@Column(name="cit_deleted", columnDefinition="BOOLEAN DEFAULT 0")
	private boolean deleted;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "city")
	private List<Warehouse> warehouses = new ArrayList<Warehouse>();

	public City() {
		super();
	}
	
	public City(String name) {
		super();
		this.name = name;
		this.deleted = false;
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

	public List<Warehouse> getWarehouses() {
	    return warehouses;
	}

	public void setWarehouses(List<Warehouse> warehouses) {
	    this.warehouses = warehouses;
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + (deleted ? 1231 : 1237);
	    result = prime * result + id;
	    result = prime * result + ((name == null) ? 0 : name.hashCode());
	    result = prime * result
		    + ((warehouses == null) ? 0 : warehouses.hashCode());
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
	    City other = (City) obj;
	    if (deleted != other.deleted)
		return false;
	    if (id != other.id)
		return false;
	    if (name == null) {
		if (other.name != null)
		    return false;
	    } else if (!name.equals(other.name))
		return false;
	    if (warehouses == null) {
		if (other.warehouses != null)
		    return false;
	    } else if (!warehouses.equals(other.warehouses))
		return false;
	    return true;
	}

	@Override
	public String toString() {
	    return "City [id=" + id + ", name=" + name + ", deleted=" + deleted
		    + ", warehouses=" + warehouses + "]";
	}
   
}
