package edu.spatial.election.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.spatial.election.domain.keys.ConstituencyKeyId;



@Entity @IdClass(ConstituencyKeyId.class)
@Table(name="CONSTITUENCY_DATA")
public class ConstituencyData implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	@Id
	@Column(name = "constituency_Id")
	private Long constituencyId;

	@Id
	@Column(name = "key_Id")
	private Long keyId;
	*/

	@Id
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "constituency_id")
	private Constituency constituency;

	@Id
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name = "key_id")
	private DataKey key;
	
	
	
	private Double value;
	
	
/*
	public Long getConstituencyId() {
		return constituencyId;
	}
	public void setConstituencyId(Long constituencyId) {
		this.constituencyId = constituencyId;
	}
	public Long getKeyId() {
		return keyId;
	}
	public void setKeyId(Long keyId) {
		this.keyId = keyId;
	}
	*/
	public Double getValue() {
		return value;
	}
	public Constituency getConstituency() {
		return constituency;
	}
	public DataKey getKey() {
		return key;
	}
}
