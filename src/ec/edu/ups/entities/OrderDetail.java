package ec.edu.ups.entities;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: OrderDetail
 *
 */
@Entity
@Table(name="ORDER_DETAIL")
public class OrderDetail implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ord_det_id")
	private int id;
	
	@Column(name = "ord_det_amount")
	private int amount;
	
    @ManyToOne
    @JoinColumn
	private ProductWarehouse productWarehouse;
	
    @JsonbTransient
    @ManyToOne
    @JoinColumn
	private OrderHead orderHead;
    
	public OrderDetail() {
		super();
	}

	public OrderDetail(int amount, ProductWarehouse productWarehouse,
			OrderHead orderHead) {
		super();
		this.amount = amount;
		this.productWarehouse = productWarehouse;
		this.orderHead = orderHead;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public void setProductWarehouse(ProductWarehouse productWarehouse) {
		this.productWarehouse = productWarehouse;
	}

	public OrderHead getOrderHead() {
		return orderHead;
	}

	public void setOrderHead(OrderHead orderHead) {
		this.orderHead = orderHead;
	}
   
}
