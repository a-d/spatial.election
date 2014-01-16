package spatialdb.backend.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import spatialdb.backend.dao.CountyDAO;
import spatialdb.backend.exception.CountyNotFoundException;
import spatialdb.backend.geo.Constituency;
import spatialdb.backend.geo.County;

public class PostgisCountyDAO implements CountyDAO{
	
	static private final Log log = LogFactory.getLog(PostgisCountyDAO.class);
	private Connection conn;
	private PreparedStatement p;
	private StringBuffer stmt;

	@Override
	public County findCountyById(long id) throws CountyNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<County> findCountyByState(String stateName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<County> findCountyByDistrict(String districtName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Constituency> findConstituenciesOfCounty(long countyId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void updateCounty(County county) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCounty(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createCounty() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

}
