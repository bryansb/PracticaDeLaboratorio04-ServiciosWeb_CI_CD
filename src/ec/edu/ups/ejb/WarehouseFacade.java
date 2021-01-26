package ec.edu.ups.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entities.Warehouse;

@Stateless
public class WarehouseFacade extends AbstractFacade<Warehouse> {

	@PersistenceContext(unitName = "Practica03")
    private EntityManager em;
	
    public WarehouseFacade() {
    	super(Warehouse.class);
    }

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
