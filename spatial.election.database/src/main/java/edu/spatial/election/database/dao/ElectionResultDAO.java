package edu.spatial.election.database.dao;

import java.util.List;

import edu.spatial.election.domain.ElectionResult;

public interface ElectionResultDAO extends SimpleDAO{

	public List<ElectionResult> getElectionResultsByConstituencyId(int constituencyId);
	public List<ElectionResult> getElectionResultsByPartyId(int partyId);
	public void saveElectionResult(ElectionResult electionResult);
	public List<ElectionResult> getElectionResults();
}
