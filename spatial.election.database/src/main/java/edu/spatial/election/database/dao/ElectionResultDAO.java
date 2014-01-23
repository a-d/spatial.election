package edu.spatial.election.database.dao;

import java.util.List;

import edu.spatial.election.database.exceptions.ElectionResultNotFoundException;
import edu.spatial.election.domain.ElectionResult;
import edu.spatial.election.domain.keys.ElectionResultKey;

public interface ElectionResultDAO extends SimpleDAO{

	public void createElectionResult(ElectionResult electionResult);
	public ElectionResult findElectionResultById(ElectionResultKey id) throws ElectionResultNotFoundException;
	public List<ElectionResult> getElectionResultsByConstituencyId(long constituencyId);
	public List<ElectionResult> getElectionResultsByPartyId(long partyId);
	public void saveElectionResult(ElectionResult electionResult);
	public List<ElectionResult> getElectionResults();
	public void deleteElectionResult(ElectionResultKey id) throws ElectionResultNotFoundException;
}
