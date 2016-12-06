package edu.spatial.election.database.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.spatial.election.database.dao.ElectionResultDAO;
import edu.spatial.election.domain.ElectionResult;

public class PostgisElectionResultDAO implements ElectionResultDAO {
	
	static private final Log log = LogFactory.getLog(PostgisElectionResultDAO.class);
	private CriteriaBuilder cb;
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
		this.cb = em.getCriteriaBuilder();
	}
	
	public void saveElectionResult(ElectionResult electionResult) {
		em.persist(electionResult);
	}

	public List<ElectionResult> getElectionResults() {
		CriteriaQuery<ElectionResult> q = cb.createQuery(ElectionResult.class);
		return em.createQuery(q.select(q.from(ElectionResult.class))).getResultList();
	}

	public List<ElectionResult> getElectionResultsByConstituencyId(int constituencyId) {
		log.info("retrieving ElectionResult with constituencyId " + constituencyId);

		CriteriaQuery<ElectionResult> cq = cb.createQuery(ElectionResult.class);
		CriteriaQuery<ElectionResult> query = cq.where(cb.equal(cq.from(ElectionResult.class).get("constituencyId"), constituencyId));
		List<ElectionResult> out = em.createQuery(query).getResultList();
		
		return out;
	}

	public List<ElectionResult> getElectionResultsByPartyId(int partyId) {
		log.info("retrieving ElectionResult with partyId " + partyId);

		CriteriaQuery<ElectionResult> cq = cb.createQuery(ElectionResult.class);
		CriteriaQuery<ElectionResult> query = cq.where(cb.equal(cq.from(ElectionResult.class).get("partyId"), partyId));
		List<ElectionResult> out = em.createQuery(query).getResultList();

		return out;
	}


}
