package edu.spatial.election.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import edu.spatial.election.domain.keys.ElectionResultKey;

@Entity
@Table(name = "ELECTIONRESULT")
@IdClass(ElectionResultKey.class)
public class ElectionResult {
	
	@Id
	private long partyID;
	
	@Id
	private long constituencyID;
	
	private long votes;

	public long getPartyID() {
		return partyID;
	}

	public void setPartyID(long partyID) {
		this.partyID = partyID;
	}

	public long getConstituencyID() {
		return constituencyID;
	}

	public void setConstituencyID(long constituencyID) {
		this.constituencyID = constituencyID;
	}

	public long getVotes() {
		return votes;
	}

	public void setVotes(long votes) {
		this.votes = votes;
	}
}
