package edu.spatial.election.database;

import java.util.LinkedList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class DatabaseConnection {

	private static ServiceRegistry serviceRegistry;
	private static SessionFactory sessionFactory;
	private static LinkedList<Session> openedSessions = new LinkedList<Session>();
	

	public static Session openSession() {
		Session out = getSessionFactory().openSession();
		openedSessions.add(out);
		return out;
	}

	public static void close() {
		if(sessionFactory!=null) {
			for(Session s : openedSessions) {
				if(s.isOpen()) {
					s.flush();
					s.close();
				}
			}
			if(!sessionFactory.isClosed()) {
				sessionFactory.close();
			}
		}
	}
	

	private static SessionFactory configureSessionFactory() throws HibernateException {
	    Configuration configuration = new Configuration();
	    configuration.configure();
	    serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    return sessionFactory;
	}
	
	private static SessionFactory getSessionFactory() {
		if(sessionFactory==null) {
			configureSessionFactory();
		}
		return sessionFactory;
	}
}
