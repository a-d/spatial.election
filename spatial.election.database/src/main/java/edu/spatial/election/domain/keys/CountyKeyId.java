package edu.spatial.election.domain.keys;

import java.io.Serializable;

public class CountyKeyId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	Integer county;
	Integer key;

	@Override
	public int hashCode(){
		int result = 42;
		result = 37 * result + county.hashCode();
		result = 37 * result + key.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof CountyKeyId
				&&  county.equals(((CountyKeyId) obj).county)
				&&  key.equals(((CountyKeyId) obj).key);
	}

}
