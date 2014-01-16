package spatialdb.backend.dao.impl;

import spatialdb.backend.dao.SpatialDAOFactory;

public class PostgisDAOFactory extends SpatialDAOFactory{

	@Override
	public PostgisCountyDAO getCountyDAO() {
		return new PostgisCountyDAO();
	}

	@Override
	public PostgisConstituencyDAO getConstituencyDAO() {
		return new PostgisConstituencyDAO();
	}

	@Override
	public PostgisStateDAO getStateDAO() {
		return new PostgisStateDAO();
	}

}
