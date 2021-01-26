package ec.edu.ups.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entities.BillDetail;

@Stateless
public class BillDetailFacade extends AbstractFacade<BillDetail> {

	@PersistenceContext(unitName = "Practica03")
    private EntityManager em;
	
    public BillDetailFacade() {
    	super(BillDetail.class);
    }

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
