package ec.edu.ups.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Order
 *
 */
@Entity
@Table(name="ORDERS")
public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ord_id")
	private int id;
	
	@Column(name = "ord_state")
	private String state;
	
	@JsonbDateFormat("dd-MM-yyyy")
	@Column(name = "ord_date")
	private Date date;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	private List<ProductWarehouse> productWarehouses;
	
	@ManyToOne
	@JoinColumn
	private User user;

	public Order() {
		super();
	}
	
	public Order(String state, Date date) {
		this.state = state;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<ProductWarehouse> getProductWarehouses() {
		return productWarehouses;
	}

	public void setProductWarehouses(List<ProductWarehouse> productWarehouses) {
		this.productWarehouses = productWarehouses;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", state=" + state + ", productWarehouses="
				+ productWarehouses + ", user=" + user + "]";
	}
}
