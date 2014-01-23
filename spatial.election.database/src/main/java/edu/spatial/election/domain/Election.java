package edu.spatial.election.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
public class Election {

	@Id
	private long electionId;
	
	private int year;
	
	private Date date;
	
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="election")
	private Set<ElectionResult> results = new HashSet<ElectionResult>();


	
	
	
	public long getElectionId() {
		return electionId;
	}


	public void setElectionId(long electionId) {
		this.electionId = electionId;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
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