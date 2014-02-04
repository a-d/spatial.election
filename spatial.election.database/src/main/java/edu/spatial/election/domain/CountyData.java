package edu.spatial.election.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="COUNTY_DATA")
public class CountyData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "county_Id")
	private int countyId;
	
	@Id
	@Column(name = "key_Id")
	private int keyId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "county_id")
	private County county;

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
