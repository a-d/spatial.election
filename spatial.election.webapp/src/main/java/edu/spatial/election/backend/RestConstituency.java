package edu.spatial.election.backend;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;

import edu.spatial.election.database.DatabaseConnection;
import edu.spatial.election.database.dao.ConstituencyDAO;
import edu.spatial.election.database.dao.SpatialDAOFactory;
import edu.spatial.election.database.exceptions.ConstituencyNotFoundException;
import edu.spatial.election.domain.Constituency;


@Path("/constituency")
public class RestConstituency {
	private SpatialDAOFactory f = SpatialDAOFactory.getDAOFactory(SpatialDAOFactory.POSTGIS);


	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Constituency> getAllConstituencies() throws ConstituencyNotFoundException, SQLException {
		return getAllConstituenciesByDetail(0);
	}
	
	@GET
	@Path("/detail/{level}")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Constituency> getAllConstituenciesByDetail(@PathParam("level") double level) {

		Session s = DatabaseConnection.openSession();

		// Create a DAO
		ConstituencyDAO constituencyDAO = f.getConstituencyDAO();
		constituencyDAO.setConnection(s);

		List<Constituency> cs = constituencyDAO.findConstituenciesByState();
		for(Constituency c : cs)
		{
			c.setGeometryDetail(level);
			c.getGeometryArray();
		}
		
		s.close();
		return cs;
	}

	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Constituency getConstituencyById(@PathParam("id") long id) throws ConstituencyNotFoundException {
		
		Session s = DatabaseConnection.openSession();

		// Create a DAO
		ConstituencyDAO constituencyDAO = f.getConstituencyDAO();
		constituencyDAO.setConnection(s);

		Constituency c = constituencyDAO.findConstituencyById(id);
		
		s.close();
		return c;
	}
}