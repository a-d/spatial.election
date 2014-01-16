package spatialdb.backend.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import spatialdb.backend.dao.StateDAO;
import spatialdb.backend.exception.StateNotFoundException;
import spatialdb.backend.geo.Constituency;
import spatialdb.backend.geo.County;
import spatialdb.backend.geo.State;

public class PostgisStateDAO implements StateDAO {

	static private final Log log = LogFactory.getLog(PostgisStateDAO.class);
	private Connection conn;
	private PreparedStatement p;
	private StringBuffer stmt;

	@Override
	public State findStateById(long id) throws StateNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteState(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<County> findCountiesOfState(long stateId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Constituency> findConstituenciesOfState(long stateId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
}
