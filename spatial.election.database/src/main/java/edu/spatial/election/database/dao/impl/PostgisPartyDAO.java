package edu.spatial.election.database.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import edu.spatial.election.database.dao.PartyDAO;
import edu.spatial.election.database.exceptions.PartyNotFoundException;
import edu.spatial.election.domain.Party;

public class PostgisPartyDAO implements PartyDAO {
	
	static private final Log log = LogFactory.getLog(PostgisPartyDAO.class);
	private Session s;

	public void setConnection(Session s) {
		this.s = s;
	}

	public Party findPartyById(long id) throws PartyNotFoundException {
		log.info("retrieving party with ID " + id);

		Party out = null;
		try
		{
			out = (Party) s.createCriteria(Party.class)
					.add(Restrictions.idEq(id))
					.list()
					.get(0);
		}
		catch(Exception e)
		{
			throw new PartyNotFoundException("Could not find Party by Id \""+id+"\".", e);
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	public List<Party> getParties() {
		return (List<Party>) s.createCriteria(Party.class).list();
	}

	public void saveParty(Party party) {
		s.saveOrUpdate(party);

	}

	public void deleteParty(long id) throws PartyNotFoundException {
		s.delete(findPartyById(id));
	}

}
