package spatialdb.backend.dao;

import java.sql.SQLException;
import java.util.Collection;

import spatialdb.backend.exception.ConstituencyNotFoundException;
import spatialdb.backend.geo.Constituency;

public interface ConstituencyDAO extends SimpleDAO{

	public Constituency findConstituencyById(long id)
			throws ConstituencyNotFoundException, SQLException;

	public Collection<Constituency> findConstituencyByState(String stateName)
			throws SQLException;

	public void updateConstituency(Constituency constituency)
			throws SQLException;

	public void deleteConstituency(long id)
			throws SQLException;

	public void createConstituency()
			throws SQLException;

}
