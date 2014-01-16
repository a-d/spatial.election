package spatialdb.backend.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import spatialdb.backend.dao.ConstituencyDAO;
import spatialdb.backend.dao.SpatialDAOFactory;
import spatialdb.backend.exception.ConstituencyNotFoundException;
import spatialdb.backend.geo.Constituency;
import spatialdb.backend.util.DatabaseConstants;

@Path("/constituency")
public class RestConstituency {

	private SpatialDAOFactory f = SpatialDAOFactory.getDAOFactory(SpatialDAOFactory.POSTGIS);
	private static final String connectionURL = DatabaseConstants.DRIVER + "://" + DatabaseConstants.HOST_NAME + 
			":" + DatabaseConstants.PORT + "/" + DatabaseConstants.DATABASE_NAME;
	private Connection conn;


	@GET
	@Produces("application/json")
	public Constituency getAllConstituencies() throws ConstituencyNotFoundException, SQLException {
		return getConstituencyById(67);
	}

	@GET
	@Path("{id}")
	@Produces("application/json")
	public Constituency getConstituencyById(@PathParam("id") long id) throws SQLException, ConstituencyNotFoundException {

		conn = DriverManager.getConnection(connectionURL, DatabaseConstants.USER_NAME, DatabaseConstants.PASSWORD);
		conn.setAutoCommit(false);

		// Create a DAO
		ConstituencyDAO constituencyDAO = f.getConstituencyDAO();
		constituencyDAO.setConnection(conn);

		Constituency c = constituencyDAO.findConstituencyById(id);
		//System.out.println(c.getLand_name() + "  " + c.getWkr_name() + "   " + c.getGid() + "  " + c.getLand_nr() + "  " + c.getWkr_nr() + "  " + c.getGeom());
		//c.setGeom(null);

		//		Collection<Constituency> al = constituencyDAO.findConstituencyByState("Berlin");
		//		for (Constituency x : al) {
		//			System.out.print(" x.id " + x.getGid());
		//		}

		conn.commit();	

		return c;
	}
}