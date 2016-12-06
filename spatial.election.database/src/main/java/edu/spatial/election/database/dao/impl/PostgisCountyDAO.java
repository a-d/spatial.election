package edu.spatial.election.database.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.spatial.election.database.dao.CountyDAO;
import edu.spatial.election.database.exceptions.ConstituencyNotFoundException;
import edu.spatial.election.database.exceptions.CountyNotFoundException;
import edu.spatial.election.domain.Constituency;
import edu.spatial.election.domain.County;

public class PostgisCountyDAO implements CountyDAO {

	static private final Log log = LogFactory.getLog(PostgisCountyDAO.class);
	private CriteriaBuilder cb;
	private EntityManager em;




	public void setEntityManager(EntityManager em) {
		this.em = em;
		this.cb = em.getCriteriaBuilder();
	}



	public County findCountyById(int id) throws CountyNotFoundException {
		log.info("retrieving constituency with ID " + id);

		County out = null;
		try
		{
			out = em.find(County.class, id);
		}
		catch(Exception e)
		{
			throw new CountyNotFoundException("Could not find County by Id \""+id+"\".", e);
		}
		return out;
	}



	public List<County> findCountyByState(String stateName) {
		log.info("retrieving counties with state name " + stateName);

		CriteriaQuery<County> cq = cb.createQuery(County.class);
		CriteriaQuery<County> query = cq.where(cb.equal(cq.from(Constituency.class).get("stateName"), stateName));
		List<County> out = em.createQuery(query).getResultList();
		
		return out;
	}



	public List<County> findCountyByDistrict(String districtName) {
		log.info("retrieving counties with district name " + districtName);

		CriteriaQuery<County> cq = cb.createQuery(County.class);
		CriteriaQuery<County> query = cq.where(cb.equal(cq.from(Constituency.class).get("districtName"), districtName));
		List<County> out = em.createQuery(query).getResultList();
		
		return out;
	}



	public List<County> getCounties() {
		CriteriaQuery<County> q = cb.createQuery(County.class);
		Root<County> r = q.from(County.class);
		return em.createQuery(q.select(r)).getResultList();
	}



	public List<Constituency> findConstituenciesOfCounty(int countyId) throws ConstituencyNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}



	public void saveCounty(County county) {
		em.persist(county);
	}



	public void deleteCounty(int id) throws CountyNotFoundException {
		em.remove(findCountyById(id));
	}

}
