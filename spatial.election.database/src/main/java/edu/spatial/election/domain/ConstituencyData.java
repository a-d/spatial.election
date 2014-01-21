package edu.spatial.election.domain;

import javax.persistence.Embeddable;

@Embeddable
public class ConstituencyData {
	
	private String key;
	private long value;
	
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
