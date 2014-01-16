package spatialdb.backend.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import spatialdb.backend.dao.ConstituencyDAO;
import spatialdb.backend.exception.ConstituencyNotFoundException;
import spatialdb.backend.geo.Constituency;
import spatialdb.backend.util.DatabaseConstants;

public class PostgisConstituencyDAO implements ConstituencyDAO{

	static private final Log log = LogFactory.getLog(PostgisConstituencyDAO.class);
	private Connection conn;
	private PreparedStatement p;
	private StringBuffer stmt;

	@Override
	public Constituency findConstituencyById(long id)
			throws ConstituencyNotFoundException, SQLException {

		log.info("retrieving constituency with ID " + id);

		stmt = new StringBuffer(200);

		stmt.append("SELECT ");
		stmt.append("c.gid as gid, ");
		stmt.append("c.wkr_nr as nr, ");
		stmt.append("c.wkr_name as name, ");
		stmt.append("c.land_nr as state_nr, ");
		stmt.append("c.land_name as state, ");
		stmt.append("st_asgeojson(c.geom) as geometry ");
		stmt.append("FROM ");
		stmt.append(DatabaseConstants.CONSTITUENCY_TABLE_NAME);
		stmt.append(" as c ");
		stmt.append("WHERE c.gid = ?");

		p = conn.prepareStatement(stmt.toString());
		p.setLong(1, id);
		p.executeQuery();
		
		ResultSet rs = p.getResultSet();

		return resultSetToConstituency(rs);
	}

	@Override
	public Collection<Constituency> findConstituencyByState(String stateName) 
			throws SQLException {

		stmt = new StringBuffer(200);

		stmt.append("SELECT ");
		stmt.append("c.gid as gid, ");
		stmt.append("c.wkr_nr as nr, ");
		stmt.append("c.wkr_name as name, ");
		stmt.append("c.land_nr as state_nr, ");
		stmt.append("c.land_name as state, ");
		stmt.append("st_asgeojson(c.geom) as geometry ");
		stmt.append("FROM ");
		stmt.append(DatabaseConstants.CONSTITUENCY_TABLE_NAME);
		stmt.append(" as c ");
		stmt.append("WHERE c.land_name = ?");


		p = conn.prepareStatement(stmt.toString());
		p.setString(1, stateName);
		p.executeQuery();
		
		ResultSet rs = p.getResultSet();
		ArrayList<Constituency> result = new ArrayList<Constituency>();
		
		while (!rs.isLast()) {
			rs.next();
			
			Constituency c = resultSetToConstituency(rs);
			result.add(c);
		}

		return result;
	}

	@Override
	public void updateConstituency(Constituency c) 
			throws SQLException {
		stmt = new StringBuffer(200);

		stmt.append("UPDATE ");
		stmt.append(DatabaseConstants.CONSTITUENCY_TABLE_NAME);
		stmt.append("SET ");
		stmt.append("wkr_nr = ?, ");
		stmt.append("wkr_name = ?, ");
		stmt.append("land_nr = ?, ");
		stmt.append("land_name = ?, ");
		stmt.append("geom = ? ");
		stmt.append("WHERE ");
		stmt.append("gid = ?");
		
		p = conn.prepareStatement(stmt.toString());
		p.setInt(1, c.getWkr_nr());
		p.setString(2, c.getWkr_name());
		p.setString(3, c.getLand_nr());
		p.setString(4, c.getLand_name());
		p.setLong(5, c.getGid());
		
		p.executeQuery();
	}

	@Override
	public void deleteConstituency(long id) 
			throws SQLException {
		
		stmt = new StringBuffer(200);
		
		stmt.append("DELETE FROM ");
		stmt.append(DatabaseConstants.CONSTITUENCY_TABLE_NAME);
		stmt.append("WHERE ");
		stmt.append("gid = ?");

		p = conn.prepareStatement(stmt.toString());
		p.setLong(1, id);
		
		p.executeQuery();
	}

	@Override
	public void createConstituency() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	private Constituency resultSetToConstituency(ResultSet rs) {

		Constituency c = new Constituency();	
		try {
			rs.next();			
			c.setGid(rs.getLong(1));
			c.setWkr_nr(new Integer(rs.getString(2)));
			c.setWkr_name(rs.getString(3));
			c.setLand_nr(rs.getString(4));
			c.setLand_name(rs.getString(5));
			c.setJsonRepresentation(rs.getString(6));
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		
		return c;
	}
}
