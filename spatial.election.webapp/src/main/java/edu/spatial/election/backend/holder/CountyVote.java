package edu.spatial.election.backend.holder;

import edu.spatial.election.domain.ElectionResult;

public class CountyVote {

	private long partyId;
	private double votes1;
	private double votes2;
	
	
	public CountyVote(ElectionResult result, Double influence) {
		this.partyId = result.getPartyId();
		this.votes1 = influence * result.getPrimaryVotes();
		this.votes2 = influence * result.getSecondaryVotes();
	}
	
	public void add(ElectionResult result, Double influence)
	{
		if(this.partyId == result.getPartyId()) {
			this.votes1 += influence * result.getPrimaryVotes();
			this.votes2 += influence * result.getSecondaryVotes();
		}
	}
	
	
	public long getPartyId() {
		return partyId;
	}
	public void setPartyId(long partyId) {
		this.partyId = partyId;
	}
	public double getVotes1() {
		return votes1;
	}
	public void setVotes1(double votes1) {
		this.votes1 = votes1;
	}
	public double getVotes2() {
		return votes2;
	}
	public void setVotes2(double votes2) {
		this.votes2 = votes2;
	}
	
}
