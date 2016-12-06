package edu.spatial.election.backend;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.spatial.election.database.DatabaseConnection;
import edu.spatial.election.database.dao.PartyDAO;
import edu.spatial.election.database.dao.SpatialDAOFactory;
import edu.spatial.election.database.exceptions.PartyNotFoundException;
import edu.spatial.election.domain.Party;


@Path("/party")
public class RestParty {
	private SpatialDAOFactory f = SpatialDAOFactory.getDAOFactory(SpatialDAOFactory.POSTGIS);


	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Party> getAllParties() {

		EntityManager em = DatabaseConnection.createManager();

		// Create a DAO
		PartyDAO partyDAO = f.getPartyDAO();
		partyDAO.setEntityManager(em);

		List<Party> cs = partyDAO.getParties();

		return cs;
	}


	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Party getPartyById(@PathParam("id") int id) throws PartyNotFoundException {
		
		EntityManager em = DatabaseConnection.createManager();
		
		// Create a DAO
		PartyDAO partyDAO = f.getPartyDAO();
		partyDAO.setEntityManager(em);

		Party c = partyDAO.findPartyById(id);
		
		em.close();
		return c;
	}
}