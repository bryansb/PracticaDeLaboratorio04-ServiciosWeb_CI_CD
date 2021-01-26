package ec.edu.ups.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

import ec.edu.ups.entities.BillHead;

@Stateless
public class BillHeadFacade extends AbstractFacade<BillHead>{

	@PersistenceContext(unitName = "Practica03")
    private EntityManager em;
	
	private static final String FIND_BY_DNI_AND_DATE_QRY = 
			"SELECT bh FROM BillHead bh WHERE bh.date "
			+ " BETWEEN :startDate AND :endDate AND "
			+ " bh.user.dni LIKE :dni ORDER BY bh.date DESC";
	
    public BillHeadFacade() {
    	super(BillHead.class);
    }

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	public List<BillHead> filterByUserDni(String dni) {
		String[][] attributes = {{"user", "dni"}};
		String[] values = {"like&%" + dni + "%"};
		return super.findByPath(attributes, values, new String[] {"date"}, 0, 0, false, false);
	}
	
	public List<BillHead> filterByDate(Date startTime, Date endTime) {
		System.out.println("filterByDate");
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<BillHead> filterByUserDniAndDate(String dni, Date startTime, 
			Date endTime) {
		return getEntityManager().createQuery(FIND_BY_DNI_AND_DATE_QRY)
				.setParameter("startDate", startTime, TemporalType.DATE)
				.setParameter("endDate", endTime, TemporalType.DATE)
				.setParameter("dni", "%" + dni + "%")
				.getResultList();
	}

}
