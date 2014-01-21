package edu.spatial.election.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	
	private long votes;

	public long getPartyId() {
		return partyId;
	}
	


	@JsonIgnore
	@Transient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "constituencyId")
	private Constituency constituency;

	@JsonIgnore
	@Transient
	@ManyToOne(fetch = FetchType.LAZY)
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

	public long getVotes() {
		return votes;
	}

	public void setVotes(long votes) {
		this.votes = votes;
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
