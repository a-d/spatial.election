package edu.spatial.election.domain.keys;

import java.io.Serializable;

public class ConstituencyKeyId  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	Integer constituency;
	Integer key;

	@Override
	public int hashCode(){
		int result = 42;
		result = 37 * result + constituency.hashCode();
		result = 37 * result + key.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof ConstituencyKeyId
				&&  constituency.equals(((ConstituencyKeyId) obj).constituency)
				&&  key.equals(((CountyKeyId) obj).key);
	}


}
