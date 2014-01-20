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
@Table(name = "COUNTY_DEPENDING_CONSTITUENCY")
public class CountyContainsConstituency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="county_Id")
	private int countyId;
	
	@Id
	@Column(name="constituency_Id")
	private int constituencyId;

	@Column(name="county_label")
	private String countyName;

	@Column(name="constituency_label")
	private String constituencyName;

	@Column(name="area_intersection")
	private double areaIntersection;
	
	@Column(name="density")
	private double populationDensity;
	
	@Column(name="ratio")
	private double dependencyIndex;


	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="county_id")
	private County county;



	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="constituency_id")
	private Constituency constituency;
	
	
	
	
	
	public int getCountyId() {
		return countyId;
	}

	public void setCountyId(int countyid) {
		this.countyId = countyid;
	}

	public int getConstituencyId() {
		return constituencyId;
	}

	public void setConstituencyId(int constituencyId) {
		this.constituencyId = constituencyId;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getConstituencyName() {
		return constituencyName;
	}

	public void setConstituencyName(String constituencyName) {
		this.constituencyName = constituencyName;
	}

	public double getAreaIntersection() {
		return areaIntersection;
	}

	public void setAreaIntersection(double areaIntersection) {
		this.areaIntersection = areaIntersection;
	}

	public double getPopulationDensity() {
		return populationDensity;
	}

	public void setPopulationDensity(double populationDensity) {
		this.populationDensity = populationDensity;
	}

	public double getDependencyIndex() {
		return dependencyIndex;
	}

	public void setDependencyIndex(double dependencyIndex) {
		this.dependencyIndex = dependencyIndex;
	}
/*
	public County getCounty() {
		return county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	public Constituency getConstituency() {
		return constituency;
	}

	public void setConstituency(Constituency constituency) {
		this.constituency = constituency;
	}
*/
}
