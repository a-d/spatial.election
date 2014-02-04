package edu.spatial.election.backend.holder;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import edu.spatial.election.domain.County;
import edu.spatial.election.domain.CountyContainsConstituency;
import edu.spatial.election.domain.ElectionResult;
import edu.spatial.election.domain.Party;

public class CountyVotes {

	private County county;
	private Collection<CountyVote> results = new LinkedList<CountyVote>();
	private Map<Integer, Double> constituencies = new HashMap<Integer, Double>();

	

	public CountyVotes(County county) {
		this.county = county;
		
		init();
	}


	private void init() {
		
		// aggregate all results of containing constituencies
		HashMap<Party, CountyVote> countyResults = new HashMap<Party, CountyVote>();
		for(CountyContainsConstituency ccc : county.getDependingConstituencies()) {
			
			constituencies.put(ccc.getConstituencyId(), ccc.getAreaQuota());
			Collection<ElectionResult> res = ccc.getConstituency().getElectionResults();
			Double influence = ccc.getAreaQuota();
			
			
			for(ElectionResult result : res) {
				
				Party party = result.getParty();
				CountyVote val = countyResults.get(party);
				if(val==null)
				{
					countyResults.put(party, new CountyVote(result, influence));
				}
				else
				{
					val.add(result, influence);
				}
			}
		}
		
		this.results = countyResults.values();
	}
	
	public Map<Integer, Double> getConstituencies() {
		return constituencies;
	}
	
	public County getCounty() {
		return county;
	}
	public Collection<CountyVote> getResults() {
		return results;
	}
	
}
