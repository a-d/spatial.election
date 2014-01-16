package spatialdb.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

import spatialdb.backend.dao.ConstituencyDAO;
import spatialdb.backend.dao.SpatialDAOFactory;
import spatialdb.backend.geo.Constituency;
import spatialdb.backend.util.DatabaseConstants;

public class App {

	public static void main(String[] args) throws SQLException {

		// create the required DAO Factory
		SpatialDAOFactory f =  SpatialDAOFactory.getDAOFactory(SpatialDAOFactory.POSTGIS);

		String connectionURL = DatabaseConstants.DRIVER + "://" + DatabaseConstants.HOST_NAME + 
				":" + DatabaseConstants.PORT + "/" + DatabaseConstants.DATABASE_NAME;

		Connection conn = DriverManager.getConnection(connectionURL, 
				DatabaseConstants.USER_NAME, DatabaseConstants.PASSWORD);;
		
		try {
			conn.setAutoCommit(false);

			// Create a DAO
			ConstituencyDAO constituencyDAO = f.getConstituencyDAO();
			constituencyDAO.setConnection(conn);

			Constituency c = constituencyDAO.findConstituencyById(67);
			System.out.println(c);
			
			Collection<Constituency> al = constituencyDAO.findConstituencyByState("Berlin");
			for (Constituency x : al) {
				System.out.print(x);
			}
			
			conn.commit();

		} catch(Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
