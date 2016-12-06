package edu.spatial.election.database.dao;

import java.util.List;

import edu.spatial.election.database.exceptions.ConstituencyNotFoundException;
import edu.spatial.election.domain.Constituency;


public interface ConstituencyDAO extends SimpleDAO {

	public Constituency findConstituencyById(int id) throws ConstituencyNotFoundException;
	public List<Constituency> findConstituencyByState(String stateName);
	public void saveConstituency(Constituency constituency);
	public void deleteConstituency(int id) throws ConstituencyNotFoundException;
	public List<Constituency> getConstituencies();

}
