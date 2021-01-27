package ec.edu.ups.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbNumberFormat;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: BillHead
 *
 */
@Entity
@Table(name="BILL_HEADS")

public class BillHead implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hea_id")
	private int id;
	
	@Column(name = "hea_subtotal", nullable=false)
	@JsonbNumberFormat(locale = "en_US", value = "#0.00")
	private double subtotal;
	
	@Column(name = "hea_vat", nullable=false)
	@JsonbNumberFormat(locale = "en_US", value = "#0.00")
	private double vat;
	
	@Column(name = "hea_date", nullable=false)
	@JsonbDateFormat("dd-MM-yyyy")
	private Calendar date;
	
	@Column(name = "hea_status", nullable=false, columnDefinition = "VARCHAR(1) DEFAULT 'C'")
	private char status;
	
	@Column(name = "hea_total", precision = 12, scale = 2, nullable=false)
	@JsonbNumberFormat(locale = "en_US", value = "#0.00")
	private double total;
	
	@Column(name = "hea_deleted", columnDefinition = "BOOLEAN DEFAULT 0")
	private boolean deleted;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "billHead")
	private List<BillDetail> billDetails = new ArrayList<BillDetail>();
	
	@ManyToOne
	@JoinColumn
	private User user;

	public BillHead() {
		super();
		this.date = new GregorianCalendar();
	}
	
	@PreUpdate
	public void cancelledBill() {
		if (status != 'C') {
			return;
		}
	}
	
	public void createBillDetail(int amount, double unitPrice,
			ProductWarehouse productWarehouse, BillHead billHead) {
		if (this.billDetails == null) {
			this.billDetails = new ArrayList<>();
		}
		BillDetail billDetail = new BillDetail(amount, unitPrice, productWarehouse, billHead);
		this.billDetails.add(billDetail);
	}
	
	public boolean calculateTotal() {
		if(this.billDetails == null) {
			return false;
		}
		this.subtotal = 0;
		this.vat = 0;
		this.total = 0;
		for (BillDetail billDetail : billDetails) {
			billDetail.calculateTotal();
			this.subtotal += billDetail.getTotal();
		}
		this.vat = this.subtotal * 0.12;
		this.total = this.subtotal + this.vat;
		return true;
	}

	public int getId() {
	    return id;
	}

	public void setId(int id) {
	    this.id = id;
	}

	public double getSubtotal() {
	    return subtotal;
	}

	public void setSubtotal(double subtotal) {
	    this.subtotal = subtotal;
	}

	public double getVat() {
	    return vat;
	}

	public void setVat(double vat) {
	    this.vat = vat;
	}

	public Calendar getDate() {
	    return date;
	}

	public void setDate(Calendar date) {
	    this.date = date;
	}

	public char getStatus() {
	    return status;
	}

	public void setStatus(char status) {
	    this.status = status;
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

	public List<BillDetail> getBillDetails() {
	    return billDetails;
	}

	public void setBillDetails(List<BillDetail> billDetails) {
	    this.billDetails = billDetails;
	}

	public User getUser() {
	    return user;
	}

	public void setUser(User user) {
	    this.user = user;
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result
		    + ((billDetails == null) ? 0 : billDetails.hashCode());
	    result = prime * result + ((date == null) ? 0 : date.hashCode());
	    result = prime * result + (deleted ? 1231 : 1237);
	    result = prime * result + id;
	    result = prime * result + status;
	    long temp;
	    temp = Double.doubleToLongBits(subtotal);
	    result = prime * result + (int) (temp ^ (temp >>> 32));
	    temp = Double.doubleToLongBits(total);
	    result = prime * result + (int) (temp ^ (temp >>> 32));
	    result = prime * result + ((user == null) ? 0 : user.hashCode());
	    temp = Double.doubleToLongBits(vat);
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
	    BillHead other = (BillHead) obj;
	    if (billDetails == null) {
		if (other.billDetails != null)
		    return false;
	    } else if (!billDetails.equals(other.billDetails))
		return false;
	    if (date == null) {
		if (other.date != null)
		    return false;
	    } else if (!date.equals(other.date))
		return false;
	    if (deleted != other.deleted)
		return false;
	    if (id != other.id)
		return false;
	    if (status != other.status)
		return false;
	    if (Double.doubleToLongBits(subtotal) != Double
		    .doubleToLongBits(other.subtotal))
		return false;
	    if (Double.doubleToLongBits(total) != Double
		    .doubleToLongBits(other.total))
		return false;
	    if (user == null) {
		if (other.user != null)
		    return false;
	    } else if (!user.equals(other.user))
		return false;
	    if (Double.doubleToLongBits(vat) != Double
		    .doubleToLongBits(other.vat))
		return false;
	    return true;
	}

	@Override
	public String toString() {
	    return "BillHead [id=" + id + ", subtotal=" + subtotal + ", vat="
		    + vat + ", date=" + date + ", status=" + status + ", total="
		    + total + ", deleted=" + deleted + ", billDetails="
		    + billDetails + ", user=" + user + "]";
	}
   
}
