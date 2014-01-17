package edu.spatial.election.database.dao;

import java.util.Collection;

import edu.spatial.election.database.exceptions.CountyNotFoundException;
import edu.spatial.election.domain.Constituency;
import edu.spatial.election.domain.County;


public interface CountyDAO extends SimpleDAO{
	
	public County findCountyById(long id) throws CountyNotFoundException;
	
	public Collection<County> findCountyByState(String stateName);
	
	public Collection<County> findCountyByDistrict(String districtName);
	
	public Collection<Constituency> findConstituenciesOfCounty(long countyId);
	
	public void saveCounty(County county);
	
	public void deleteCounty(long id);

}
