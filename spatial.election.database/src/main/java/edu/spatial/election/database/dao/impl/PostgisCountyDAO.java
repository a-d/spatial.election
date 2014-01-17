package edu.spatial.election.database.dao.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import edu.spatial.election.database.dao.CountyDAO;
import edu.spatial.election.database.exceptions.CountyNotFoundException;
import edu.spatial.election.domain.Constituency;
import edu.spatial.election.domain.County;

public class PostgisCountyDAO implements CountyDAO {

	static private final Log log = LogFactory.getLog(PostgisCountyDAO.class);
	private Session s;


	public County findCountyById(long id) throws CountyNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}


	public Collection<County> findCountyByState(String stateName) {
		// TODO Auto-generated method stub
		return null;
	}


	public Collection<County> findCountyByDistrict(String districtName) {
		// TODO Auto-generated method stub
		return null;
	}


	public Collection<Constituency> findConstituenciesOfCounty(long countyId) {
		// TODO Auto-generated method stub
		return null;
	}


	public void saveCounty(County county) {
		// TODO Auto-generated method stub

	}


	public void deleteCounty(long id) {
		// TODO Auto-generated method stub

	}

	public void setConnection(Session s) {
		this.s = s;
	}

}
