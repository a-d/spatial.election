package edu.spatial.election.database.dao;

import org.hibernate.Session;

public interface SimpleDAO {

	public void setConnection(Session s);
}
