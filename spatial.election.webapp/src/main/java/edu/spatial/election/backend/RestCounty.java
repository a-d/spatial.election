package edu.spatial.election.backend;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;

import edu.spatial.election.database.DatabaseConnection;
import edu.spatial.election.database.dao.CountyDAO;
import edu.spatial.election.database.dao.SpatialDAOFactory;
import edu.spatial.election.database.exceptions.CountyNotFoundException;
import edu.spatial.election.domain.County;


@Path("/county")
public class RestCounty {
	private SpatialDAOFactory f = SpatialDAOFactory.getDAOFactory(SpatialDAOFactory.POSTGIS);


	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<County> getAllCounties() {
		return getAllCountiesByDetail(0);
	}
	
	@GET
	@Path("/detail/{level}")
	@Produces({MediaType.APPLICATION_JSON})
	public List<County> getAllCountiesByDetail(@PathParam("level") double level) {

		Session s = DatabaseConnection.openSession();

		// Create a DAO
		CountyDAO countyDAO = f.getCountyDAO();
		countyDAO.setConnection(s);

		List<County> cs = countyDAO.getCounties();
		for(County c : cs)
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
	public County getCountyById(@PathParam("id") long id) throws CountyNotFoundException {
		
		Session s = DatabaseConnection.openSession();

		// Create a DAO
		CountyDAO countyDAO = f.getCountyDAO();
		countyDAO.setConnection(s);

		County c = countyDAO.findCountyById(id);
		
		s.close();
		return c;
	}
}