package edu.spatial.election.backend;

import java.sql.SQLException;
import java.util.Collection;

import org.hibernate.Session;

import edu.spatial.election.database.DatabaseConnection;
import edu.spatial.election.database.dao.ConstituencyDAO;
import edu.spatial.election.database.dao.SpatialDAOFactory;
import edu.spatial.election.domain.Constituency;


public class App {

	public static void main(String[] args) throws SQLException {

		// create the required DAO Factory
		SpatialDAOFactory f =  SpatialDAOFactory.getDAOFactory(SpatialDAOFactory.POSTGIS);

		try {
			Session s = DatabaseConnection.openSession();

			// Create a DAO
			ConstituencyDAO constituencyDAO = f.getConstituencyDAO();
			constituencyDAO.setConnection(s);

			Constituency c = constituencyDAO.findConstituencyById(67);
			System.out.println(c);
			
			Collection<Constituency> al = constituencyDAO.findConstituencyByState("Berlin");
			for (Constituency x : al) {
				System.out.print(x);
			}
			
			s.close();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
