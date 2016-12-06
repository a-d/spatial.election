package edu.spatial.election.database.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.spatial.election.database.dao.PartyDAO;
import edu.spatial.election.database.exceptions.PartyNotFoundException;
import edu.spatial.election.domain.Party;

public class PostgisPartyDAO implements PartyDAO {
	
	static private final Log log = LogFactory.getLog(PostgisPartyDAO.class);
	private CriteriaBuilder cb;
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
		this.cb = em.getCriteriaBuilder();
	}

	public Party findPartyById(int id) throws PartyNotFoundException {
		log.info("retrieving party with ID " + id);

		Party out = null;
		try
		{
			out = em.find(Party.class, id);
		}
		catch(Exception e)
		{
			throw new PartyNotFoundException("Could not find Party by Id \""+id+"\".", e);
		}
		return out;
	}


	public List<Party> getParties() {
		CriteriaQuery<Party> q = cb.createQuery(Party.class);
		return em.createQuery(q.select(q.from(Party.class))).getResultList();
	}

	public void saveParty(Party party) {
		em.persist(party);

	}

	public void deleteParty(int id) throws PartyNotFoundException {
		em.remove(findPartyById(id));
	}

}
