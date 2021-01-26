package ec.edu.ups.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import ec.edu.ups.ejb.BillHeadFacade;
import ec.edu.ups.ejb.ProductWarehouseFacade;
import ec.edu.ups.entities.BillHead;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class BillManagementBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private BillHeadFacade ejbBillHeadFacade;
	
	@EJB
	private ProductWarehouseFacade ejbProductWarehouseFacade;
	
	private List<BillHead> billHeadList;
		
	private String userDni;
	private Date startTime;
	private Date endTime;
	
	private BillHead billHead;
	private boolean showDetail;
	
	public BillManagementBean() {
		super();
	}

	@PostConstruct
	public void init() {
		
	}
	
	public String cancel(BillHead billHead) {
		billHead.setStatus('C');
		ejbBillHeadFacade.update(billHead);
		return null;
	}
	
	public void filterBills() {
		if (startTime == null || endTime == null) {
			billHeadList = ejbBillHeadFacade.filterByUserDni(userDni);
		} else {
			billHeadList = ejbBillHeadFacade
					.filterByUserDniAndDate(userDni, startTime, endTime);
		}
		
	}
	
	public String cleanFilter() {
		userDni = "";
		startTime = null;
		endTime = null;
		return null;
	}
	
	public List<BillHead> getBillHeadList() {
		if (userDni == null) {
			userDni = "";
		}
		filterBills();
		return billHeadList;
	}
	
	public void showBillDetail(BillHead billHead) {
		setBillHead(billHead);
		showDetail = true;
	}
	
	public void hideBillDetail() {
		setBillHead(null);
		showDetail = false;
	}
	
	public void setBillHeadList(List<BillHead> billHeadList) {
		this.billHeadList = billHeadList;
	}
	
	public String getUserDni() {
		return userDni;
	}
	
	public void setUserDni(String userDni) {
		this.userDni = userDni;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public BillHead getBillHead() {
		return billHead;
	}

	public void setBillHead(BillHead billHead) {
		this.billHead = billHead;
	}

	public boolean isShowDetail() {
		return showDetail;
	}

	public void setShowDetail(boolean showDetail) {
		this.showDetail = showDetail;
	}
}
