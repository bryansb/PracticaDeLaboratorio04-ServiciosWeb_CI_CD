package ec.edu.ups.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entities.OrderDetail;

@Stateless
public class OrderDetailFacade extends AbstractFacade<OrderDetail> {

	@PersistenceContext(unitName = "Practica03")
    private EntityManager em;
	
	public OrderDetailFacade() {
		super(OrderDetail.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
