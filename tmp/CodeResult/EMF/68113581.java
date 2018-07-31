package feri.rvir.elocator.dao;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("transactions-optional");
	
	private EMF() {}
	
	public static EntityManagerFactory getInstance() {
		return emf;
	}
}