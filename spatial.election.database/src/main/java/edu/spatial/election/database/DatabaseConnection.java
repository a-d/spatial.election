package edu.spatial.election.database;

import java.util.LinkedList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.HibernateException;

public class DatabaseConnection {

	private static EntityManagerFactory managerFactory;
	private static LinkedList<EntityManager> openedManagers = new LinkedList<EntityManager>();
	

	public static EntityManager createManager() {
		EntityManager out = getManagerFactory().createEntityManager();
		openedManagers.add(out);
		return out;
	}

	public static void close() {
		if(managerFactory!=null) {
			for(EntityManager em : openedManagers) {
				if(em.isOpen()) {
					em.flush();
					em.close();
				}
			}
			if(managerFactory.isOpen()) {
				managerFactory.close();
			}
		}
	}
	

	private static EntityManagerFactory createManagerFactory() throws HibernateException {
	    managerFactory = Persistence.createEntityManagerFactory( "edu.spatial.election" );
	    return managerFactory;
	}
	
	private static EntityManagerFactory getManagerFactory() {
		if(managerFactory==null) {
			synchronized(openedManagers)
			{
				if(managerFactory==null) {
					createManagerFactory();
				}
			}
		}
		return managerFactory;
	}
}
