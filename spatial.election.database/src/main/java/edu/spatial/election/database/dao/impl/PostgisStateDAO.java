package edu.spatial.election.database.dao.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import edu.spatial.election.database.dao.StateDAO;
import edu.spatial.election.database.exceptions.StateNotFoundException;
import edu.spatial.election.domain.Constituency;
import edu.spatial.election.domain.County;
import edu.spatial.election.domain.State;

public class PostgisStateDAO implements StateDAO {

	static private final Log log = LogFactory.getLog(PostgisStateDAO.class);
	private Session s;

	
	public State findStateById(long id) throws StateNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateState() {
		// TODO Auto-generated method stub

	}

	public void deleteState(long id) {
		// TODO Auto-generated method stub

	}

	public Collection<County> findCountiesOfState(long stateId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Constituency> findConstituenciesOfState(long stateId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setConnection(Session s) {
		this.s = s;
	}
}
