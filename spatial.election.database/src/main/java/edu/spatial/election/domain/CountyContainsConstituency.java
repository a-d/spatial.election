package edu.spatial.election.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.spatial.election.domain.keys.CountyConstituencyId;



// TODO
@Entity @IdClass(CountyConstituencyId.class)
@Table(name = "COUNTY_INTERSECTING_CONSTITUENCY")
public class CountyContainsConstituency implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	@Id
	@Column(name = "county_Id")
	private Long countyId;

	@Id
	@Column(name = "constituency_Id")
	private Long constituencyId;
*/
	@Id
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "county_Id")
	private County county;

	@Id
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "constituency_id")
	private Constituency constituency;
	
	
	@Column(name = "county_label")
	private String countyName;

	@Column(name = "constituency_label")
	private String constituencyName;

	@Column(name = "area_intersection")
	private Double areaIntersection;

	@Column(name = "area_quota")
	private Double areaQuota;

	
	
	
	
	
	public Integer getCountyId() {
		return county.getCountyId();
	}
	
	public Integer getConstituencyId() {
		return constituency.getWkr_nr();
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

	public void setAreaIntersection(Double areaIntersection) {
		this.areaIntersection = areaIntersection;
	}

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


	public double getAreaQuota() {
		return areaQuota;
	}


	public void setAreaQuota(double areaQuota) {
		this.areaQuota = areaQuota;
	}

}
