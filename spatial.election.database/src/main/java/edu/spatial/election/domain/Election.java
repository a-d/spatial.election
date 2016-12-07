package edu.spatial.election.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Election {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer electionId;
	
	private Integer year;
	
	private Date date;
	
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="election")
	private Set<ElectionResult> results = new HashSet<ElectionResult>();


	@Override
	public String toString() {
		return String.valueOf(year);
	}
	
	
	public Integer getElectionId() {
		return electionId;
	}


	public void setElectionId(Integer electionId) {
		this.electionId = electionId;
	}


	public Integer getYear() {
		return year;
	}


	public void setYear(Integer year) {
		this.year = year;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public Set<ElectionResult> getResults() {
		return results;
	}


	public void setResults(Set<ElectionResult> results) {
		this.results = results;
	}
	
}
