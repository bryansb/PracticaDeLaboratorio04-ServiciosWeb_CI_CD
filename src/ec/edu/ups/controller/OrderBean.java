package ec.edu.ups.controller;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import ec.edu.ups.ejb.OrderHeadFacade;
import ec.edu.ups.ejb.ProductWarehouseFacade;
import ec.edu.ups.entities.BillHead;
import ec.edu.ups.entities.OrderDetail;
import ec.edu.ups.entities.OrderHead;
import ec.edu.ups.entities.ProductWarehouse;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class OrderBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private OrderHeadFacade orderHeadFacade;
	
	@EJB
	private ProductWarehouseFacade productWarehouseFacade;
	
	private List<OrderHead> orderHeadList;
	
	private OrderHead orderHead;
	private boolean showDetail;
	
	public OrderBean() {
	}
	
	@PostConstruct
	public void init() {
		orderHeadList = orderHeadFacade.findAll();
	}
	
	public void updateOrderHeadStatus(OrderHead orderHead, String status) {
		orderHead.setStatus(status.toUpperCase());
		if (validStatus(orderHead)) {
			orderHeadFacade.update(orderHead);
		}
		orderHeadList = orderHeadFacade.findAll();
	}

	
	private boolean validStatus(OrderHead orderHead) {
		switch (orderHead.getStatus().toUpperCase()) {
		case "ENVIADO":
		case "EN PROCESO":
		case "EN CAMINO":
		case "FINALIZADO":
			break;
		case "RECEPTADO":
			orderReceived(orderHead);
			break;
		default:
			return false;
		}
		return true;
	}
	
	private boolean orderReceived(OrderHead orderHead) {
		if (orderHead.getBillHead() != null) {
			return true;
		}
		BillHead billHead = new BillHead();
		billHead.setDate(new GregorianCalendar());
		billHead.setStatus('A');
		for (OrderDetail orderDetail : orderHead.getOrders()) {
			ProductWarehouse productWarehouse = orderDetail.getProductWarehouse();
			billHead.createBillDetail(orderDetail.getAmount(), 
					productWarehouse.getPrice(), 
					orderDetail.getProductWarehouse(), billHead);
			productWarehouse.setStock(productWarehouse.getStock() - orderDetail.getAmount());
			productWarehouseFacade.update(productWarehouse);
		}
		billHead.setUser(orderHead.getUser());
		billHead.calculateTotal();
		orderHead.setBillHead(billHead);
		return true;
	}
	
	public void showBillDetail(OrderHead orderHead) {
		setOrderHead(orderHead);
		showDetail = true;
	}
	
	public void hideBillDetail() {
		setOrderHead(null);
		showDetail = false;
	}
	
	public List<OrderHead> getOrderHeadList() {
		orderHeadList = orderHeadFacade.findAll();
		return orderHeadList;
	}

	public void setOrderHeadList(List<OrderHead> orderHeadList) {
		this.orderHeadList = orderHeadList;
	}

	public OrderHead getOrderHead() {
		return orderHead;
	}

	public void setOrderHead(OrderHead orderHead) {
		this.orderHead = orderHead;
	}

	public boolean isShowDetail() {
		return showDetail;
	}

	public void setShowDetail(boolean showDetail) {
		this.showDetail = showDetail;
	}

}
