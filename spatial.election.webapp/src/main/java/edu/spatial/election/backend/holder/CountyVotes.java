package edu.spatial.election.backend.holder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import edu.spatial.election.domain.County;
import edu.spatial.election.domain.CountyContainsConstituency;
import edu.spatial.election.domain.ElectionResult;
import edu.spatial.election.domain.Party;

public class CountyVotes {

	private County county;
	private HashMap<Party, Double> results;
	private LinkedList<Integer> constituencyIds = new LinkedList<Integer>();


	public CountyVotes(County county) {
		this.county = county;
		
		init();
	}


	private void init() {
		
		// aggregate all results of containing constituencies
		HashMap<Party, Double> countyResults = new HashMap<Party, Double>();
		for(CountyContainsConstituency ccc : county.getDependingConstituencies()) {
			
			constituencyIds.add(ccc.getConstituencyId());
			List<ElectionResult> res = ccc.getConstituency().getElectionResults();
			Double influence = ccc.getDependencyIndex();
			
			
			for(ElectionResult result : res) {
				
				Party party = result.getParty();
				Double oldVal = countyResults.get(party);
				Double newVal = (oldVal==null ? 0.0 : oldVal) + influence * result.getVotes();
				
				countyResults.put(party, newVal);
			}
		}
		
		this.results = countyResults;
	}
	
	public LinkedList<Integer> getConstituencyIds() {
		return constituencyIds;
	}
	
	public County getCounty() {
		return county;
	}
	public HashMap<Party, Double> getResults() {
		return results;
	}
	
}
