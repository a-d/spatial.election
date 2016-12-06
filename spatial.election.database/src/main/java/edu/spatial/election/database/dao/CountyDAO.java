package edu.spatial.election.database.dao;

import java.util.List;

import edu.spatial.election.database.exceptions.ConstituencyNotFoundException;
import edu.spatial.election.database.exceptions.CountyNotFoundException;
import edu.spatial.election.domain.Constituency;
import edu.spatial.election.domain.County;


public interface CountyDAO extends SimpleDAO{
	
	public County findCountyById(int id) throws CountyNotFoundException;
	public List<County> findCountyByState(String stateName);
	public List<County> findCountyByDistrict(String districtName);
	public List<County> getCounties();
	public List<Constituency> findConstituenciesOfCounty(int countyId) throws ConstituencyNotFoundException;
	public void saveCounty(County county);
	public void deleteCounty(int id) throws CountyNotFoundException;

}
