package edu.spatial.election.database.dao;

import java.util.Collection;

import edu.spatial.election.database.exceptions.ConstituencyNotFoundException;
import edu.spatial.election.domain.Constituency;


public interface ConstituencyDAO extends SimpleDAO{

	public Constituency findConstituencyById(long id) throws ConstituencyNotFoundException;

	public Collection<Constituency> findConstituencyByState(String stateName);

	public void saveConstituency(Constituency constituency);

	public void deleteConstituency(long id) throws ConstituencyNotFoundException;

}
