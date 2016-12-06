package edu.spatial.election.database.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import edu.spatial.election.database.dao.ConstituencyDAO;
import edu.spatial.election.database.exceptions.ConstituencyNotFoundException;
import edu.spatial.election.domain.Constituency;

public class PostgisConstituencyDAO implements ConstituencyDAO {

	static private final Log log = LogFactory.getLog(PostgisConstituencyDAO.class);
	private CriteriaBuilder cb;
	private EntityManager em;

	public Constituency findConstituencyById(int id) throws ConstituencyNotFoundException {
		log.info("retrieving constituency with ID " + id);

		Constituency out = null;
		try
		{
			out = em.find(Constituency.class, id);
		}
		catch(Exception e)
		{
			throw new ConstituencyNotFoundException("Could not find Constituency by Id \""+id+"\".", e);
		}
		return out;
	}

	public List<Constituency> findConstituencyByState(String stateName) {
		log.info("retrieving constituencies with state name " + stateName);
		CriteriaQuery<Constituency> cq = cb.createQuery(Constituency.class);
		CriteriaQuery<Constituency> query = cq.where(cb.equal(cq.from(Constituency.class).get("land_name"), stateName));
		List<Constituency> out = em.createQuery(query).getResultList();
		return out;
	}

	public void saveConstituency(Constituency c) {
		em.persist(c);
	}

	public void deleteConstituency(int id) throws ConstituencyNotFoundException {
		em.remove(findConstituencyById(id));
	}

	public List<Constituency> getConstituencies() {
		CriteriaQuery<Constituency> q = cb.createQuery(Constituency.class);
		return em.createQuery(q.select(q.from(Constituency.class))).getResultList();
	}

	public void setEntityManager(EntityManager em) {
		this.em = em;
		this.cb = em.getCriteriaBuilder();
	}
}
