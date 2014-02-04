package edu.spatial.election.backend;

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
	public List<Constituency> getAllConstituencies() {
		Session s = DatabaseConnection.openSession();

		// Create a DAO
		ConstituencyDAO constituencyDAO = f.getConstituencyDAO();
		constituencyDAO.setConnection(s);

		List<Constituency> cs = constituencyDAO.getConstituencies();
		s.close();
		return cs;
	}
	
	@GET
	@Path("{id}/geometry")
	@Produces({MediaType.APPLICATION_JSON})
	public double[][][] getConstituencyGeometryById(@PathParam("id") long id) throws ConstituencyNotFoundException {
		Session s = DatabaseConnection.openSession();

		// Create a DAO
		ConstituencyDAO constituencyDAO = f.getConstituencyDAO();
		constituencyDAO.setConnection(s);

		Constituency c = constituencyDAO.findConstituencyById(id);
		c.setGeometryDetail(0);
		c.getGeometryArray();
		double[][][] result = c.getGeometryArray();
		
		s.close();
		return result;
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