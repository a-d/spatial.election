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
	private long partyId;
	
	@Id
	private long constituencyId;
	
	private long votes;

	public long getPartyId() {
		return partyId;
	}

	public void setPartyId(long partyId) {
		this.partyId = partyId;
	}

	public long getConstituencyId() {
		return constituencyId;
	}

	public void setConstituencyId(long constituencyId) {
		this.constituencyId = constituencyId;
	}

	public long getVotes() {
		return votes;
	}

	public void setVotes(long votes) {
		this.votes = votes;
	}
}
