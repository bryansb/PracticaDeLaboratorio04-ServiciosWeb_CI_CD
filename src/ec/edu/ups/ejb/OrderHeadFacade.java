package ec.edu.ups.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entities.OrderHead;

@Stateless
public class OrderHeadFacade extends AbstractFacade<OrderHead>{
	
	private static final String FIND_BY_USER_ID_QRY = 
			" SELECT o FROM OrderHead o "
			+ " WHERE o.user.id = :userId ";

	@PersistenceContext(unitName = "Practica03")
    private EntityManager em;
	
	public OrderHeadFacade() {
		super(OrderHead.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderHead> findByUserId(int userId){
		return (List<OrderHead>) getEntityManager().createQuery(FIND_BY_USER_ID_QRY)
				.setParameter("userId", userId).getResultList();
	}

}
