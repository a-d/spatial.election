package edu.spatial.election.database;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import edu.spatial.election.database.dao.impl.PostgisCountyDAO;
import edu.spatial.election.domain.County;
import edu.spatial.election.domain.Party;

public class Usage {

	public static void main(String[] args) {
		
		EntityManager em = DatabaseConnection.createManager();
		
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Party> q = cb.createQuery(Party.class);
			List<Party> parties = em.createQuery(q.select(q.from(Party.class))).getResultList();
			System.out.println(parties);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			PostgisCountyDAO dao = new PostgisCountyDAO();
			dao.setEntityManager(em);
			List<County> counties = dao.getCounties();
			System.out.println(counties);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		em.close();
		DatabaseConnection.close();
	}
}
