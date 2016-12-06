package edu.spatial.election.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import edu.spatial.election.domain.keys.CountyKeyId;

@Entity @IdClass(CountyKeyId.class)
@Table(name="COUNTY_DATA")
public class CountyData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "county_id")
	private County county;

	@Id
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name = "key_id")
	private DataKey key;
	
	
	
	private Double value;

	
	
	
	public County getCounty() {
		return county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	public DataKey getKey() {
		return key;
	}

	public void setKey(DataKey key) {
		this.key = key;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
