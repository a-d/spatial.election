package edu.spatial.election.domain;

import javax.persistence.Embeddable;

@Embeddable
public class CountyData {
	
	private String key;
	private long value;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
}
