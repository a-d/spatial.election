package spatialdb.backend.dao;

import java.util.Collection;

import spatialdb.backend.exception.CountyNotFoundException;
import spatialdb.backend.geo.Constituency;
import spatialdb.backend.geo.County;

public interface CountyDAO extends SimpleDAO{
	
	public County findCountyById(long id)
		throws CountyNotFoundException;
	
	public Collection<County> findCountyByState(String stateName);
	
	public Collection<County> findCountyByDistrict(String districtName);
	
	public Collection<Constituency> findConstituenciesOfCounty(long countyId);
	
	public void updateCounty(County county);
	
	public void deleteCounty(long id);
	
	public void createCounty();
}
