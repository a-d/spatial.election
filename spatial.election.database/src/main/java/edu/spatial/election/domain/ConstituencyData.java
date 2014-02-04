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
@Table(name="CONSTITUENCY_DATA")
public class ConstituencyData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "constituency_Id")
	private long constituencyId;
	
	@Id
	@Column(name = "key_Id")
	private long keyId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "constituency_id")
	private Constituency constituency;

	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name = "key_id")
	private DataKey key;
	
	
	
	private Double value;
	
	
	
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Constituency getConstituency() {
		return constituency;
	}
	public void setConstituency(Constituency constituency) {
		this.constituency = constituency;
	}
	public DataKey getKey() {
		return key;
	}
	public void setKey(DataKey key) {
		this.key = key;
	}
}
