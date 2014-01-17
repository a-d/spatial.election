package edu.spatial.election.database.dao.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import edu.spatial.election.database.dao.ConstituencyDAO;
import edu.spatial.election.database.exceptions.ConstituencyNotFoundException;
import edu.spatial.election.domain.Constituency;

public class PostgisConstituencyDAO implements ConstituencyDAO {

	static private final Log log = LogFactory.getLog(PostgisConstituencyDAO.class);
	private Session s;

	public Constituency findConstituencyById(long id) throws ConstituencyNotFoundException {
		log.info("retrieving constituency with ID " + id);

		Constituency out = null;
		try
		{
			out = (Constituency) s.createCriteria(Constituency.class)
					.add(Restrictions.idEq(id))
					.list()
					.get(0);
		}
		catch(Exception e)
		{
			throw new ConstituencyNotFoundException("Could not find Constituency by Id \""+id+"\".", e);
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	public Collection<Constituency> findConstituencyByState(String stateName) {
		log.info("retrieving constituencies with state name " + stateName);

		List<Constituency> out = (List<Constituency>) s.createCriteria(Constituency.class)
					.add(Restrictions.eq("land_name", stateName))
					.list();
		return out;

		// stmt.append("st_asgeojson(c.geom) as geometry ");
	}

	public void saveConstituency(Constituency c) {
		s.saveOrUpdate(c);
	}

	public void deleteConstituency(long id) throws ConstituencyNotFoundException {
		s.delete(findConstituencyById(id));
	}

	public void setConnection(Session s) {
		this.s = s;
	}
}
