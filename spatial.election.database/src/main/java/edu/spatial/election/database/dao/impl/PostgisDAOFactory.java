package edu.spatial.election.database.dao.impl;

import edu.spatial.election.database.dao.SpatialDAOFactory;

public class PostgisDAOFactory extends SpatialDAOFactory {

	@Override
	public PostgisCountyDAO getCountyDAO() {
		return new PostgisCountyDAO();
	}

	@Override
	public PostgisConstituencyDAO getConstituencyDAO() {
		return new PostgisConstituencyDAO();
	}
}
