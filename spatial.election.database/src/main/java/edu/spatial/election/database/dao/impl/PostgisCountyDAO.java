package edu.spatial.election.database.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import edu.spatial.election.database.dao.CountyDAO;
import edu.spatial.election.database.exceptions.ConstituencyNotFoundException;
import edu.spatial.election.database.exceptions.CountyNotFoundException;
import edu.spatial.election.domain.Constituency;
import edu.spatial.election.domain.County;

public class PostgisCountyDAO implements CountyDAO {

	static private final Log log = LogFactory.getLog(PostgisCountyDAO.class);
	private Session s;



	public void setConnection(Session s) {
		this.s = s;
	}



	public County findCountyById(long id) throws CountyNotFoundException {
		log.info("retrieving constituency with ID " + id);

		County out = null;
		try
		{
			out = (County) s.createCriteria(County.class)
					.add(Restrictions.idEq(id))
					.list()
					.get(0);
		}
		catch(Exception e)
		{
			throw new CountyNotFoundException("Could not find County by Id \""+id+"\".", e);
		}
		return out;
	}



	public List<County> findCountyByState(String stateName) {
		log.info("retrieving counties with state name " + stateName);

		@SuppressWarnings("unchecked")
		List<County> out = (List<County>) s.createCriteria(County.class)
					.add(Restrictions.eq("stateName", stateName))
					.list();
		return out;
	}



	public List<County> findCountyByDistrict(String districtName) {
		log.info("retrieving counties with district name " + districtName);

		@SuppressWarnings("unchecked")
		List<County> out = (List<County>) s.createCriteria(County.class)
					.add(Restrictions.eq("districtName", districtName))
					.list();
		return out;
	}



	@SuppressWarnings("unchecked")
	public List<County> getCounties() {
		return (List<County>) s.createCriteria(County.class).list();
	}



	public List<Constituency> findConstituenciesOfCounty(long countyId) throws ConstituencyNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}



	public void saveCounty(County county) {
		s.saveOrUpdate(county);
	}



	public void deleteCounty(long id) throws CountyNotFoundException {
		s.delete(findCountyById(id));
	}

}
