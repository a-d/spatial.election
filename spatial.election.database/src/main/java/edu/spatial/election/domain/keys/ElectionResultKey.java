package edu.spatial.election.domain.keys;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ElectionResultKey implements Serializable {

	private long partyID;
	private long constituencyID;
	
	public ElectionResultKey() {
	}
	
	public ElectionResultKey(long partyID, long constituencyID) {
		this.partyID = partyID;
		this.constituencyID = constituencyID;
	}

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

	@Override
	public boolean equals(Object o) {
	    if (o == null) return false;
	    if (o == this) return true;
	    if (!(o instanceof ElectionResultKey)) {
	    	return false;
	    } else {
	    	long oPartyID = ((ElectionResultKey) o).getPartyID();
	    	long oConstituencyID = ((ElectionResultKey) o).getConstituencyID();
	    	if (oPartyID == this.partyID && oConstituencyID == this.constituencyID) {
	    		return true;
	    	} else {
	    		return false;
	    	}
	    }
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "Election Result Key Object with: ConstituencyID " + this.constituencyID + " PartyID " + this.partyID;
	}
}
