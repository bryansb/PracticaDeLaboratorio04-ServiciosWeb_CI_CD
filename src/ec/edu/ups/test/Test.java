package ec.edu.ups.test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Test {
    
    public static void main(String args[]) {
    	EntityManager em = Persistence.createEntityManagerFactory("Practica03").createEntityManager();
    	em.isOpen();
    	
    }

}
