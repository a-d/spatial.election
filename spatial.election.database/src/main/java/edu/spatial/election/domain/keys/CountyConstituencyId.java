package edu.spatial.election.domain.keys;

import java.io.Serializable;

public class CountyConstituencyId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	Integer county;
	Integer constituency;

	@Override
	public int hashCode(){
		int result = 42;
		result = 37 * result + county.hashCode();
		result = 37 * result + constituency.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof CountyConstituencyId
				&&  county.equals(((CountyConstituencyId) obj).county)
				&&  constituency.equals(((CountyConstituencyId) obj).constituency);
	}
	
	@Override
	public String toString() {
		return "Election Result Key Object with: ConstituencyID " + this.constituency + " PartyID " + this.county;
	}
}
