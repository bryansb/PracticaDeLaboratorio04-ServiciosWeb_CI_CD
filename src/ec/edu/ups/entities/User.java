package ec.edu.ups.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@Table(name="USERS")

public class User implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="use_id")
	private int id;
	
	@Column(name="use_email", length=255, nullable=false, unique=true)
	private String email;
	
	@Column(name="use_dni", length=10, nullable=false, unique=true)
	private String dni;
	
	@Column(name="use_username", length=255, nullable=true)
	private String username;
	
	@Column(name="use_password", length=255, nullable=false)
	private String password;
	
	@Column(name="use_name", length=255, nullable=false)
	private String name;
	
	@Column(name="use_lastname", length=255, nullable=false)
	private String lastname;
	
	@Column(name="use_role", length=1, nullable=false, columnDefinition = "VARCHAR(1) DEFAULT 'C'")
	private char role;
	
	@Column(name="use_deleted", columnDefinition="BOOLEAN DEFAULT 0")
	private boolean deleted;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonbTransient
	private List<BillHead> billHeads = new ArrayList<BillHead>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonbTransient
	private List<Order> orders;
	
	@JsonbTransient
	@Transient
	private boolean editable;

	public User() {
		super();
	}

	public User(String email, String dni, String username, String password, String name, String lastname, char role, boolean deleted) {
		super();
		this.email = email;
		this.dni = dni;
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastname = lastname;
		this.role = role;
		this.deleted = deleted;
	}

	public int getId() {
	    return id;
	}

	public void setId(int id) {
	    this.id = id;
	}

	public String getEmail() {
	    return email;
	}

	public void setEmail(String email) {
	    this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public String getLastname() {
	    return lastname;
	}

	public void setLastname(String lastname) {
	    this.lastname = lastname;
	}

	public char getRole() {
	    return role;
	}

	public void setRole(char role) {
	    this.role = role;
	}

	public boolean isDeleted() {
	    return deleted;
	}

	public void setDeleted(boolean deleted) {
	    this.deleted = deleted;
	}

	public List<BillHead> getBillHeads() {
	    return billHeads;
	}

	public void setBillHeads(List<BillHead> billHeads) {
	    this.billHeads = billHeads;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result
		    + ((billHeads == null) ? 0 : billHeads.hashCode());
	    result = prime * result + (deleted ? 1231 : 1237);
	    result = prime * result + ((email == null) ? 0 : email.hashCode());
	    result = prime * result + id;
	    result = prime * result
		    + ((lastname == null) ? 0 : lastname.hashCode());
	    result = prime * result + ((name == null) ? 0 : name.hashCode());
	    result = prime * result + role;
	    result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
	    User other = (User) obj;
	    if (billHeads == null) {
		if (other.billHeads != null)
		    return false;
	    } else if (!billHeads.equals(other.billHeads))
		return false;
	    if (deleted != other.deleted)
		return false;
	    if (email == null) {
		if (other.email != null)
		    return false;
	    } else if (!email.equals(other.email))
		return false;
	    if (id != other.id)
		return false;
	    if (lastname == null) {
		if (other.lastname != null)
		    return false;
	    } else if (!lastname.equals(other.lastname))
		return false;
	    if (name == null) {
		if (other.name != null)
		    return false;
	    } else if (!name.equals(other.name))
		return false;
	    if (role != other.role)
		return false;
	    if (dni == null) {
		if (other.dni != null)
		    return false;
	    } else if (!dni.equals(other.dni))
		return false;
	    return true;
	}

	@Override
	public String toString() {
	    return "User [id=" + id + ", email=" + email + ", username="
		    + dni + ", name=" + name + ", lastname=" + lastname 
		    + ", role=" + role + ", deleted=" + deleted + ", billHeads=" 
		    + billHeads + "]";
	}
   
}
