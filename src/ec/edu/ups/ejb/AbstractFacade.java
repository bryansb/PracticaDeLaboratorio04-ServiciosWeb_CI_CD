package ec.edu.ups.ejb;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class AbstractFacade<T> {

	private Class<T> entityClass;
	
	public AbstractFacade(Class<T> entityClass) {
    	this.entityClass = entityClass;
    }
	
	protected abstract EntityManager getEntityManager();
	
	public void create(T entity) {
		getEntityManager().persist(entity);
    }
	
	public T read(Object id) {
		return getEntityManager().find(entityClass, id);
    }

    public void update(T entity) {
		getEntityManager().merge(entity);
    }

    public void delete(T entity) {
		getEntityManager().remove(getEntityManager().merge(entity));
    }
    
    public T find(Object id) {
    	return getEntityManager().find(entityClass, id);
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> findRange(int[] range) {
    	javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
    	cq.select(cq.from(entityClass));
    	javax.persistence.Query q = getEntityManager().createQuery(cq);
    	q.setMaxResults(range[1] - range[0]);
    	q.setFirstResult(range[0]);
    	return q.getResultList();
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> findAll() {
    	javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
    	cq.select(cq.from(entityClass));
    	return getEntityManager().createQuery(cq).getResultList();
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    public int count() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
    	javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
    	cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
    	return ((Long) q.getSingleResult()).intValue();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> findByPath(String[][] attributes, String[] values, String[] order, int index, 
			int size, boolean isAsc, boolean isDistinct) {
    	getEntityManager().clear();
		// Se crea un criterio de consulta
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClass);
		
		// FROM
		Root<T> root = criteriaQuery.from(entityClass);
		
		// SELECT
		criteriaQuery.select(root);
		
		// Predicados, combinados con AND
		if(attributes != null && values != null) {
			Predicate predicate = criteriaBuilder.conjunction();
			for (int i = 0; i < attributes.length; i++) {
				Path path = root.get(attributes[i][0]);
				for (int j = 1; j < attributes[i].length; j++) {
					path = path.get(attributes[i][j]);
				}
				predicate = criteriaBuilder.and(predicate, getSig(criteriaBuilder, path.as(String.class), values[i]));
			}
			
			// WHERE 
			criteriaQuery.where(predicate);
		}
		
		// ORDER
		if (order != null) {
			Path orderPath = root.get(order[0]);
			if (order.length > 1) {
				for (int i = 1; i < order.length; i++) {
					orderPath = orderPath.get(order[i]);
				}
				
			} 
			if (isAsc)
				criteriaQuery.orderBy(criteriaBuilder.asc(orderPath));
			else
				criteriaQuery.orderBy(criteriaBuilder.desc(orderPath));
		}
		
		criteriaQuery.distinct(isDistinct);
		
		// Resultado
		if (index >= 0 && size > 0) {
			TypedQuery<T> tq = getEntityManager().createQuery(criteriaQuery);
			tq.setFirstResult(index);
			tq.setMaxResults(size);
			return (List<T>) tq.getResultList();
		} else {
			// Se realiza la Query
			Query query = getEntityManager().createQuery(criteriaQuery);
			return (List<T>) query.getResultList();
		}
	}
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> findByJoin(String[] entities, String[][] attributes, String[] values, String order, int index,
			int size, boolean isDistinct) {
    	getEntityManager().clear();
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(this.entityClass);
		Root<T> root = criteriaQuery.from(this.entityClass);
		criteriaQuery.select(root);
		Predicate predicate = criteriaBuilder.conjunction();
		Predicate sig;
		int k = 0;
		if(entities[k].isEmpty()) {
			sig = getSig(criteriaBuilder, root.get(attributes[k][0]).as(String.class), values[k]);
			predicate = criteriaBuilder.and(predicate, sig);
			k++;
		}
		Join join = root.join(entities[k]);
		if(!attributes[k][0].isEmpty()) {
			sig = getSig(criteriaBuilder, join.get(attributes[k][0]).as(String.class), values[k]);
			predicate = criteriaBuilder.and(predicate, sig);
		}
		k++;
		for (int i = k; i < entities.length; i++) {
			join = join.join(entities[i]);
			for (int j = 0; j < attributes[i].length; j++) {
				if(!attributes[i][j].isEmpty()) {
					sig = getSig(criteriaBuilder, join.get(attributes[i][j]).as(String.class), values[i]);
					predicate = criteriaBuilder.and(predicate, sig);
				}
			}
		}
		criteriaQuery.where(predicate);
		if (order != null) criteriaQuery.orderBy(criteriaBuilder.asc(root.get(order)));
		criteriaQuery.distinct(isDistinct);
		if (index >= 0 && size > 0) {
			TypedQuery<T> tq = getEntityManager().createQuery(criteriaQuery);
			tq.setFirstResult(index);
			tq.setMaxResults(size);
			return (List<T>) tq.getResultList();
		} else {
			Query query = getEntityManager().createQuery(criteriaQuery);
			return (List<T>) query.getResultList();
		}
	}
	
	public Predicate getSig(CriteriaBuilder criteriaBuilder, 
			javax.persistence.criteria.Expression<String> exp, String value) {
		Predicate sig = null;
		String[] keys = value.split("&");
		switch (keys[0]) {
		case "like":
			sig = criteriaBuilder.like(exp, keys[1]);
			break;
		case "equal":
			sig = criteriaBuilder.equal(exp, keys[1]);
			break;
		case "notLike":
			sig = criteriaBuilder.notLike(exp, keys[1]);
			break;
		case ">":
			sig = criteriaBuilder.greaterThan(exp, keys[1]);
			break;
		default:
			System.out.println(">>> Error >> AbstractFacade:getSig:No se encuentra la opción > " + keys[0]);
			break;
		}
		return sig;
	}
}
