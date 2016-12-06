package edu.spatial.election.domain.keys;

import java.io.Serializable;

public class ElectionConstituencyPartyId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	Integer election;
	Integer constituency;
	Integer party;

	@Override
	public int hashCode(){
		int result = 42;
		result = 37 * result + election.hashCode();
		result = 37 * result + constituency.hashCode();
		result = 37 * result + party.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof ElectionConstituencyPartyId
				&&  election.equals(((ElectionConstituencyPartyId) obj).election)
				&&  constituency.equals(((ElectionConstituencyPartyId) obj).constituency)
				&&  party.equals(((ElectionConstituencyPartyId) obj).party);
	}
}	
