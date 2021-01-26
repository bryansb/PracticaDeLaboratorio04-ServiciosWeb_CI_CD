package ec.edu.ups.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: BillDetail
 *
 */
@Entity
@Table(name="BILL_DETAILS")

public class BillDetail implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "det_id")
	private int id;
	
	@Column(name = "det_amount")
	private int amount;
	
	@Column(name = "det_unit_price", precision = 10, scale = 2)
	private double unitPrice;
	
	@Column(name = "det_total", precision = 10, scale = 2)
	private double total;
	
	@Column(name = "det_deleted", columnDefinition = "BOOLEAN DEFAULT 0")
	private boolean deleted;
	
	@ManyToOne
	@JoinColumn
	private ProductWarehouse productWarehouse;
	
	@ManyToOne
	@JoinColumn
	private BillHead billHead;

	public BillDetail() {
		super();
	}

	public BillDetail(int amount, double unitPrice,
			ProductWarehouse productWarehouse, BillHead billHead) {
		super();
		this.amount = amount;
		this.unitPrice = unitPrice;
		this.productWarehouse = productWarehouse;
		this.billHead = billHead;
	}

	public void calculateTotal() {
		this.total = this.unitPrice * this.amount;
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

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public ProductWarehouse getProductWarehouse() {
		return productWarehouse;
	}

	public void setProductWarehouse(ProductWarehouse productWarehouse) {
		this.productWarehouse = productWarehouse;
	}

	public BillHead getBillHead() {
		return billHead;
	}

	public void setBillHead(BillHead billHead) {
		this.billHead = billHead;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result
				+ ((billHead == null) ? 0 : billHead.hashCode());
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result
				+ ((productWarehouse == null) ? 0 : productWarehouse.hashCode());
		long temp;
		temp = Double.doubleToLongBits(total);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(unitPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		BillDetail other = (BillDetail) obj;
		if (amount != other.amount)
			return false;
		if (billHead == null) {
			if (other.billHead != null)
				return false;
		} else if (!billHead.equals(other.billHead))
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
		if (Double.doubleToLongBits(total) != Double
				.doubleToLongBits(other.total))
			return false;
		if (Double.doubleToLongBits(unitPrice) != Double
				.doubleToLongBits(other.unitPrice))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BillDetail [id=" + id + ", amount=" + amount + ", unitPrice="
				+ unitPrice + ", total=" + total + ", deleted=" + deleted
				+ ", productDetail=" + productWarehouse + ", billHead=" + billHead
				+ "]";
	}
}
