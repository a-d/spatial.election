package edu.spatial.election.database.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import edu.spatial.election.database.dao.ElectionResultDAO;
import edu.spatial.election.database.exceptions.ElectionResultNotFoundException;
import edu.spatial.election.domain.ElectionResult;
import edu.spatial.election.domain.keys.ElectionResultKey;

public class PostgisElectionResultDAO implements ElectionResultDAO {
	
	static private final Log log = LogFactory.getLog(PostgisElectionResultDAO.class);
	private Session s;

	public void setConnection(Session s) {
		this.s = s;
	}
	public ElectionResult findElectionResultById(ElectionResultKey id) throws ElectionResultNotFoundException {
		log.info("retrieving ElectionResult with ElectionResultKey " + id.toString());

		ElectionResult out = null;
		try
		{
			out = (ElectionResult) s.createCriteria(ElectionResult.class)
					.add(Restrictions.idEq(id))
					.list()
					.get(0);
		}
		catch(Exception e)
		{
			throw new ElectionResultNotFoundException("Could not find ElectionResult by Id \"" + id.toString() +"\".", e);
		}
		return out;
	}

	public void saveElectionResult(ElectionResult electionResult) {
		s.saveOrUpdate(electionResult);
	}

	public void deleteElectionResult(ElectionResultKey id)
			throws ElectionResultNotFoundException {
		s.delete(findElectionResultById(id));
	}

	@SuppressWarnings("unchecked")
	public List<ElectionResult> getElectionResults() {
		return (List<ElectionResult>) s.createCriteria(ElectionResult.class).list();
	}

	public List<ElectionResult> getElectionResultsByConstituencyId(long constituencyId) {
		log.info("retrieving ElectionResult with constituencyId " + constituencyId);

		@SuppressWarnings("unchecked")
		List<ElectionResult> out = (List<ElectionResult>) s.createCriteria(ElectionResult.class)
					.add(Restrictions.eq("constituencyId", constituencyId))
					.list();
		return out;
	}

	public List<ElectionResult> getElectionResultsByPartyId(long partyId) {
		log.info("retrieving ElectionResult with partyId " + partyId);

		@SuppressWarnings("unchecked")
		List<ElectionResult> out = (List<ElectionResult>) s.createCriteria(ElectionResult.class)
					.add(Restrictions.eq("partyId", partyId))
					.list();
		return out;
	}

}
