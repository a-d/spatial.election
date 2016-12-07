package edu.spatial.election.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="DATA_KEY")
public class DataKey implements Comparable<DataKey> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(unique=true, nullable=false)
	private String name;

	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="key")
	private Set<ConstituencyData> constituencyData = new HashSet<ConstituencyData>();

	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="key")
	private Set<CountyData> countyData = new HashSet<CountyData>();
	

	public int compareTo(DataKey o) {
		return getName().compareTo(o.getName());
	}
	
	@Override
	public String toString() {
		return name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ConstituencyData> getConstituencyData() {
		return constituencyData;
	}

	public void setConstituencyData(Set<ConstituencyData> constituencyData) {
		this.constituencyData = constituencyData;
	}

	public Set<CountyData> getCountyData() {
		return countyData;
	}

	public void setCountyData(Set<CountyData> countyData) {
		this.countyData = countyData;
	}



}
