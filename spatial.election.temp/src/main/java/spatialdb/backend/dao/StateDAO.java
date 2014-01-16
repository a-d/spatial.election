package spatialdb.backend.dao;

import java.util.Collection;

import spatialdb.backend.exception.StateNotFoundException;
import spatialdb.backend.geo.Constituency;
import spatialdb.backend.geo.County;
import spatialdb.backend.geo.State;

public interface StateDAO extends SimpleDAO{
	
	public State findStateById(long id)
		throws StateNotFoundException;

	public Collection<County> findCountiesOfState(long stateId);
	
	public Collection<Constituency> findConstituenciesOfState(long stateId);
	
	public void updateState();
	
	public void deleteState(long id);
}
