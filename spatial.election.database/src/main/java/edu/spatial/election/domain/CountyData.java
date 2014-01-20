package edu.spatial.election.domain;

import javax.persistence.Embeddable;

@Embeddable
public class CountyData {
	private String key;
	private long value;
}
