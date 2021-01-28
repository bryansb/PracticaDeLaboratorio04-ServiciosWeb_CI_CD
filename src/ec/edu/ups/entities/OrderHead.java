package ec.edu.ups.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Order
 *
 */
@Entity
@Table(name="ORDER_HEAD")
public class OrderHead implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ord_id")
	private int id;
	
	@Column(name = "ord_address")
	private String address;
	
	@Column(name = "ord_status")
	private String status;
	
	
	@JsonbDateFormat("dd-MM-yyyy")
	@Column(name = "ord_date")
	private Calendar date;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderHead")
	private List<OrderDetail> orders;
	
	@ManyToOne
	@JoinColumn
	@JsonbTransient
	private User user;
	
	@OneToOne
	@JoinColumn
	@JsonbTransient
	private BillHead billHead;

	public OrderHead() {
		super();
	}
	
	public OrderHead(String address, String status, Calendar date) {
		this.address = address;
		this.status = status;
		this.date = date;
	}

	public void createOrderDetail(int amount, ProductWarehouse productWarehouse) {
		if (this.orders == null) {
			orders = new ArrayList<>();
		}
		OrderDetail orderDetail = new OrderDetail(amount, productWarehouse, this);
		this.orders.add(orderDetail);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<OrderDetail> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDetail> orders) {
		this.orders = orders;
	}

	public BillHead getBillHead() {
		return billHead;
	}

	public void setBillHead(BillHead billHead) {
		this.billHead = billHead;
	}

	@Override
	public String toString() {
		return "OrderHead [id=" + id + ", address=" + address + ", status="
				+ status + ", date=" + date + ", orders=" + orders + ", user="
				+ user + ", billHead=" + billHead + "]";
	}
}
