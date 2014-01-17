package edu.spatial.election.database.dao;

import java.util.Collection;

import edu.spatial.election.database.exceptions.StateNotFoundException;
import edu.spatial.election.domain.Constituency;
import edu.spatial.election.domain.County;
import edu.spatial.election.domain.State;

public interface StateDAO extends SimpleDAO{
	
	public State findStateById(long id) throws StateNotFoundException;

	public Collection<County> findCountiesOfState(long stateId);
	
	public Collection<Constituency> findConstituenciesOfState(long stateId);
	
	public void updateState();
	
	public void deleteState(long id);
}
