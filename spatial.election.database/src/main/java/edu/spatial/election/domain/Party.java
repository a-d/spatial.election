package edu.spatial.election.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PARTY")
public class Party {

	@Id
	@GeneratedValue
	private long partyID;
	
	private String partyName;

	public long getPartyID() {
		return partyID;
	}

	public void setPartyID(long partyID) {
		this.partyID = partyID;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
}
