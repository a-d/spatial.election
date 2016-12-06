package edu.spatial.election.backend;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
		EntityManager em = DatabaseConnection.createManager();

		// Create a DAO
		ConstituencyDAO constituencyDAO = f.getConstituencyDAO();
		constituencyDAO.setEntityManager(em);

		List<Constituency> cs = constituencyDAO.getConstituencies();
		em.close();
		return cs;
	}
	
	@GET
	@Path("{id}/geometry")
	@Produces({MediaType.APPLICATION_JSON})
	public double[][][] getConstituencyGeometryById(@PathParam("id") int id) throws ConstituencyNotFoundException {
		EntityManager em = DatabaseConnection.createManager();

		// Create a DAO
		ConstituencyDAO constituencyDAO = f.getConstituencyDAO();
		constituencyDAO.setEntityManager(em);

		Constituency c = constituencyDAO.findConstituencyById(id);
		c.setGeometryDetail(0);
		c.getGeometryArray();
		double[][][] result = c.getGeometryArray();
		
		em.close();
		return result;
	}

	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Constituency getConstituencyById(@PathParam("id") int id) throws ConstituencyNotFoundException {
		EntityManager em = DatabaseConnection.createManager();

		// Create a DAO
		ConstituencyDAO constituencyDAO = f.getConstituencyDAO();
		constituencyDAO.setEntityManager(em);

		Constituency c = constituencyDAO.findConstituencyById(id);
		em.close();
		
		
		// cannot return c because it is just a Hibernate facade
		// that's why we need to create a properties-save proxy
		c = Constituency.createSaveProxy(c);

		return c;
	}
}