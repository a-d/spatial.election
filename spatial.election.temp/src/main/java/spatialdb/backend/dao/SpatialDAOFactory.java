package spatialdb.backend.dao;

import spatialdb.backend.dao.impl.PostgisConstituencyDAO;
import spatialdb.backend.dao.impl.PostgisCountyDAO;
import spatialdb.backend.dao.impl.PostgisDAOFactory;
import spatialdb.backend.dao.impl.PostgisStateDAO;


//Abstract class DAO Factory
public abstract class SpatialDAOFactory {

	// List of DAO types supported by the factory
	public static final int POSTGIS = 1;

	// There will be a method for each DAO that can be 
	// created. The concrete factories will have to 
	// implement these methods.
	public abstract PostgisCountyDAO getCountyDAO();
	public abstract PostgisConstituencyDAO getConstituencyDAO();
	public abstract PostgisStateDAO getStateDAO();

	public static SpatialDAOFactory getDAOFactory(int whichFactory) {

		switch (whichFactory) {
			case POSTGIS: 
				return new PostgisDAOFactory();
			default: 
				return null;
		}
	}
}
