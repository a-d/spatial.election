package edu.spatial.election.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "ELECTIONRESULT")
//@IdClass(ElectionResultKey.class) // TODO could not be initiated
public class ElectionResult implements Serializable {
	
	@Id
	private long partyId;
	
	@Id
	private long constituencyId;
	
	private long primaryVotes;
	
	private long secondaryVotes;
	
	public long getPartyId() {
		return partyId;
	}
	


	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "constituencyId")
	private Constituency constituency;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "partyId")
	private Party party;
	

	public void setPartyId(long partyId) {
		this.partyId = partyId;
	}

	public long getConstituencyId() {
		return constituencyId;
	}

	public void setConstituencyId(long constituencyId) {
		this.constituencyId = constituencyId;
	}

	public long getPrimaryVotes() {
		return primaryVotes;
	}

	public void setPrimaryVotes(long primaryVotes) {
		this.primaryVotes = primaryVotes;
	}
	
	public long getSecondaryVotes() {
		return secondaryVotes;
	}

	public void setSecondaryVotes(long secondaryVotes) {
		this.secondaryVotes = secondaryVotes;
	}

	public Constituency getConstituency() {
		return constituency;
	}

	public void setConstituency(Constituency constituency) {
		this.constituency = constituency;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}
}
