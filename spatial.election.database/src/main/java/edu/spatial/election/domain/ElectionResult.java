package edu.spatial.election.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.spatial.election.domain.keys.ElectionConstituencyPartyId;


@Entity @IdClass(ElectionConstituencyPartyId.class)
@Table(name = "ELECTIONRESULT")
public class ElectionResult implements Serializable {
	
	private static final long serialVersionUID = 1;

	/*
	@Id
	@Column(name = "partyId")
	private long partyId;

	@Id
	@Column(name = "constituencyId")
	private long constituencyId;

	@Id
	@Column(name="election_electionid")
	private Long electionId;
	*/
	
	private Long primaryVotes;
	private Long secondaryVotes;
	

	@Id
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "election_electionid")
	private Election election;

	@Id
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "constituencyId")
	private Constituency constituency;

	@Id
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "partyId")
	private Party party;
	


	/*
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

	public Long getElectionId() {
		return electionId;
	}


	public void setElectionId(Long electionId) {
		this.electionId = electionId;
	}
*/
	public Long getPrimaryVotes() {
		return primaryVotes;
	}

	public void setPrimaryVotes(Long primaryVotes) {
		this.primaryVotes = primaryVotes;
	}
	
	public Long getSecondaryVotes() {
		return secondaryVotes;
	}

	public void setSecondaryVotes(Long secondaryVotes) {
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


	public Election getElection() {
		return election;
	}


	public void setElection(Election election) {
		this.election = election;
	}

}
