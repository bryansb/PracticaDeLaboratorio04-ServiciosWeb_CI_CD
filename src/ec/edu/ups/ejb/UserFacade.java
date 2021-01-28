package ec.edu.ups.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entities.User;
import ec.edu.ups.utils.MathFunction;

@Stateless
public class UserFacade extends AbstractFacade<User> {

	@PersistenceContext(unitName = "Practica03")
    private EntityManager em;
	
    public UserFacade() {
    	super(User.class);
    }

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	private static final String LOGIN_JPQL = "SELECT u FROM User u WHERE "
			+ "(u.email = :key "
			+ "OR u.username = :key) "
			+ "AND u.password = :password "
			+ "AND u.deleted = 0";
	
	public User loginUser(String key, String password) {
		try {
			return (User) em.createQuery(LOGIN_JPQL)
					.setParameter("key", key)
					.setParameter("password", password)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		
		
	}
	
	public User login(String email, String password) {
		User user;
		password = MathFunction.getMd5(password);
		
		String[][] attributes = {{"email"}, {"password"}, {"deleted"}};
		String[] values = {"equal&" + email, "equal&" + password, "equal&0"};
		try {
			user = super.findByPath(attributes, values, null, 0, 1, false, false).get(0);
		} catch (Exception e) {
			user = null;
		}
		return user;
	}
	
	public User findUserByDNI(String dni) {
		User user;
		String[][] attributes = {{"dni"}, {"deleted"}};
		String[] values = {"equal&" + dni, "equal&0"};
		try {
			user = super.findByPath(attributes, values, null, 0, 1, false, false).get(0);
		} catch (Exception e) {
			user = null;
		}
		return user;
	}
	
	public User findUserByEmail(String email) {
		User user;
		String[][] attributes = {{"email"}};
		String[] values = {"equal&" + email};
		try {
			user = super.findByPath(attributes, values, null, 0, 1, false, false).get(0);
		} catch (Exception e) {
			user = null;
		}
		return user;
	}
	
	public User findUserByUsername(String username) {
		User user;
		String[][] attributes = {{"username"}};
		String[] values = {"equal&" + username};
		try {
			user = super.findByPath(attributes, values, null, 0, 1, false, false).get(0);
		} catch (Exception e) {
			user = null;
		}
		return user;
	}
	
	public List<User> findUser(String dni) {
		List<User> userList;
		String[][] attributes = {{"dni"}, {"deleted"}, {"role"}};
		String[] values = {"like&%" + dni + "%", "equal&0", "equal&C"};
		try {
			userList = super.findByPath(attributes, values, null, 0, 0, false, false);
		} catch (Exception e) {
			userList = null;
		}
		return userList;
	}
	
	public List<User> findClients() {
		List<User> userList;
		String[][] attributes = {{"role"}};
		String[] values = {"equal&C"};
		try {
			userList = super.findByPath(attributes, values, null, 0, 0, false, false);
		} catch (Exception e) {
			userList = null;
		}
		return userList;
	}

}
